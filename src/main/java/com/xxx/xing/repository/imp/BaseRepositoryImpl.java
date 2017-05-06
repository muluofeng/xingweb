package com.xxx.xing.repository.imp;

/**
 * @author xing
 * @Created by 2017-04-24 上午11:02.
 */

import com.xxx.xing.repository.BaseRepository;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import javax.persistence.Column;
import javax.persistence.EntityManager;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Query;
import javax.persistence.Table;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

/**
 * @author loocao
 * @since 2017/1/11
 */
public class BaseRepositoryImpl<T, ID extends Serializable> extends SimpleJpaRepository<T, ID>
        implements BaseRepository<T, ID> {
    private Log log = LogFactory.getLog(BaseRepositoryImpl.class);

    private JpaEntityInformation<T, ID> ei;
    private EntityManager em;

    public BaseRepositoryImpl(JpaEntityInformation<T, ID> entityInformation, EntityManager entityManager) {
        super(entityInformation, entityManager);
        this.ei = entityInformation;
        this.em = entityManager;
    }

    public BaseRepositoryImpl(Class<T> domainClass, EntityManager em) {
        super(domainClass, em);
    }

    @Override
    @Transactional(readOnly = false)
    public void deleteByIds(Class<T> clazz,Collection<ID> ids) {
        T entity=null;
        for(Object id:ids){
            entity=em.find(clazz,id);
            em.remove(entity);
        }
    }

    @Override
    @Transactional(readOnly = false)
    public void updateSelective(T entity) {
        // entity不能为空
        Assert.notNull(entity, "The entity must not be null!");

        StringBuilder sql = new StringBuilder("update ");
        Class<T> entityClass = this.getDomainClass();
        Table table = entityClass.getDeclaredAnnotation(Table.class);
        String tableName = entityClass.getSimpleName().toLowerCase();
        if (table != null) {
            tableName = table.name();
        }
        sql.append(tableName).append(" set ");
        Field[] fields = entityClass.getDeclaredFields();
        try {
            // 如果ID未配置或为null，则不做更新
            if (ei.getIdAttribute() == null || ei.getId(entity) == null) {
                return;
            }
            // 获取ID
            ID id = ei.getId(entity);
            Assert.notNull(id, "The entity id must not be null!");

            // 生成set
            List<String> sets = new ArrayList<>();
            List<Object> params = new ArrayList<>();
            int i = 1;
            for (Field field : fields) {
                // 排除静态变量
                if (Modifier.isStatic(field.getModifiers()) || field.getName().equals("id")) {
                    continue;
                }

                field.setAccessible(true);// 设置属性为可访问
                Object value = field.get(entity);// 获取值
                if (value != null) {
                    // 获取字段名
                    String columnName = null;
                    Column column = field.getAnnotation(Column.class);
                    if (column == null) {
                        JoinColumn joinColumn = field.getAnnotation(JoinColumn.class);
                        if (joinColumn != null && value != null) {
                            columnName = joinColumn.name();
                            Class fieldClass = field.getDeclaringClass();
                            value = fieldClass.getMethod("getId").invoke(value);
                        }
                    } else {
                        columnName = column.name();
                    }

                    if (StringUtils.isEmpty(columnName)) {
                        columnName = field.getName();
                    }
                    sets.add(columnName + "=?" + (i++));
                    params.add(value);
                }
            }
            sql.append(String.join(",", sets)).append(" where id = ").append(id);
            Query query = em.createNativeQuery(sql.toString());
            // 设置查询参数
            for (i = 0; i < params.size(); i++) {
                query.setParameter(i + 1, params.get(i));
            }
            query.executeUpdate();
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            log.error(e.getMessage(), e);
        }
    }

}
