package com.xxx.xing.service;

import com.xxx.xing.dao.AdminDAO;
import com.xxx.xing.entity.Admin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.xml.ws.Action;
import java.util.List;

/**
 * Created by xing on 2017/2/15.
 */
@Service
public class AdminService {

    @Autowired
    AdminDAO adminDAO;

    public  Admin findByLoginname(String loginname){
        return adminDAO.findByLoginname(loginname);
    }

    public Admin findById(Integer id){return adminDAO.findOne(id);}

    public Page<Admin> findAll(Pageable pageable){
        return adminDAO.findAll(pageable);
    }

    public void save(Admin admin){
        if(admin.getId()!=null){

        }else{
            adminDAO.save(admin);
        }

    }

    public void delete(Integer id){
         adminDAO.delete(id);
    }

}
