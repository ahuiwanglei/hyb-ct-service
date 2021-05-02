package com.micro.seller.entity.db;

import javax.persistence.*;


@Entity
@Table(name = "hyb_dictionary")
public class HybDictionary {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String pkdic;

    @Column
    private String tbcode;

    @Column
    private String code;

    @Column
    private String name;

    @Column
    private String orderby;

    @Column
    private String remark;

    public String getPkdic() {
        return pkdic;
    }

    public void setPkdic(String pkdic) {
        this.pkdic = pkdic;
    }

    public String getTbcode() {
        return tbcode;
    }

    public void setTbcode(String tbcode) {
        this.tbcode = tbcode;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOrderby() {
        return orderby;
    }

    public void setOrderby(String orderby) {
        this.orderby = orderby;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
