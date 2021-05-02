package com.mszx.hyb.configure.dao.hyb;

import com.mszx.hyb.configure.model.db.hyb.HybConfiguration;
import com.mszx.hyb.configure.model.db.hyb.wx.HybWxBalanceRecord;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HybWxBalanceRecordRepository extends JpaRepository<HybWxBalanceRecord, String> {
}
