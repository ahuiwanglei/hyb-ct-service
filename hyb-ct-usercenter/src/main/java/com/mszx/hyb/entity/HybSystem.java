package com.mszx.hyb.entity;

import java.math.BigDecimal;

public class HybSystem{
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hyb_system.pksystem
     *
     * @mbggenerated Wed Oct 21 09:45:00 CST 2015
     */
    private String pksystem;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hyb_system.sysname
     *
     * @mbggenerated Wed Oct 21 09:45:00 CST 2015
     */
    private String sysname;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hyb_system.sysversion
     *
     * @mbggenerated Wed Oct 21 09:45:00 CST 2015
     */
    private String sysversion;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hyb_system.sysaddress
     *
     * @mbggenerated Wed Oct 21 09:45:00 CST 2015
     */
    private String sysaddress;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hyb_system.sysdesc
     *
     * @mbggenerated Wed Oct 21 09:45:00 CST 2015
     */
    private String sysdesc;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hyb_system.syslogourl
     *
     * @mbggenerated Wed Oct 21 09:45:00 CST 2015
     */
    private String syslogourl;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hyb_system.sysphone
     *
     * @mbggenerated Wed Oct 21 09:45:00 CST 2015
     */
    private String sysphone;

    private String system_account;

    /**
     * 系统余额
     */
    private String balance;
    /**
     * 系统系涨利利率（百分比）
     */
    private BigDecimal interest;
    /**
     * 系统会员手机充值返利利率(百分比)
     */
    private BigDecimal mobierecharge;
    /**
     * 系统会员油卡充值返利利率(百分比)
     */
    private BigDecimal oilcardrecharge;
    /**
     * 惠员包会员积分返回比例（百分比）
     */
    private BigDecimal integral;
    /**
     * 系统会员虚拟余额比例 (百分比)
     */
    private BigDecimal virtualbalance;
    /**
     * 系统会员虚拟余额比例 (百分比)
     */
    private String exchange;
    /**
	 * @return the integral
	 */
	public BigDecimal getIntegral() {
		return integral;
	}

	/**
	 * @param integral the integral to set
	 */
	public void setIntegral(BigDecimal integral) {
		this.integral = integral;
	}

	/**
     * qq服务充值返利利率(百分比)
     */
    private BigDecimal qqrecharge;
    /**
     * 系统红利利率(千分比)
     */
    private BigDecimal systemdividend;
    /**
     * 第一商家手续费率(千分比)
     */
    private BigDecimal firstmchantpoundage;
    /**
     * 非第一商家手续费率(千分比)
     */
    private BigDecimal othermchantpoundage;
    /**
     * 第一经销商红利利率(千分比)
     */
    private BigDecimal firstdealerdividend;
    /**
     * 非第一经销商红利利率(千分比)
     */
    private BigDecimal otherdealerdividend;
    /**
     * 第一商家红利利率(千分比)thirdpoundage
     */
    private BigDecimal firstmchantdividend;
    /**
     * 第三方手续费率(千分比)
     */
    private BigDecimal thirdpoundage;
   
    private BigDecimal withdraw_start;
   
    private BigDecimal withdraw_end;
   
    private BigDecimal recharge_start;
   
    private BigDecimal recharge_end;
   
    private BigDecimal virtualbalance_mobile;
    
    private BigDecimal virtualbalance_unicom;
    
    private BigDecimal virtualbalance_telecom;
   
    public BigDecimal getVirtualbalance_mobile() {
		return virtualbalance_mobile;
	}

	public void setVirtualbalance_mobile(BigDecimal virtualbalance_mobile) {
		this.virtualbalance_mobile = virtualbalance_mobile;
	}

	public BigDecimal getVirtualbalance_unicom() {
		return virtualbalance_unicom;
	}

	public void setVirtualbalance_unicom(BigDecimal virtualbalance_unicom) {
		this.virtualbalance_unicom = virtualbalance_unicom;
	}

