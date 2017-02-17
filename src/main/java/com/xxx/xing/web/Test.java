package com.xxx.xing.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by xing on 2017/2/14.
 */
@Controller
@RequestMapping("/test")
public class Test {
    @RequestMapping("/one")
    public String  one(){
        return "index";
    }

    @RequestMapping("/form")
    public String  two(){
        return "form";
    }
}
