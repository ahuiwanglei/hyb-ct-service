package com.mszx.hyb.dao;

import com.mszx.hyb.entity.HybWxFan;
import com.mszx.hyb.entity.HybWxUser;

public interface HybWxFanMapper {
    int deleteByPrimaryKey(Integer pkfansid);

    int insert(HybWxFan record);

    int insertSelective(HybWxFan record);

    HybWxFan selectByPrimaryKey(Integer pkfansid);

    int updateByPrimaryKeySelective(HybWxFan record);

    int updateByPrimaryKey(HybWxFan record);

    HybWxFan selectByOpenId(String openid);

    int updateIntegralById(HybWxFan hybWxFan);

    HybWxUser selectWxUserByOpenid(HybWxUser params);

    HybWxFan selectByUnionid(String unionid);

    int updateUnionidByPkregister(HybWxUser params);

}