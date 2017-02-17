package com.xxx.xing.web.admin;

import com.xxx.xing.common.Result;
import com.xxx.xing.entity.Admin;
import com.xxx.xing.service.AdminService;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * Created by xing on 2017/2/15.
 */
@Controller
@RequestMapping("/admin/user")
public class AdminController {


    @ModelAttribute("dminObj")
    public void getAdmin(Model model){
        Admin admin=new Admin();
        model.addAttribute("adminObj",admin);
    }

    @Autowired
    AdminService adminService;

    @RequestMapping("")
    public String index(){
        return "admin/user/index";
    }


    @RequestMapping("/userlist")
    @ResponseBody
    public Map<String, Object> getUsers(int draw,int start,int length){
        Map  map=new HashMap<String,Object>();
        if(start!=0){
            start=start/length;
        }
        Sort sort=new Sort(Sort.Direction.DESC,"id");
        Pageable  pageable=new PageRequest(start,length,sort);
        Page<Admin> adminPage=adminService.findAll(pageable);

        map.put("draw",draw);
        map.put("recordsTotal", adminPage.getTotalPages()); //数据库里总共记录数
        map.put("recordsFiltered",adminPage.getTotalElements());//返回的是过滤后的记录数
        map.put("data", adminPage.getContent());
        return map;
    }

    @RequestMapping("/add")
    public String add(){
        return "admin/user/item";
    }

    @RequestMapping(value = "/save",method = RequestMethod.POST)
    public String save(Admin admin,String confirmpassword){
        admin.setSuper(false);

        String salt= RandomStringUtils.randomAlphanumeric(20);
        Md5PasswordEncoder encoder=new Md5PasswordEncoder();
        String password=encoder.encodePassword(admin.getPassword(),salt);
        admin.setSalt(salt);
        admin.setPassword(password);
        admin.setStatus(0);
        admin.setSuper(false);
        adminService.save(admin);
        return "redirect:/admin/user";
    }


    @RequestMapping("/item")
    public String getItem(){
        return "admin/user/item";
    }

    @RequestMapping("/delete")
    @ResponseBody
    public Result  delete(Integer id){
        adminService.delete(id);
        return new Result(true,"删除成功");
    }
}
