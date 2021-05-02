package com.micro.seller.dao;

import com.micro.seller.entity.db.HybBalance;
import com.micro.seller.entity.db.HybBalanceChangeRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;

public interface HybBalanceChangeRecordRepository  extends JpaRepository<HybBalanceChangeRecord, Integer> {

    @Query("select count(u) from HybBalanceChangeRecord u where u.pkregister =?1 and u.orderTime > ?2 and u.orderTime < ?3")
    int countTodayUser(String pkregister, Date startat, Date endat);
}
