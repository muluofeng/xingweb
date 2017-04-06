package com.xxx.xing.solr;

import com.xxx.xing.configuration.SolrConfig;
import com.xxx.xing.entity.Bookmark;
import org.apache.commons.lang3.StringEscapeUtils;
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

/**
 * @author xing
 * @Created by 2017-04-01 上午9:39.
 */
@Component
public class BookmarkSolr {
    //每页多少条数据
    private final int PAGE_NUM = 7;
    // 默认是第0页
    private final int PAGE_ROW = 0;
    @Autowired
    private SolrConfig solrConfig;

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
        document.addField("id", UUID.randomUUID().toString());
        document.addField("bookmark_userId", bookmark.getUserId());
        document.addField("bookmark_title", bookmark.getTitle());
        document.addField("bookmark_content", bookmark.getContent());
        document.addField("bookmark_url", bookmark.getUrl());
        return document;
    }

    public void deleteIndex() {
        SolrClient client = getSolrClient();
        try {
            //根据查询条件删除数据,这里的条件只能有一个，不能以逗号相隔
            client.deleteByQuery("bookmark_userId:123");
            //一定要记得提交，否则不起作用
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
    public Map<String, Object> search(String keywords, int page) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        SolrClient client = getSolrClient();
        SolrQuery query = new SolrQuery();
        //设置高亮
        query.setHighlight(true);
        query.setParam("hl.fl", "bookmark_content,bookmark_title");
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
                    content=s.substring(0,40);
                }
                bookmarks.add(new Bookmark(
                        Integer.parseInt((String) document.getFieldValue("bookmark_userId")),
                        title,
                        content,
                        (String) document.getFieldValue("bookmark_url")
                ));
                System.out.println(document.getFieldValue("id") + " title :" + title + "  url: " + document.getFieldValue("bookmark_url"));
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
