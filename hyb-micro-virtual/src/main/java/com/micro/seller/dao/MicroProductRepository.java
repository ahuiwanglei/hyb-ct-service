package com.micro.seller.dao;

import com.micro.seller.entity.db.MicroProduct;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MicroProductRepository  extends JpaRepository<MicroProduct, Integer> {

}