	public BigDecimal getVirtualbalance_telecom() {
		return virtualbalance_telecom;
	}

	public void setVirtualbalance_telecom(BigDecimal virtualbalance_telecom) {
		this.virtualbalance_telecom = virtualbalance_telecom;
	}

	public BigDecimal getWithdraw_start() {
    	return withdraw_start;
    }
	
	public void setWithdraw_start(BigDecimal withdraw_start) {
		this.withdraw_start = withdraw_start;
	}
	
	public BigDecimal getWithdraw_end() {
		return withdraw_end;
	}
	
	public void setWithdraw_end(BigDecimal withdraw_end) {
		this.withdraw_end = withdraw_end;
	}
	
	public BigDecimal getRecharge_start() {
		return recharge_start;
	}
	
	public void setRecharge_start(BigDecimal recharge_start) {
		this.recharge_start = recharge_start;
	}
	
	public BigDecimal getRecharge_end() {
		return recharge_end;
	}
	
	public void setRecharge_end(BigDecimal recharge_end) {
		this.recharge_end = recharge_end;
	}

	/**
	 * @return the balance
	 */
	public String getBalance() {
		return balance;
	}
    
	public BigDecimal getInterest() {
		return interest;
	}

	public void setInterest(BigDecimal interest) {
		this.interest = interest;
	}

	public BigDecimal getMobierecharge() {
		return mobierecharge;
	}

	public void setMobierecharge(BigDecimal mobierecharge) {
		this.mobierecharge = mobierecharge;
	}

	public BigDecimal getOilcardrecharge() {
		return oilcardrecharge;
	}

	public void setOilcardrecharge(BigDecimal oilcardrecharge) {
		this.oilcardrecharge = oilcardrecharge;
	}

	public BigDecimal getSystemdividend() {
		return systemdividend;
	}

	public void setSystemdividend(BigDecimal systemdividend) {
		this.systemdividend = systemdividend;
	}

	public BigDecimal getFirstmchantpoundage() {
		return firstmchantpoundage;
	}

	public void setFirstmchantpoundage(BigDecimal firstmchantpoundage) {
		this.firstmchantpoundage = firstmchantpoundage;
	}

	public BigDecimal getOthermchantpoundage() {
		return othermchantpoundage;
	}

	public void setOthermchantpoundage(BigDecimal othermchantpoundage) {
		this.othermchantpoundage = othermchantpoundage;
	}

	public BigDecimal getFirstdealerdividend() {
		return firstdealerdividend;
	}

	public void setFirstdealerdividend(BigDecimal firstdealerdividend) {
		this.firstdealerdividend = firstdealerdividend;
	}

	public BigDecimal getOtherdealerdividend() {
		return otherdealerdividend;
	}

	public void setOtherdealerdividend(BigDecimal otherdealerdividend) {
		this.otherdealerdividend = otherdealerdividend;
	}

	public BigDecimal getFirstmchantdividend() {
		return firstmchantdividend;
	}

	public void setFirstmchantdividend(BigDecimal firstmchantdividend) {
		this.firstmchantdividend = firstmchantdividend;
	}

	public BigDecimal getThirdpoundage() {
		return thirdpoundage;
	}

	public void setThirdpoundage(BigDecimal thirdpoundage) {
		this.thirdpoundage = thirdpoundage;
	}

	public BigDecimal getQqrecharge() {
		return qqrecharge;
	}

	public void setQqrecharge(BigDecimal qqrecharge) {
		this.qqrecharge = qqrecharge;
	}

	public BigDecimal getVirtualbalance() {
		return virtualbalance;
	}

	public void setVirtualbalance(BigDecimal virtualbalance) {
		this.virtualbalance = virtualbalance;
	}


	public String getExchange() {
		return exchange;
	}

	public void setExchange(String exchange) {
		this.exchange = exchange;
	}

