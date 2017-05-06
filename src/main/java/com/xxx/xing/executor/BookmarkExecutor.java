package com.xxx.xing.executor;

import cn.edu.hfut.dmic.htmlbot.DomPage;
import cn.edu.hfut.dmic.htmlbot.contentextractor.ContentExtractor;
import com.xxx.xing.entity.Bookmark;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * @author xing
 * @desc 多线程跑jsoup获取网页数据
 * @Created by 2017-04-25 下午4:57.
 */
@Component
public class BookmarkExecutor {
    private static final int MAX_THREAD_NUMS = 10;
    //多少次请求无数据为无效书签
    private static final int REQUEST_TIME = 3;

    private static Log log = LogFactory.getLog(BookmarkExecutor.class);

    ExecutorService executorServic;

    @PostConstruct
    public void init() {
        executorServic = Executors.newFixedThreadPool(MAX_THREAD_NUMS);
    }


    public Future<Bookmark> addTask(String url, String userid) {
        Future<Bookmark> f = executorServic.submit(new BookmarkCallable(url, userid));
        return f;
    }


    // 关闭线程池
    public void shutdown() {
        executorServic.shutdown();
    }




    class BookmarkCallable implements Callable<Bookmark> {
        //        private static Log log= LogFactory.getLog(BookmarkCallable.class);
        private String url;

        private String userid;


        public BookmarkCallable(String url, String userid) {
            this.url = url;
            this.userid = userid;
        }


        @Override
        public Bookmark call() throws Exception {
            int count = 0;
            for (int i = 0; i < REQUEST_TIME; i++) {

                try {
                    Connection.Response res = Jsoup.connect(url).timeout(5000)
                            .ignoreHttpErrors(true)
                            .followRedirects(true)
                            .userAgent("Mozilla/4.0 (compatible; MSIE 9.0; Windows NT 6.1; Trident/5.0)")
                            .execute();

                    if (res.statusCode() == 307) {

                        String sNewUrl = res.header("Location");
                        log.info("sNewUrl:" + sNewUrl);
                        if (sNewUrl != null && sNewUrl.length() > 7)
                            url = sNewUrl;
                        res = Jsoup.connect(url).timeout(5000).execute();

                    }

                    Document doc = res.parse();
                    DomPage domPage = new DomPage(doc);
                    ContentExtractor contentExtractor = new ContentExtractor(domPage);
                    String content = Jsoup.parse(contentExtractor.getContent()).text();
                    log.info("title:" + domPage.getDoc().title() + "  url:" + url);

                    return new Bookmark(userid, domPage.getDoc().title(), content, url);
                } catch (Exception e) {
                    if (i == REQUEST_TIME - 1) {
                        count++;
                        log.info("状态码:" + " 无效书签：" + url + " 异常信息:" + e.getMessage());
                    }
                }
            }
            return null;
        }
    }

}
