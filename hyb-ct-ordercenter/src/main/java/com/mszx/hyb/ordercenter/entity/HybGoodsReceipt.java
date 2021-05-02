package com.mszx.hyb.ordercenter.entity;

import lombok.Data;

import java.util.Date;

@Data
public class HybGoodsReceipt {

    private Integer goods_receipt_info_id;
    private Integer orderid;
    private String receipt_user_name;
    private String address;
    private String cellphone;
    private Date create_at;

}
