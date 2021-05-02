package com.micro.seller.entity.db;


import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "hyb_micro_balance_records")
public class MicroBalanceRecord {

    //0: 余额记录，1：宝石记录  2: 钻石记录
    public enum TYPE{
        MoneyRecord(0), GemRecord(1), DiamondRecord(2);
        private int value;

        private TYPE(int v) {
            this.value = v;
        }

        public int getValue() {
            return value;
        }

    }

    public enum FlowType {
        InFlow(0), OutFlow(1);
        private int value;

        private FlowType(int v) {
            this.value = v;
        }

        public int getValue() {
            return value;
        }

    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer balance_record_id;

    @Column
    private String pkregister;

    @Column
    private Integer type;

    @Column
    private Integer flow_type;

    @Column
    private String order_title;

    @Column
    private BigDecimal amount;

    @Column
    private Date created_at;

    public String getPkregister() {
        return pkregister;
    }

    public void setPkregister(String pkregister) {
        this.pkregister = pkregister;
    }

    public Integer getFlow_type() {
        return flow_type;
    }

    public void setFlow_type(Integer flow_type) {
        this.flow_type = flow_type;
    }

    public Integer getBalance_record_id() {
        return balance_record_id;
    }

    public void setBalance_record_id(Integer balance_record_id) {
        this.balance_record_id = balance_record_id;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getOrder_title() {
        return order_title;
    }

    public void setOrder_title(String order_title) {
        this.order_title = order_title;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Date getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Date created_at) {
        this.created_at = created_at;
    }
}
