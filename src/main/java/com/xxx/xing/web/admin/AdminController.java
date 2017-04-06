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
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by xing on 2017/2/15.
 */
@Controller
@RequestMapping("/admin/user")
public class AdminController {


    @Autowired
    AdminService adminService;

    @ModelAttribute("dminObj")
    public void getAdmin(Model model) {
        Admin admin = new Admin();
        model.addAttribute("adminObj", admin);
    }

    @RequestMapping("")
    public String index() {
        return "admin/user/index";
    }


    @RequestMapping("/userlist")
    @ResponseBody
    public Map<String, Object> getUsers(int draw, int start, int length) {
        Map map = new HashMap<String, Object>();
        if (start != 0) {
            start = start / length;
        }
        Sort sort = new Sort(Sort.Direction.DESC, "id");
        Pageable pageable = new PageRequest(start, length, sort);
        Page<Admin> adminPage = adminService.findAll(pageable);

        map.put("draw", draw);
        map.put("recordsTotal", adminPage.getTotalPages()); //数据库里总共记录数
        map.put("recordsFiltered", adminPage.getTotalElements());//返回的是过滤后的记录数
        map.put("data", adminPage.getContent());

        map.put("title", "管理员列表");
        return map;
    }


    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public String save(@Valid Admin admin, BindingResult bindingResult, String confirmpassword, Model model) {
        String message = null;
        if (admin.getId() != null) {  //编辑
            model.addAttribute("title", "编辑管理员");
        } else {  //添加
            admin.setCtime(new Date());
            model.addAttribute("title", "添加管理员");
        }
        //数据验证
        if (bindingResult.hasErrors()) {
            message = bindingResult.getFieldError().getDefaultMessage();
            model.addAttribute("adminObj", admin);
            model.addAttribute("message", message);
            return "admin/user/item";
        }

        if ("".equals(admin.getPassword()) && admin.getId() != null) {
            admin.setPassword(null);
        } else {
            //验证密码
            boolean isError = false;
            if (!admin.getPassword().equals(confirmpassword)) {
                isError = true;
                message = "2次密码不一致";
            }
            if (admin.getPassword().length() > 11 || admin.getPassword().length() < 6) {
                isError = true;
                message = "密码必须6-11个字符";
            }
            if (isError) {
                model.addAttribute("adminObj", admin);
                model.addAttribute("message", message);
                return "admin/user/item";
            }
            String salt = RandomStringUtils.randomAlphanumeric(20);
            Md5PasswordEncoder encoder = new Md5PasswordEncoder();
            String password = encoder.encodePassword(admin.getPassword(), salt);
            admin.setSalt(salt);
            admin.setPassword(password);
        }
        admin.setStatus(1);
        admin.setSuper(false);
        adminService.save(admin);
        return "redirect:/admin/user";
    }

    @RequestMapping("/add")
    public String add(Model model) {
        model.addAttribute("title", "添加管理员");
        return "admin/user/item";
    }

    @RequestMapping("/edit/{id}")
    public String getItem(@PathVariable() Integer id, Model model) {
        Admin admin = adminService.findById(id);
        model.addAttribute("adminObj", admin);
        model.addAttribute("title", "编辑管理员");
        return "admin/user/item";
    }

    @RequestMapping("/delete")
    @ResponseBody
    public Result delete(Integer id) {
        adminService.delete(id);
        return new Result(true, "删除成功");
    }
}
