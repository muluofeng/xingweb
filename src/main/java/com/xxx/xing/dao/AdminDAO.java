package com.xxx.xing.dao;

import com.xxx.xing.entity.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by xing on 2017/2/15.
 */
public interface AdminDAO  extends JpaRepository<Admin,Integer>{

    Admin findByLoginname(String loginname);

}
