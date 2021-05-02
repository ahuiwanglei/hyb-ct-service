package com.mszx.hyb.dao;

import com.mszx.hyb.entity.HybIntegralRecord;

public interface HybIntegralRecordMapper {
    int deleteByPrimaryKey(Integer pkintegralrecord);

    int insert(HybIntegralRecord record);

    int insertSelective(HybIntegralRecord record);

    HybIntegralRecord selectByPrimaryKey(Integer pkintegralrecord);

    int updateByPrimaryKeySelective(HybIntegralRecord record);

    int updateByPrimaryKey(HybIntegralRecord record);
}