package com.micro.seller.dao;

import com.micro.seller.entity.db.HybMemberInviter;
import com.micro.seller.entity.db.HybRegister;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface HybMemberInviterRepository extends JpaRepository<HybMemberInviter, Integer> {

    @Query("select u.subUser from HybMemberInviter u where u.parentUser.phoneno =?1 and u.parentUser.appid = 'hyb'")
    List<HybRegister> findRegisterByParentPhone(String phoneno);

    @Query("select u.subUser from HybMemberInviter u where u.parentUser.pkregister =?1 and u.parentUser.appid = 'hyb'")
    List<HybRegister> findRegisterByParentPkRegister(String pkregister);
}