	/**
	 * @param balance the balance to set
	 */
	public void setBalance(String balance) {
		this.balance = balance;
	}

	/**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hyb_system.pksystem
     *
     * @return the value of hyb_system.pksystem
     *
     * @mbggenerated Wed Oct 21 09:45:00 CST 2015
     */
    public String getPksystem() {
        return pksystem;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hyb_system.pksystem
     *
     * @param pksystem the value for hyb_system.pksystem
     *
     * @mbggenerated Wed Oct 21 09:45:00 CST 2015
     */
    public void setPksystem(String pksystem) {
        this.pksystem = pksystem == null ? null : pksystem.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hyb_system.sysname
     *
     * @return the value of hyb_system.sysname
     *
     * @mbggenerated Wed Oct 21 09:45:00 CST 2015
     */
    public String getSysname() {
        return sysname;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hyb_system.sysname
     *
     * @param sysname the value for hyb_system.sysname
     *
     * @mbggenerated Wed Oct 21 09:45:00 CST 2015
     */
    public void setSysname(String sysname) {
        this.sysname = sysname == null ? null : sysname.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hyb_system.sysversion
     *
     * @return the value of hyb_system.sysversion
     *
     * @mbggenerated Wed Oct 21 09:45:00 CST 2015
     */
    public String getSysversion() {
        return sysversion;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hyb_system.sysversion
     *
     * @param sysversion the value for hyb_system.sysversion
     *
     * @mbggenerated Wed Oct 21 09:45:00 CST 2015
     */
    public void setSysversion(String sysversion) {
        this.sysversion = sysversion == null ? null : sysversion.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hyb_system.sysaddress
     *
     * @return the value of hyb_system.sysaddress
     *
     * @mbggenerated Wed Oct 21 09:45:00 CST 2015
     */
    public String getSysaddress() {
        return sysaddress;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hyb_system.sysaddress
     *
     * @param sysaddress the value for hyb_system.sysaddress
     *
     * @mbggenerated Wed Oct 21 09:45:00 CST 2015
     */
    public void setSysaddress(String sysaddress) {
        this.sysaddress = sysaddress == null ? null : sysaddress.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hyb_system.sysdesc
     *
     * @return the value of hyb_system.sysdesc
     *
     * @mbggenerated Wed Oct 21 09:45:00 CST 2015
     */
    public String getSysdesc() {
        return sysdesc;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hyb_system.sysdesc
     *
     * @param sysdesc the value for hyb_system.sysdesc
     *
     * @mbggenerated Wed Oct 21 09:45:00 CST 2015
     */
    public void setSysdesc(String sysdesc) {
        this.sysdesc = sysdesc == null ? null : sysdesc.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hyb_system.syslogourl
     *
     * @return the value of hyb_system.syslogourl
     *
     * @mbggenerated Wed Oct 21 09:45:00 CST 2015
     */
    public String getSyslogourl() {
        return syslogourl;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hyb_system.syslogourl
     *
     * @param syslogourl the value for hyb_system.syslogourl
     *
     * @mbggenerated Wed Oct 21 09:45:00 CST 2015
     */
    public void setSyslogourl(String syslogourl) {
        this.syslogourl = syslogourl == null ? null : syslogourl.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hyb_system.sysphone
     *
     * @return the value of hyb_system.sysphone
     *
     * @mbggenerated Wed Oct 21 09:45:00 CST 2015
     */
    public String getSysphone() {
        return sysphone;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hyb_system.sysphone
     *
     * @param sysphone the value for hyb_system.sysphone
     *
     * @mbggenerated Wed Oct 21 09:45:00 CST 2015
     */
    public void setSysphone(String sysphone) {
        this.sysphone = sysphone == null ? null : sysphone.trim();
    }

	public String getSystem_account() {
		return system_account;
	}

	public void setSystem_account(String system_account) {
		this.system_account = system_account;
	}
}