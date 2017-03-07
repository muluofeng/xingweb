package com.xxx.xing.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.ErrorAttributes;
import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * Created by xing on 2017/2/23.
 */
@Controller
public class MainSiteErrorController implements ErrorController {

    private static final  String ERROR_PATH="/error";

    private ErrorAttributes  attributes;

    @Autowired
    public MainSiteErrorController(ErrorAttributes errorAttributes){
        this.attributes=errorAttributes;
    }

    /**
     * 获取错误信息
     * @param request
     * @return
     */
    private Map<String,Object> getErrorAttributes(HttpServletRequest request){
        RequestAttributes requestAttribute=new ServletRequestAttributes(request);
        return this.attributes.getErrorAttributes(requestAttribute,true);
    }

    @RequestMapping(value = ERROR_PATH)
    public String handleError(HttpServletRequest request, HttpServletResponse response){
        Map<String,Object>  model=getErrorAttributes(request);
        System.out.println(model);
        return "admin/error";
    }

    @Override
    public String getErrorPath() {
        return ERROR_PATH;
    }
}
