package com.mszx.hyb.configure.model.db.paycenter;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Data
@Entity
@Table(name="pay_merchant")
public class PayMerchant {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    private Integer id;

    @Column
    private String merchant_token;

    @Column
    private String merchant_pass;

    @Column
    private String outid;
}
