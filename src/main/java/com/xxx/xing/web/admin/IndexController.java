package com.xxx.xing.web.admin;

import com.xxx.xing.service.MemberService;
import com.xxx.xing.vo.MemberProvinceVO;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @author xing
 * @Created by 2017-03-29 上午10:05.
 */
@Controller(value = "adminIndex")
@RequestMapping("/admin")
public class IndexController {
    private Log log = LogFactory.getLog(IndexController.class);
    @Autowired
    private MemberService memberService;


    @RequestMapping("/login")
    public String login() {
        return "admin/login";
    }

    @RequestMapping("/form")
    public String form() {
        return "admin/form";
    }

    /**
     * 后台首页
     * @return
     */
    @RequestMapping("/index")
    public String index() {
        log.info("just s test");
        return "admin/index";
    }


    /**
     * 获取会员所属省份的数据
     */
    @RequestMapping("/getCountByProvince")
    @ResponseBody
    private List<MemberProvinceVO> getMemberCountByProvince() {
        List<MemberProvinceVO> voList = memberService.getMemberCountByProvince();
        return voList;
    }
}
