package com.micro.seller.entity.db;

import org.hibernate.annotations.Where;

import javax.persistence.*;

@Entity
@Table(name = "hyb_register")
public class HybRegister {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String pkregister;

    @Column
    private String phoneno;

    @Column
    @Where(clause = "appid='hyb'")
    private String appid;

    @Column
    private Integer user_source_type;

    public Integer getUser_source_type() {
        return user_source_type;
    }

    public void setUser_source_type(Integer user_source_type) {
        this.user_source_type = user_source_type;
    }

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getPkregister() {
        return pkregister;
    }

    public void setPkregister(String pkregister) {
        this.pkregister = pkregister;
    }

    public String getPhoneno() {
        return phoneno;
    }

    public void setPhoneno(String phoneno) {
        this.phoneno = phoneno;
    }
}
