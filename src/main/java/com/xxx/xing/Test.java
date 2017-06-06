package com.xxx.xing;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author xing
 * @Created by 2017-05-04 上午9:39.
 */
@Controller
@RequestMapping("/test")
public class Test {

//    @RequestMapping("/jsoup")
//    @ResponseBody
//    public Bookmark test(@RequestParam(name = "url", required = false) String url) throws IOException {
//        Connection connection = Jsoup.connect(url);
//        connection.timeout(5000).userAgent("Mozilla/4.0 (compatible; MSIE 9.0; Windows NT 6.1; Trident/5.0)");
//        Document doc = connection.get();
//        HttpRequest request = new HttpRequest(url);
//        String html = request.response().decode();
//        Document doc = Jsoup.parse(html, url);
//        String title=doc.title();
//        String content= ContentExtractor.getContentByUrl(url);
//
//
//        DomPage domPage = new DomPage(doc);
//        ContentExtractor contentExtractor = new ContentExtractor(domPage);
//        String content = Jsoup.parse(contentExtractor.getContent()).text();
//        return new Bookmark("12", domPage.getDoc().title(), content, url);
//    }
    @Value("${solr.host}")
    String solr;

    @RequestMapping("value")
    @ResponseBody
    public String getValue(){
        return  solr;
    }


}
