package com.micro.seller.entity.db;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;

@Entity
@Table(name = "hyb_member_inviter")
public class HybMemberInviter {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotFound(action = NotFoundAction.IGNORE)
    @ManyToOne(cascade = {CascadeType.REFRESH, CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "subuserid")
    private HybRegister subUser;

    @NotFound(action = NotFoundAction.IGNORE)
    @ManyToOne(cascade = {CascadeType.REFRESH, CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "parentuserid")
    private HybRegister parentUser;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public HybRegister getSubUser() {
        return subUser;
    }

    public void setSubUser(HybRegister subUser) {
        this.subUser = subUser;
    }

    public HybRegister getParentUser() {
        return parentUser;
    }

    public void setParentUser(HybRegister parentUser) {
        this.parentUser = parentUser;
    }
}
