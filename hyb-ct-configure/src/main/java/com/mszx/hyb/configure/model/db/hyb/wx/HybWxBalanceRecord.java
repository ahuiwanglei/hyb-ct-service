package com.mszx.hyb.configure.model.db.hyb.wx;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Data
@Entity
@Table(name="hyb_wx_balance_record")
public class HybWxBalanceRecord {

    public enum DealType {
        ReceiveTicket(0), BalanceToTicket(1), TicketToBalance(2), Consume(3), Recharge(4), ExpiredBack(5);
        private int value;

        private DealType(int v) {
            this.value = v;
        }

        public int getValue() {
            return value;
        }
    }


    @Id
//    @GeneratedValue(strategy= GenerationType.AUTO)
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    @Column(name = "pkrecord")
    private String pkrecord;

    @Column
    private String openid;

    @Column
    private Integer type;

    @Column
    private BigDecimal before_balance;

    @Column
    private BigDecimal after_balance;

    @Column
    private BigDecimal order_amount;

    @Column
    private String  order_subject;

    @Column
    private Integer change_type;

    @Column
    private String deal_type;

    @Column
    private String outid;

    @Column
    private Date createtime;
}
