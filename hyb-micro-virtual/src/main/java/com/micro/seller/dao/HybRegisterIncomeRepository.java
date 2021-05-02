package com.micro.seller.dao;

import com.micro.seller.entity.db.HybRegister;
import com.micro.seller.entity.db.HybRegisterIncomeBak;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface HybRegisterIncomeRepository extends JpaRepository<HybRegisterIncomeBak, Integer> {

}
