package com.micro.seller.dao;

import com.micro.seller.entity.db.HybDictionary;
import com.micro.seller.entity.db.HybMemberInviter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface HybDictionaryRepository extends JpaRepository<HybDictionary, Integer> {

    @Query("select u from HybDictionary u where u.tbcode = ?1")
    List<HybDictionary> findTbCode(String root_phone_virutal_income);
}
