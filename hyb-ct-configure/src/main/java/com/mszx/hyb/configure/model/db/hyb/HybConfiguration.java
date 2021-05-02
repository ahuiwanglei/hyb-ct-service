package com.mszx.hyb.configure.model.db.hyb;


import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Data
@Entity
@Table(name="hyb_configuration")
public class HybConfiguration {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    @Column(name = "pkconfiguration")
    private String id;

    @Column
    private String config_key;

    @Column
    private String config_val;

    @Column
    private Integer config_type;

    @Column
    private Integer config_staut;

    @Column
    private String config_explain;

    @Column
    private String remark;
}
