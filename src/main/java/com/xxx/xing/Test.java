package com.xxx.xing;

import cn.edu.hfut.dmic.contentextractor.ContentExtractor;
import com.xxx.xing.entity.Bookmark;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;

/**
 * @author xing
 * @Created by 2017-05-04 上午9:39.
 */
@Controller
@RequestMapping("/test")
public class Test {

    @RequestMapping("/jsoup")
    @ResponseBody
    public Bookmark test(@RequestParam(name = "url", required = false) String url) throws IOException {
        Connection connection = Jsoup.connect(url);
        connection.timeout(5000).userAgent("Mozilla/4.0 (compatible; MSIE 9.0; Windows NT 6.1; Trident/5.0)");
        Document doc = connection.get();

//        DomPage domPage = new DomPage(doc);
//        ContentExtractor contentExtractor = new ContentExtractor(domPage);
//        String content = Jsoup.parse(contentExtractor.getContent()).text();
//        return new Bookmark("12", domPage.getDoc().title(), content, url);
        try {
            String content= ContentExtractor.getContentByUrl(url);
        return new Bookmark("12", "21", content, url);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;

    }
    @Value("${solr.host}")
    String solr;

    @RequestMapping("value")
    @ResponseBody
    public String getValue(){
        return  solr;
    }


}
