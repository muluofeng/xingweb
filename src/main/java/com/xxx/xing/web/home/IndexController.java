package com.xxx.xing.web.home;

import cn.edu.hfut.dmic.htmlbot.DomPage;
import cn.edu.hfut.dmic.htmlbot.contentextractor.ContentExtractor;
import com.xxx.xing.entity.Bookmark;
import com.xxx.xing.solr.BookmarkSolr;
import org.apache.commons.io.IOUtils;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import sun.nio.ch.IOUtil;

import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * @author xing
 * @Created by 2017-03-30 上午11:19.
 */
@Controller(value = "homeIndex")
public class IndexController {
    @Autowired
    BookmarkSolr bookmarkSolr;

    @RequestMapping("/")
    public String index(@RequestParam(required = false, name = "q") String keyword,
                        @RequestParam(required = false, name = "page") Integer page, Model model) {
        if (keyword == null) {
            return "home/index";
        }

        //根据关键字搜索信息
        Map<String, Object> searchResult = bookmarkSolr.search(keyword, page == null ? 1 : page);
        model.addAttribute("bookmarks", searchResult.get("data"));
        model.addAttribute("num", searchResult.get("num"));
        model.addAttribute("pages", searchResult.get("pages"));
        model.addAttribute("current", page == null ? 1 : page);
        model.addAttribute("keyword", keyword);
        model.addAttribute("time", searchResult.get("time"));
        return "home/resultlist";
    }

    @RequestMapping(value = "/uploadhtml", method = RequestMethod.POST)
    @ResponseBody
    public String uploadhtml(@RequestParam(value = "file", required = false) MultipartFile multipartFile) {
        String fileName = multipartFile.getOriginalFilename();
        Map<String,Bookmark> bookmarkMap=new HashMap<>();

        try {
            Document document = Jsoup.parse(IOUtils.toString(multipartFile.getInputStream()), "UTF-8");
            Elements elements = document.getElementsByTag("a");
            //获取迭代器
            Iterator it = elements.iterator();
            while (it.hasNext()) {
                Element element = (Element) it.next();
                String url = element.attr("href");
                try {
                    Connection connection = Jsoup.connect(url);
                    if (connection.execute().statusCode() != 200) {
                        continue;
                    }
                    connection.timeout(2000).userAgent("Mozilla/4.0 (compatible; MSIE 9.0; Windows NT 6.1; Trident/5.0)");
                    Document doc = connection.get();
                    DomPage domPage = new DomPage(doc);
                    ContentExtractor contentExtractor = new ContentExtractor(domPage);
                    if(bookmarkMap.get(url)==null){
                        bookmarkMap.put(url,new Bookmark(123, domPage.getDoc().title(), contentExtractor.getContent(), url));
                        System.out.println("title:"+domPage.getDoc().title()+"  url:"+url);
                    }
                } catch (Exception e) {
                    continue;
                }

            }

            System.out.println(bookmarkMap.size());
            bookmarkSolr.createIndexs(bookmarkMap);
            System.out.println("solr写入完成");

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @RequestMapping("deleteBookmarks")
    @ResponseBody
    public void deleteBookmarks() {
        bookmarkSolr.deleteIndex();
    }

    @RequestMapping("testSolr")
    @ResponseBody
    public Bookmark test(@RequestParam(required = true,name = "url")String url) {
        Connection connection = Jsoup.connect(url).timeout(2000).userAgent("Mozilla/4.0 (compatible; MSIE 9.0; Windows NT 6.1; Trident/5.0)");
        Document doc = null;
        Bookmark bookmark=null;
        String title=null;
        String content=null;
        try {
            doc = connection.get();
            DomPage domPage = new DomPage(doc);
            ContentExtractor contentExtractor = new ContentExtractor(domPage);
             title = domPage.getDoc().title();
             content = contentExtractor.getContent();
            System.out.println(title);
            bookmark= new Bookmark(123, title, content, url);
        } catch (IOException e) {
            e.printStackTrace();

        }
        return bookmark;
    }
}
