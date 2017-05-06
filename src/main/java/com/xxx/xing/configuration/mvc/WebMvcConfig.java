package com.xxx.xing.configuration.mvc;

import com.xxx.xing.interceptor.AdminInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Created by xing on 2017/2/16.
 * admin/**目录下所有都使用AdminInterceptor
 */
@Configuration
//@EnableWebMvc
public class WebMvcConfig extends WebMvcConfigurerAdapter{

    @Autowired
    AdminInterceptor interceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(interceptor).addPathPatterns("/admin/**");
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/admin/user").setViewName("admin/user/index");
    }
}
