package com.xxx.xing.vo;

import java.io.Serializable;

/**
 * @author xing
 * @Created by 2017-03-29 下午2:47.
 */
public class MemberProvinceVO  implements Serializable{
    private String name;
    private Integer value;

    public MemberProvinceVO() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }
}
