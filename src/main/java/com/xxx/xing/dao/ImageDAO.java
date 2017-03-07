package com.xxx.xing.dao;

import com.xxx.xing.entity.Admin;
import com.xxx.xing.entity.Image;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author xing
 * @Created by 2017-03-03 上午10:09.
 */
public interface ImageDAO extends JpaRepository<Image,Integer> {
    Page<Image>  findByNameLike(String name, Pageable pageable);
}
