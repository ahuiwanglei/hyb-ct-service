package com.micro.seller.dao;

import com.micro.seller.entity.db.HybRegister;
import com.micro.seller.entity.db.MicroProductBuyRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MicroProductBuyRecordRepository  extends JpaRepository<MicroProductBuyRecord, Integer> {


    @Query("select u from MicroProductBuyRecord u where u.pkregister in (?1) and u.status = 1")
    List<MicroProductBuyRecord> findPkregistersByRecords(List<String> ids);

    @Query("select u from MicroProductBuyRecord u where u.status = 1")
    List<MicroProductBuyRecord>  findByBuyRecord();
}
