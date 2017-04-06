package com.xxx.xing.service;

import com.xxx.xing.dao.MemberDAO;
import com.xxx.xing.vo.MemberProvinceVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author xing
 * @Created by 2017-03-29 下午2:40.
 */
@Service
public class MemberService {
    @Autowired
    private MemberDAO memberDAO;
    public List<MemberProvinceVO> getMemberCountByProvince(){
        return memberDAO.countAllByProvince();
    }
}
