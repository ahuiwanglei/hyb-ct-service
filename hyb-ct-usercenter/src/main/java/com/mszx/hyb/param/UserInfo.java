package com.mszx.hyb.param;


	import lombok.Data;

    import java.util.Date;

@Data
public class UserInfo {
    private String pkregister;
    private String phoneno;
    private String email;
    private String loginpassword;
    private String newpassword;
    private String newphoneno;
    private String newpaypassword;
    private String paypassword;
    private String position;
    private String nickname;
    private String registerdate;
    private String cpuid; //设备号
    private String extralparam; // 定位用户手机登录地址
    private String nativeLoginPassword;
    private Integer uid;
    private String wx_openid;
    private String qq_openid;
    private String bgpic;
    private String appid;
    private String pushId;
    private String code;

    private String headimg;
    public UserInfo() {

    }

    public UserInfo(String phoneno) {
        this.phoneno = phoneno;
    }

    public UserInfo(String pkregister, String wx_openid, String qq_openid, String nickname, String position) {
        this.pkregister = pkregister;
        this.wx_openid = wx_openid;
        this.qq_openid = qq_openid;
        this.nickname = nickname;
        this.position = position;
    }

}

