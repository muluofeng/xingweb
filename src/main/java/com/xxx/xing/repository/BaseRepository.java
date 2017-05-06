package com.xxx.xing.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.io.Serializable;
import java.util.Collection;

/**
 * @author xing
 * @Created by 2017-04-24 上午11:01.
 */
@NoRepositoryBean
public interface BaseRepository<T, ID extends Serializable> extends JpaRepository<T, ID> {

    /**
     * 根据ID删除
     *
     * @param ids
     */
    void deleteByIds(Class<T> clazz,Collection<ID> ids);

    /**
     * 根据ID更新非空字段
     *
     * @param entity
     */
    void updateSelective(T entity);

}
