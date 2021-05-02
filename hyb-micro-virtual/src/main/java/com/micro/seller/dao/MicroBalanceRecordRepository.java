package com.micro.seller.dao;

import com.micro.seller.entity.db.MicroBalanceRecord;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MicroBalanceRecordRepository extends JpaRepository<MicroBalanceRecord, Integer> {

}
