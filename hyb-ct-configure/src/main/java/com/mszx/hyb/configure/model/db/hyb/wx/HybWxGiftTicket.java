package com.mszx.hyb.configure.model.db.hyb.wx;

import lombok.Data;
import org.hibernate.annotations.CollectionId;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Data
@Entity
@Table(name="hyb_wx_gift_ticket")
public class HybWxGiftTicket {

    public static final Integer expired_day = 1;

    public enum PayStatus {
        NoPay(0), Payed(1);
        private int value;

        private PayStatus(int v) {
            this.value = v;
        }

        public int getValue() {
            return value;
        }

    }

    //0:直接购买，1余额兑换，3：平台券, 2：体验券
    public enum SourceFrom {
        Buy(0), BalanceToTicket(1), PlatformTicket(3), TryTicket(2);
        private int value;

        private SourceFrom(int v) {
            this.value = v;
        }

        public int getValue() {
            return value;
        }

    }


    public enum Status {
        NoReceiver(0), Received(1), Expired(2);
        private int value;

        private Status(int v) {
            this.value = v;
        }

        public int getValue() {
            return value;
        }

    }


    @Id
//    @GeneratedValue(strategy= GenerationType.AUTO)
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    @Column(name = "pkticket")
    private String pkticket;

    @Column
    private String title;

    @Column
    private String from_user;

    @Column
    private BigDecimal amount;

    @Column
    private Integer status;

    @Column
    private Integer source_from;

    @Column
    private Integer pay_status;

    @Column
    private Date createtime;


}
