package com.micro.seller.service;

import com.micro.seller.entity.db.MicroBalance;

public interface MicroBuyRecordService {
    void produceGem();

    MicroBalance getUserMicroBalance(String pkregister);
}
