package com.mszx.hyb.ordercenter.params;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class OrderNotifyParams {
    private String orderNum;
    private Integer pay_type;
    private BigDecimal real_pay_amount;

    private String check_consume_user;
    private String pkmuser;

    private String check_code;

    private Integer check_consume_source;

    private String outOrderId;

    private Integer outid;

}
