package com.mszx.hyb.configure.model.db.hyb.wx;


import lombok.Data;
import org.hibernate.annotations.CollectionId;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Data
@Entity
@Table(name="hyb_wx_balance")
public class HybWxBalance {

    @Id
//    @GeneratedValue(strategy= GenerationType.AUTO)
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    @Column(name = "pkwxbalance")
    private String pkwxbalance;

    @Column
    private String openid;

    @Column
    private BigDecimal balance;

    @Column
    private BigDecimal try_money;

    @Column
    private BigDecimal freeze_balance;

    @Column
    private BigDecimal total_send_amount;

    @Column
    private BigDecimal total_get_amount;

    @Column
    private Date createtime;


}
