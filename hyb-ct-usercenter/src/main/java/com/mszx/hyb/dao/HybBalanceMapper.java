package com.mszx.hyb.dao;

import com.mszx.hyb.entity.HybBalance;
import org.apache.ibatis.annotations.Param;

import java.sql.SQLException;

public interface HybBalanceMapper {

    int insertSelective(HybBalance record) throws SQLException;

    HybBalance selectByPrimaryKey(String pkbalance) throws SQLException;

    HybBalance selectByPkmuserAndPkregister(@Param("pkmuser") String pkmuser, @Param("pkregister") String pkregster) throws SQLException;

    int updateBalanceByPK(HybBalance hybBalance);

    int updateBalanceByPkmuserAndPkregister(HybBalance hybBalance);
}
