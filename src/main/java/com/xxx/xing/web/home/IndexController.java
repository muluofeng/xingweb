package com.xxx.xing.web.home;

import cn.edu.hfut.dmic.htmlbot.DomPage;
import cn.edu.hfut.dmic.htmlbot.contentextractor.ContentExtractor;
import com.qq.connect.javabeans.qzone.UserInfoBean;
import com.xxx.xing.entity.Bookmark;
import com.xxx.xing.service.BookmarkService;
import com.xxx.xing.service.TestService;
import com.xxx.xing.service.TokenService;
import com.xxx.xing.solr.BookmarkSolr;
import org.apache.commons.io.IOUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
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

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.*;

/**
 * @author xing
 * @Created by 2017-03-30 上午11:19.
 */
@Controller(value = "homeIndex")
public class IndexController {
    private static Log log = LogFactory.getLog(IndexController.class);
    @Autowired
    BookmarkSolr bookmarkSolr;
    @Autowired
    BookmarkService bookmarkService;
    @Autowired
    TokenService tokenService;
    @Autowired
    TestService testService;

    @RequestMapping("/")
    public String index(@RequestParam(required = false, name = "q") String keyword,
                        @RequestParam(required = false, name = "page") Integer page,
                        Model model, HttpSession session,
                        HttpServletResponse response) {
        /**
         String uuid=UUID.randomUUID().toString();
         int cookieTime = 5000; //有效时间
         Cookie cookie = new Cookie("token", uuid); //添加用户使用chrome插件更新的cookies
         cookie.setMaxAge(cookieTime);
         response.addCookie(cookie);
         **/
        UserInfoBean userInfoBean = (UserInfoBean) session.getAttribute("user");
        String openid = null;
        if (userInfoBean != null) { //获取第三方登录的用户信息
            openid = (String) session.getAttribute("openid");
            model.addAttribute("isLogin", true);
            model.addAttribute("userImg", userInfoBean.getAvatar().getAvatarURL50());
            model.addAttribute("nickname", userInfoBean.getNickname());
            model.addAttribute("openid", openid);

        } else {
            model.addAttribute("isLogin", false);
        }

        if (keyword == null) {
            return "home/index";
        }

        //根据关键字搜索信息
        Map<String, Object> searchResult = bookmarkSolr.search(keyword, openid, page == null ? 1 : page);
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
    public String uploadhtml(@RequestParam(value = "file", required = false) MultipartFile multipartFile, HttpSession session) {
        String openid = (String) session.getAttribute("openid");
        String fileName = multipartFile.getOriginalFilename();
        Map<String, Bookmark> bookmarkMap = new HashMap<>();
        if (openid == null) {
            return null;
        }
        try {
            Document document = Jsoup.parse(IOUtils.toString(multipartFile.getInputStream()), "UTF-8");
            Elements elements = document.getElementsByTag("a");
            List<String> uploadList = new ArrayList<>(); //上传的所有书签
            List<String> existList = new ArrayList<>(); //已经存在的所有书签
            List<String> addList = new ArrayList<>(); //新增的书签
            List<String> deleteList = new ArrayList<>(); //删除的书签
            List<String> ids = new ArrayList<>();
            Map map = new HashMap();
            Iterator it = elements.iterator();
            while (it.hasNext()) {
                Element element = (Element) it.next();
                uploadList.add(element.attr("href"));
            }
            //更新
            bookmarkService.update(openid, uploadList);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 删除所有的索引，方便测试
     */
    @RequestMapping("deleteBookmarks")
    @ResponseBody
    public void deleteBookmarks() {
        bookmarkSolr.deleteAllIndex();
    }

    /**
     * chrome插件ajax更新书签
     *
     * @param urlArr
     * @param token
     */
    @RequestMapping("chromeUpload")
    @ResponseBody
    public void chromeUpload(@RequestParam(name = "url", required = true) String[] urlArr, @RequestParam(required = true, name = "token") String token) {
        String openid = tokenService.findByToken(token);
        log.info(openid);
        log.info("chrome插件开始更新solr");
        if (openid != null && urlArr.length > 0) {
            List<String> list = Arrays.asList(urlArr);
            bookmarkService.update(openid, list);
            log.info("chrome插件更新solr索引成功");
        }
    }

}
