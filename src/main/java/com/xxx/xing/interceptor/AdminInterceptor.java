package com.xxx.xing.interceptor;

import com.xxx.xing.configuration.security.AdminUser;
import com.xxx.xing.constant.SessionKeys;
import org.apache.tomcat.util.buf.HexUtils;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.DigestUtils;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by xing on 2017/2/16.
 * 将用户登录后的数据放入session
 */
@Component
public class AdminInterceptor extends HandlerInterceptorAdapter{
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        try{
            AdminUser  adminUser= (AdminUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            if(adminUser.getUsername().matches("^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$")){
                String avatar="http://www.gravatar.com/avatar/"+ DigestUtils.md5DigestAsHex(adminUser.getUsername().toLowerCase().getBytes())+"?s=";
                adminUser.setAvatar(avatar);
            }
            request.getSession().setAttribute(SessionKeys.ADMIN_USER,adminUser);
        }catch (Exception e){

        }
        return  true;
    }
}
