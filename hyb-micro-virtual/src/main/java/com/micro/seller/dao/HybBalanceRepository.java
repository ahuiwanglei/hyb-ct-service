package com.micro.seller.dao;

import com.micro.seller.entity.db.HybBalance;
import com.micro.seller.entity.db.HybDictionary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.util.List;

public interface HybBalanceRepository extends JpaRepository<HybBalance, Integer> {

    @Query("select u from HybBalance u where u.pkmuser =?2 and u.pkregister =?1 ")
    HybBalance findPkRegisterBalance(String pkregister, String pkmuser);

    @Query(value = "select pksystem from hyb_system limit 0,1", nativeQuery = true)
    String hybSystem();

    @Query("update HybBalance u set u.totalinterest =?1 where u.pkbalance = ?2")
    @Modifying
    int updateBalanceByPKbalanceId(BigDecimal totalinterest, String pkbalance);

    @Query(value = "select balance.pkregister,register.phoneno, register.bankusername,balance.virtualbalance  , a.one " +
            " from hyb_balance balance " +
            " left join ( select parentuserid, count(1) one  from hyb_member_inviter  group by parentuserid " +
            ") a on a.parentuserid = balance.pkregister " +
            "left join hyb_register register on register.pkregister = balance.pkregister " +
            "where balance.pkmuser = '8d91228426a344c68e0d4147b3cd7a80' and register.user_source_type =1  order by balance.virtualbalance desc limit 0,100", nativeQuery = true)
    List<Object> get100Data();

    @Query(value = "select count(1) from (" +
            "select * from hyb_member_inviter where subuserid  in ( " +
            "   select parentuserid from hyb_member_inviter " +
            ") and parentuserid = ?1 ) a ", nativeQuery = true)
    Object getSignleUser(String userid);

    @Query(
            value = "select phoneno, bankusername from hyb_member_inviter \n" +
            "left join hyb_register on pkregister = subuserid\n" +
            " where parentuserid = ?1 limit 0,7", nativeQuery = true)
    List<Object> getSubUser(String userid);
}
