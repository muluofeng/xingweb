package com.xxx.xing.repository.imp;

import com.xxx.xing.repository.MemberRepository;
import com.xxx.xing.vo.MemberProvinceVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.util.List;

/**
 * @author xing
 * @Created by 2017-03-29 下午2:58.
 */
@Repository("memberDAOImpl")
public class MemberRepositoryImpl implements MemberRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<MemberProvinceVO> countAllByProvince() {
        String sql = "select m.province as name,COUNT(*) as value from member as m  GROUP BY m.province  ORDER BY value DESC";
        BeanPropertyRowMapper rowMapper=BeanPropertyRowMapper.newInstance(MemberProvinceVO.class);
        List<MemberProvinceVO> memberProvinceVOS = jdbcTemplate.query(sql, rowMapper);
        return memberProvinceVOS;
    }
}
