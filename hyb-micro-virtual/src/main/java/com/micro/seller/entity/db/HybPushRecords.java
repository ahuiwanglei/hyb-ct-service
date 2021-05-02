package com.micro.seller.entity.db;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name = "hyb_push_records")
public class HybPushRecords {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid")
    private String pkpushrecords;

    @Column
    private String pkmuser;

    @Column
    private String pkregister;

    @Column
    private String image;

    @Column
    private Integer type;

    @Column
    private String senddate;

    @Column
    private String remarks;

    public String getPkpushrecords() {
        return pkpushrecords;
    }

    public void setPkpushrecords(String pkpushrecords) {
        this.pkpushrecords = pkpushrecords;
    }

    public String getPkmuser() {
        return pkmuser;
    }

    public void setPkmuser(String pkmuser) {
        this.pkmuser = pkmuser;
    }

    public String getPkregister() {
        return pkregister;
    }

    public void setPkregister(String pkregister) {
        this.pkregister = pkregister;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getSenddate() {
        return senddate;
    }

    public void setSenddate(String senddate) {
        this.senddate = senddate;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
}
