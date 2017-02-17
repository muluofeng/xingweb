package com.xxx.xing.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by xing on 2017/2/15.
 */
@Entity
@Table(name = "admin")
public class Admin implements Serializable{
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer  id;

    private String loginname;

    private String nickname;

    private Integer status;

    @Column(name = "is_super")
    private Boolean isSuper;

    @Temporal(TemporalType.TIMESTAMP)
    private Date  ctime;

    private String password;
    private String salt;

    public Admin() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }


    public String getLoginname() {
        return loginname;
    }

    public void setLoginname(String loginname) {
        this.loginname = loginname;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Boolean getSuper() {
        return isSuper;
    }

    public void setSuper(Boolean aSuper) {
        isSuper = aSuper;
    }

    public Date getCtime() {
        return ctime;
    }

    public void setCtime(Date ctime) {
        this.ctime = ctime;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }
}
