package com.micro.seller.dao;

import com.micro.seller.entity.db.HybMemberInviter;
import com.micro.seller.entity.db.HybPushRecords;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HybPushRecordsRepository extends JpaRepository<HybPushRecords, Integer> {

}
