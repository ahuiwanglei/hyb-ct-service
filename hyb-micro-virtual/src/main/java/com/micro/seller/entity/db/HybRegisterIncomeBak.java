package com.micro.seller.entity.db;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.dao.DataAccessException;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "hyb_register_income_bak")
public class HybRegisterIncomeBak {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid")
    private String pkincomebak;

    @Column
    private String pkregister;

    @Column
    private BigDecimal amount;

    @Column
    private Date created_at;

    public String getPkincomebak() {
        return pkincomebak;
    }

    public void setPkincomebak(String pkincomebak) {
        this.pkincomebak = pkincomebak;
    }

    public String getPkregister() {
        return pkregister;
    }

    public void setPkregister(String pkregister) {
        this.pkregister = pkregister;
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
