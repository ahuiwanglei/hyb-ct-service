package com.micro.seller.entity.db;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "hyb_micro_product_buy_records")
public class MicroProductBuyRecord {

    public enum AmountReturnStatus {
        returning(0), returnfinish(1);
        private int value;

        private AmountReturnStatus(int v) {
            this.value = v;
        }

        public int getValue() {
            return value;
        }

    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer buy_record_id;

    @NotFound(action = NotFoundAction.IGNORE)
//    @JsonSerialize(using = SingleMicroProductSerializer.class)
    @ManyToOne(cascade = {CascadeType.REFRESH, CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "product_id")
    private MicroProduct product;

    @Column
    private String pkregister;

    @Column
    private Integer return_amount_finish;

    @Column
    private BigDecimal return_amount_balance;

    @Column
    private String goods_address;

    @Column
    private Integer exchange_status;

    @Column
    private Integer status;

    @Column
    private String orderid;

    @Column
    private Date created_at;

    @Column
    private Date updated_at;

    public Integer getBuy_record_id() {
        return buy_record_id;
    }

    public void setBuy_record_id(Integer buy_record_id) {
        this.buy_record_id = buy_record_id;
    }

    public String getPkregister() {
        return pkregister;
    }

    public void setPkregister(String pkregister) {
        this.pkregister = pkregister;
    }

    public Integer getReturn_amount_finish() {
        return return_amount_finish;
    }

    public void setReturn_amount_finish(Integer return_amount_finish) {
        this.return_amount_finish = return_amount_finish;
    }

    public MicroProduct getProduct() {
        return product;
    }

    public void setProduct(MicroProduct product) {
        this.product = product;
    }

    public BigDecimal getReturn_amount_balance() {
        return return_amount_balance;
    }

    public void setReturn_amount_balance(BigDecimal return_amount_balance) {
        this.return_amount_balance = return_amount_balance;
    }

    public String getGoods_address() {
        return goods_address;
    }

    public void setGoods_address(String goods_address) {
        this.goods_address = goods_address;
    }

    public String getOrderid() {
        return orderid;
    }

    public void setOrderid(String orderid) {
        this.orderid = orderid;
    }

    public Integer getExchange_status() {
        return exchange_status;
    }

    public void setExchange_status(Integer exchange_status) {
        this.exchange_status = exchange_status;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Date created_at) {
        this.created_at = created_at;
    }

    public Date getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(Date updated_at) {
        this.updated_at = updated_at;
    }
}
