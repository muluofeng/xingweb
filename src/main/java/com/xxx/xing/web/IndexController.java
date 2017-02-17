package com.xxx.xing.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by xing on 2017/2/15.
 */
@Controller
@RequestMapping("/admin")
public class IndexController {
    @RequestMapping("/index")
    public String index(){
        return "admin/index";
    }

    @RequestMapping("/login")
    public String login(){
        return "admin/login";
    }
}
