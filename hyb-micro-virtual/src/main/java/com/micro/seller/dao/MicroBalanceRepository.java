package com.micro.seller.dao;

import com.micro.seller.entity.db.HybPushRecords;
import com.micro.seller.entity.db.MicroBalance;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MicroBalanceRepository  extends JpaRepository<MicroBalance, Integer> {

    MicroBalance findByPkregister(String pkregister);


}
