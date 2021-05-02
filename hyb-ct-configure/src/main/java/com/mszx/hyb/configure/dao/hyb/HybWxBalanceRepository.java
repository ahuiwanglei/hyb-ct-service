package com.mszx.hyb.configure.dao.hyb;

import com.mszx.hyb.configure.model.db.hyb.HybConfiguration;
import com.mszx.hyb.configure.model.db.hyb.wx.HybWxBalance;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HybWxBalanceRepository extends JpaRepository<HybWxBalance, String> {
}
