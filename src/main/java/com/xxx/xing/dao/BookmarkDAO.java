package com.xxx.xing.dao;

import com.xxx.xing.entity.Bookmark;
import com.xxx.xing.repository.BaseRepository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author xing
 * @Created by 2017-04-20 下午1:55.
 */
public interface BookmarkDAO extends BaseRepository<Bookmark,Integer> {
    /**
     * 根据用户id获取所有的书签
     * @param userId
     * @return
     */
    public List<Bookmark> findByUserId(String userId);



}
