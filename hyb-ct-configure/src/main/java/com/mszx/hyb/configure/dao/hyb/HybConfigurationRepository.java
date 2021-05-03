package com.mszx.hyb.configure.dao.hyb;

import com.mszx.hyb.configure.model.db.hyb.HybConfiguration;
import com.mszx.hyb.configure.model.db.paycenter.PayMerchant;
import org.springframework.data.jpa.repository.JpaRepository;
//123123
public interface HybConfigurationRepository extends JpaRepository<HybConfiguration, Integer> {
}
