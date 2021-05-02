package com.micro.seller.entity.db;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "hyb_balance")
public class HybBalance {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String pkbalance;

    @Column
    private String pkregister;

//    @NotFound(action = NotFoundAction.IGNORE)
//    @ManyToOne(cascade = {CascadeType.REFRESH, CascadeType.PERSIST, CascadeType.MERGE})
//    @JoinColumn(name = "pkregister")
//    private HybRegister hybRegister;

    @Column
    private String pkmuser;

    @Column
    private BigDecimal totalinterest;

    @Column
    private BigDecimal balance;

    @Column
    private BigDecimal virtualbalance;

    public String getPkregister() {
        return pkregister;
    }

    public void setPkregister(String pkregister) {
        this.pkregister = pkregister;
    }

    public String getPkbalance() {
        return pkbalance;
    }

    public void setPkbalance(String pkbalance) {
        this.pkbalance = pkbalance;
    }

//    public HybRegister getHybRegister() {
//        return hybRegister;
//    }
//
//    public void setHybRegister(HybRegister hybRegister) {
//        this.hybRegister = hybRegister;
//    }

    public String getPkmuser() {
        return pkmuser;
    }

    public void setPkmuser(String pkmuser) {
        this.pkmuser = pkmuser;
    }

    public BigDecimal getTotalinterest() {
        return totalinterest;
    }

    public void setTotalinterest(BigDecimal totalinterest) {
        this.totalinterest = totalinterest;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public BigDecimal getVirtualbalance() {
        return virtualbalance;
    }

    public void setVirtualbalance(BigDecimal virtualbalance) {
        this.virtualbalance = virtualbalance;
    }
}
