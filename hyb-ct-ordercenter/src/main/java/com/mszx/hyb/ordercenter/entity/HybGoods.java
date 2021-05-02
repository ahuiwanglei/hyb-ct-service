package com.mszx.hyb.ordercenter.entity;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class HybGoods {

    private Integer order_goods_id;
    private Integer orderid;
    private Integer goodsid;
    private String goods_pic;
    private Integer goods_num;
    private BigDecimal goods_price;
    private String goods_name;
    private Date update_at;
    private Date create_at;
}
