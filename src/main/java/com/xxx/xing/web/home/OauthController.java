package com.xxx.xing.web.home;

import com.qq.connect.QQConnectException;
import com.qq.connect.api.OpenID;
import com.qq.connect.api.qzone.UserInfo;
import com.qq.connect.javabeans.AccessToken;
import com.qq.connect.javabeans.qzone.UserInfoBean;
import com.qq.connect.oauth.Oauth;
import com.xxx.xing.service.TokenService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.UUID;

/**
 * @author xing
 * @Created by 2017-04-09 上午12:34.
 */
@Controller
@RequestMapping("/oauth")
public class OauthController {
    private static Log log = LogFactory.getLog(OauthController.class);

    @Autowired
    TokenService tokenService;

    @RequestMapping("/qq")
    public void qqLogin(HttpServletResponse response, HttpServletRequest request) {
        try {

            response.sendRedirect(new Oauth().getAuthorizeURL(request));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (QQConnectException e) {
            e.printStackTrace();
        }

    }

    @RequestMapping("/qq/callback")
    public String qqCallback(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
        try {
            AccessToken accessTokenObj = (new Oauth()).getAccessTokenByRequest(request);
            String accessToken = null,openID = null;
            long tokenExpireIn = 0L;

            if (accessTokenObj.getAccessToken().equals("")) {
//                我们的网站被CSRF攻击了或者用户取消了授权
//                做一些数据统计工作
                log.info("没有获取到响应参数");
            } else {
                accessToken = accessTokenObj.getAccessToken();
                tokenExpireIn = accessTokenObj.getExpireIn();
                // 利用获取到的accessToken 去获取当前用的openid -------- start
                OpenID openIDObj = new OpenID(accessToken);
                openID = openIDObj.getUserOpenID();
                // 利用获取到的accessToken 去获取当前用户的openid --------- end
                UserInfo qzoneUserInfo = new UserInfo(accessToken, openID);
                UserInfoBean userInfoBean = qzoneUserInfo.getUserInfo();
                if (userInfoBean.getRet() == 0) {
                    //判断用户openid是否已经存在，不存在就保存用户的信息，否则跳转到首页
                    session.setAttribute("user", userInfoBean);
                    session.setAttribute("openid", openID);
                    //产生token，设置cookie
                    String uuid = UUID.randomUUID().toString();
                    int cookieTime = 5000; //有效时间
                    Cookie cookie = new Cookie("token", uuid); //添加用户使用chrome插件更新的cookies
                    cookie.setMaxAge(cookieTime);
                    response.addCookie(cookie);
                    tokenService.saveToken(openID, uuid); //缓存openid和token的对应关系
                    log.info("登录者信息："+userInfoBean.toString());
                } else {

                    log.info("没有获取到用户信息，原因：" + userInfoBean.getMsg());
                }
            }
        } catch (QQConnectException e) {
            e.printStackTrace();
        }
        return "redirect:/";
    }
}
