package com.xxx.xing.configuration.security;

import com.fasterxml.jackson.core.JsonEncoding;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.xxx.xing.constant.RoleKeys;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by xing on 2017/2/16.
 */
@Configuration
@EnableWebSecurity
@Order(SecurityProperties.ACCESS_OVERRIDE_ORDER)
public class SecurityConfig  extends WebSecurityConfigurerAdapter{


    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/assets/**","/js/**","/css/**","/img/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                // 路径权限
                .authorizeRequests()
                .antMatchers("/admin/login").permitAll()
                .antMatchers("/admin/dologin").permitAll()
                .antMatchers("/**").hasRole(RoleKeys.ADMIN)
                // 登录
                .and()
                .formLogin()
                .loginPage("/admin/login").loginProcessingUrl("/admin/dologin")
                .usernameParameter("loginname").passwordParameter("password")
                .successHandler(new LoginSuccessHandler())
                .failureHandler(new LoginFailureHandler())
                .permitAll()
                // 退出
                .and()
                .logout()
                .logoutUrl("/admin/logout")
                .logoutSuccessUrl("/admin/login")
                .permitAll();
    }

    class LoginSuccessHandler implements AuthenticationSuccessHandler {

        @Override
        public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
            Map<String, Object> data = new HashMap<String, Object>();
            data.put("status", 1);
            data.put("url", "/");
            ObjectMapper mapper = new ObjectMapper();
            JsonGenerator generator = mapper.getFactory().createGenerator(response.getOutputStream(), JsonEncoding.UTF8);
            generator.writeObject(data);
            generator.flush();
            if (!generator.isClosed()) {
                generator.close();
            }
        }
    }

    class LoginFailureHandler implements AuthenticationFailureHandler {

        @Override
        public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws IOException, ServletException {
            e.printStackTrace();
            Map<String, Object> data = new HashMap<String, Object>();
            data.put("status", 0);
            data.put("info", e.getMessage());
            ObjectMapper mapper = new ObjectMapper();
            JsonGenerator generator = mapper.getFactory().createGenerator(response.getOutputStream(), JsonEncoding.UTF8);
            generator.writeObject(data);
            generator.flush();
            if (!generator.isClosed()) {
                generator.close();
            }
        }
    }
}
