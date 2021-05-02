package com.micro.seller.dao;

import com.micro.seller.entity.db.HybMemberInviter;
import com.micro.seller.entity.db.HybRegister;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface HybRegisterRepository  extends JpaRepository<HybRegister, Integer> {

    @Query("select u from HybRegister u where u.user_source_type = 1")
    List<HybRegister> findByShenAllUser();
}
