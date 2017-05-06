package com.xxx.xing.entity;

import javax.persistence.*;

/**
 * @author xing
 * @Created by 2017-04-01 上午9:44.
 */
@Entity
public class Bookmark {
    @Column(name = "userid")
    private String userId;
    private String title;
    private String content;
    private String url;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    public Bookmark(String userId, String title, String content, String url) {
        this.userId = userId;
        this.title = title;
        this.content = content;
        this.url = url;
    }

    public Bookmark() {
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
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
        return "["+userId+","
                +title+","
                +content+","
                +url +"]";
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Bookmark bookmark = (Bookmark) o;

        if (id != bookmark.id) return false;
        if (userId != null ? !userId.equals(bookmark.userId) : bookmark.userId != null) return false;
        if (title != null ? !title.equals(bookmark.title) : bookmark.title != null) return false;
        if (content != null ? !content.equals(bookmark.content) : bookmark.content != null) return false;
        if (url != null ? !url.equals(bookmark.url) : bookmark.url != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (userId != null ? userId.hashCode() : 0);
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (content != null ? content.hashCode() : 0);
        result = 31 * result + (url != null ? url.hashCode() : 0);
        return result;
    }
}
