package com.xxx.xing.solr;

import com.xxx.xing.configuration.SolrConfig;
import com.xxx.xing.entity.Bookmark;
import com.xxx.xing.executor.BookmarkExecutor;
import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * @author xing
 * @Created by 2017-04-01 上午9:39.
 */
@Component
public class BookmarkSolr {
    //多少次请求无数据为无效书签
    private static final int REQUEST_TIME = 3;
    private static Log log = LogFactory.getLog(BookmarkSolr.class);
    //每页多少条数据
    private final int PAGE_NUM = 10;
    // 默认是第0页
    private final int PAGE_ROW = 0;
    @Autowired
    BookmarkExecutor bookmarkExecutor;
    @Autowired
    private SolrConfig solrConfig;

    public Map<String, Bookmark> getBookmarkByUrl(String[] urlArr, String userid) {
        Map<String, Bookmark> map = new HashMap<String, Bookmark>();
        List<Future> list = new ArrayList<Future>();
        for (String url : urlArr) {
            list.add(bookmarkExecutor.addTask(url, userid));
        }
        int i = 0;
        for (Future f : list) {
            try {
                i++;
                log.info(i + ":" + System.currentTimeMillis());
                Bookmark b = (Bookmark) f.get();
                log.info(i + ":" + System.currentTimeMillis());
                if (b != null) {
                    map.put(b.getUrl(), b);
                }
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }
        return map;
    }

    public SolrClient getSolrClient() {
        return new HttpSolrClient(solrConfig.getHost() + "/" + solrConfig.getSolrName());
    }

    /**
     * 新建索引
     *
     * @param bookmark
     */
    public void createIndex(Bookmark bookmark) {
        SolrClient solrClient = getSolrClient();
        List<SolrInputDocument> documentList = new ArrayList<SolrInputDocument>();
        SolrInputDocument document = new SolrInputDocument();
        documentList.add(getDocument(bookmark));
        try {
            solrClient.add(documentList);
            solrClient.commit();
        } catch (SolrServerException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * 新建多个索引
     *
     * @param bookmarks
     */
    public void createIndexs(Map<String, Bookmark> bookmarks) {
        SolrClient solrClient = getSolrClient();
        List<SolrInputDocument> documentList = new ArrayList<SolrInputDocument>();
        SolrInputDocument document = new SolrInputDocument();
        if (bookmarks.size() == 0) {
            return;
        }
        Iterator iterator = bookmarks.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, Bookmark> entry = (Map.Entry<String, Bookmark>) iterator.next();
            documentList.add(getDocument((Bookmark) entry.getValue()));

        }

        try {
            solrClient.add(documentList);
            solrClient.commit();
        } catch (SolrServerException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public SolrInputDocument getDocument(Bookmark bookmark) {
        SolrInputDocument document = new SolrInputDocument();
        document.addField("id", bookmark.getId());
        document.addField("bookmark_userId", bookmark.getUserId());
        document.addField("bookmark_title", bookmark.getTitle());
        document.addField("bookmark_content", bookmark.getContent());
        document.addField("bookmark_url", bookmark.getUrl());
        return document;
    }

    /**
     * 根据id 删除solr索引
     *
     * @param ids
     */
    public void deleteIndexByIds(List<String> ids) {
        SolrClient client = getSolrClient();
        try {
            //根据查询条件删除数据,这里的条件只能有一个，不能以逗号相隔
            client.deleteById(ids);
            //一定要记得提交，否则不起作用
            client.commit();
            client.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 删除所有的书签索引
     */
    public void deleteAllIndex() {
        SolrClient client = getSolrClient();
        try {
            client.deleteByQuery("*");
            client.commit();
            client.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 根据关键词搜索
     *
     * @param keywords
     * @param page
     * @return
     */
    public Map<String, Object> search(String keywords, String userId, int page) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        SolrClient client = getSolrClient();
        SolrQuery query = new SolrQuery();
        //设置高亮
        query.setHighlight(true);
        query.setParam("hl.fl", "bookmark_content,bookmark_title");
        //过滤
        query.set("fq", "bookmark_userId:" + userId);
        //前缀
        query.setHighlightSimplePre("<span style='color:red'>");
        //后缀
        query.setHighlightSimplePost("</span>");
        query.setQuery("bookmark_keywords:" + StringEscapeUtils.escapeHtml4(keywords));
        query.setStart((page - 1) * PAGE_NUM);
        query.setRows(PAGE_NUM);

        QueryResponse response = null;
        List<Bookmark> bookmarks = new ArrayList<Bookmark>();
        try {
            response = client.query(query);
            SolrDocumentList documents = response.getResults();
            double time = response.getElapsedTime() / (double) 1000;
            long num = documents.getNumFound();
            resultMap.put("time", time);
            if (documents.size() == 0) {
                resultMap.put("pages", 0);
                resultMap.put("num", 0);
                return resultMap;
            }
            resultMap.put("num", num);
            resultMap.put("pages", Math.ceil((double) num / PAGE_NUM));
            //获取高亮信息
            Map<String, Map<String, List<String>>> highlighting = response.getHighlighting();
            String title = null;
            String content = null;



            for (SolrDocument document : documents) {
                Map<String, List<String>> map = highlighting.get(document.get("id"));
                List<String> listTitle = map.get("bookmark_title");
                if (listTitle != null && listTitle.size() > 0) {
                    title = listTitle.get(0);
                } else {
                    title = (String) document.getFieldValue("bookmark_title");
                }
                List<String> listContent = map.get("bookmark_content");
                if (listContent != null && listContent.size() > 0) {
                    content = listContent.get(0);
                } else {

                    String s = (String) document.getFieldValue("bookmark_content");
                    content = s.length() > 40 ? s.substring(0, 40) : s;
                }
                Bookmark b=new Bookmark(
                        (String) document.getFieldValue("bookmark_userId"),
                        title,
                        content,
                        (String) document.getFieldValue("bookmark_url")
                );
                bookmarks.add(b);
                log.info("搜索到:"+b.toString());
            }


        } catch (SolrServerException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        resultMap.put("data", bookmarks);
        return resultMap;
    }
}
