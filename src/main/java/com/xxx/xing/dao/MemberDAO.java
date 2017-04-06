package com.xxx.xing.dao;

import com.xxx.xing.entity.Member;
import com.xxx.xing.repository.MemberRepository;
import com.xxx.xing.vo.MemberProvinceVO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @author xing
 * @Created by 2017-03-29 下午2:39.
 */
public interface MemberDAO extends JpaRepository<Member,Integer> ,MemberRepository{

    public List<MemberProvinceVO> countAllByProvince();
}
