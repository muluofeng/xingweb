package com.xxx.xing.entity;

/**
 * @author xing
 * @Created by 2017-04-01 上午9:44.
 */
public class Bookmark {
    private Integer userId;
    private String title;
    private String content;
    private String url;

    public Bookmark(Integer userId, String title, String content, String url) {
        this.userId = userId;
        this.title = title;
        this.content = content;
        this.url = url;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "["+title+","
                +content+","
                +url +"]";
    }
}
