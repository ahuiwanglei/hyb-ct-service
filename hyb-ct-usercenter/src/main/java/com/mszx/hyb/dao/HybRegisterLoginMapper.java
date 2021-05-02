package com.mszx.hyb.dao;

import com.mszx.hyb.entity.HybRegisterLogin;

public interface HybRegisterLoginMapper {
    int deleteByPrimaryKey(String loginid);

    int insert(HybRegisterLogin record);

    int insertSelective(HybRegisterLogin record);

    HybRegisterLogin selectByPrimaryKey(String loginid);

    int updateByPrimaryKeySelective(HybRegisterLogin record);

    int updateByPrimaryKey(HybRegisterLogin record);
    HybRegisterLogin selectByPkregisterKey(String pkregister);
}