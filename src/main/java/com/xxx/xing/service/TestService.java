package com.xxx.xing.service;

import org.springframework.stereotype.Service;

/**
 * @author xing
 * @Created by 2017-05-03 上午10:12.
 */
@Service
public class TestService {
    public String sayHello(String name){
        System.out.println("TestService:sayHello");
        System.out.println("测试 aop"+name);
        return "returnValue";
    }
}
