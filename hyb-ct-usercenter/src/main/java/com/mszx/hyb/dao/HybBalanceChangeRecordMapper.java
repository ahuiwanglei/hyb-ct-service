package com.mszx.hyb.dao;


import com.mszx.hyb.entity.HybBalanceChangeRecord;
import com.mszx.hyb.entity.VirtualRecord;

import java.util.List;

public interface HybBalanceChangeRecordMapper{


    int insertSelective(HybBalanceChangeRecord record);

    HybBalanceChangeRecord selectByPrimaryKey(Long changeId);


    int addVirtualRecord(VirtualRecord virtualRecord);
}