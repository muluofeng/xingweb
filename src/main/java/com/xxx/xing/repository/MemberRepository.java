package com.xxx.xing.repository;

import com.xxx.xing.vo.MemberProvinceVO;

import java.util.List;

/**
 * @author xing
 * @Created by 2017-03-29 下午2:57.
 */
public interface MemberRepository {
    List<MemberProvinceVO>  countAllByProvince();
}
