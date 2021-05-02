package com.mszx.hyb.service.impl;

import com.mszx.hyb.common.model.Result;
import com.mszx.hyb.common.util.CommonUtil;
import com.mszx.hyb.common.util.ResultFactory;
import com.mszx.hyb.common.util.Strings;
import com.mszx.hyb.dao.HybBalanceChangeRecordMapper;
import com.mszx.hyb.dao.HybBalanceMapper;
import com.mszx.hyb.dao.HybSystemMapper;
import com.mszx.hyb.entity.*;
import com.mszx.hyb.service.IBalanceAction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.Date;

@Service
public class BalanceActionService implements IBalanceAction {

    @Autowired
    HybSystemMapper hybSystemMapper;
    @Autowired
    HybBalanceMapper hybBalanceMapper;
    @Autowired
    HybBalanceChangeRecordMapper hybBalanceChangeRecordMapper;

    @Transactional(readOnly = false)
    @Override
    public Result updateBalance(BalanceParams balanceParams) {
        if (Strings.isEmpty(balanceParams.getPkregister())) {
            return ResultFactory.getErrorResult("用户主键不能为空");
        }
        if (balanceParams == null || balanceParams.getOrderMoney().compareTo(new BigDecimal("0")) <= 0) {
            return ResultFactory.getErrorResult("金额不正确");
        }
        if (balanceParams.getChangeType() == null) {
            return ResultFactory.getErrorResult("金额操作符不正确（ChangeType）");
        }
        if (balanceParams.getBiztype() == null || (balanceParams.getBiztype() != BalanceParams.BizType.SystemBalance.getValue() && balanceParams.getBiztype() != BalanceParams.BizType.Virtualbalance.getValue()) ) {
            return ResultFactory.getErrorResult("更新账户类型不正确(biztype)");
        }
        HybSystem hybSystem = hybSystemMapper.selectSystem();
        if (Strings.isEmpty(balanceParams.getPkmuser())) {
            balanceParams.setPkmuser(hybSystem.getPksystem());
        }
        try {
            HybBalance hybBalance = hybBalanceMapper.selectByPkmuserAndPkregister(balanceParams.getPkmuser(), balanceParams.getPkregister());
            if (balanceParams.getChangeType() == BalanceParams.ChangeType.Reduce.getValue()) {
                if (hybBalance == null)
                    return ResultFactory.getErrorResult("余额不足");
                if (balanceParams.getBiztype() == BalanceParams.BizType.SystemBalance.getValue() && hybBalance.getBalance().compareTo(balanceParams.getOrderMoney()) < 0)
                    return ResultFactory.getErrorResult("余额不足");
                if (balanceParams.getBiztype() == BalanceParams.BizType.Virtualbalance.getValue() && hybBalance.getVirtualbalance().compareTo(balanceParams.getOrderMoney()) < 0)
                    return ResultFactory.getErrorResult("余额不足");
            } else if (balanceParams.getChangeType() == BalanceParams.ChangeType.Add.getValue()) {
                if (balanceParams.getOrderMoney().compareTo(new BigDecimal("0")) <= 0) {
                    return ResultFactory.getErrorResult("金额不正确");
                }
            } else {
                return ResultFactory.getErrorResult("金额操作符不正确(ChangeType)");
            }

            if (hybBalance == null) {
                hybBalance = new HybBalance();
                hybBalance.setPkbalance(CommonUtil.getUUID());
                hybBalance.setPkregister(balanceParams.getPkregister());
                hybBalance.setPkmuser(balanceParams.getPkmuser());
                hybBalance.setCreatetime(new Date());
                if (balanceParams.getBiztype() == BalanceParams.BizType.SystemBalance.getValue()) {
                    hybBalance.setBalance(balanceParams.getOrderMoney());
                } else if (balanceParams.getBiztype() == BalanceParams.BizType.Virtualbalance.getValue()) {
                    hybBalance.setVirtualbalance(balanceParams.getOrderMoney());
                } else {
                    return ResultFactory.getErrorResult("暂不支持此业务");
                }
                int flag = hybBalanceMapper.insertSelective(hybBalance);
                if (flag > 0) {
                    createBalanceRecord(hybBalance, balanceParams);
                    return ResultFactory.getSuccessResult(hybBalance);
                }
            } else {
                HybBalance updateBalance = new HybBalance();
                updateBalance.setPkbalance(hybBalance.getPkbalance());
                if (balanceParams.getBiztype() == BalanceParams.BizType.SystemBalance.getValue()) {
                    if(balanceParams.getChangeType() == BalanceParams.ChangeType.Add.getValue()){
                        hybBalance.setBalance(hybBalance.getBalance().add(balanceParams.getOrderMoney()));
                    }else if(balanceParams.getChangeType() == BalanceParams.ChangeType.Reduce.getValue()){
                        hybBalance.setBalance(hybBalance.getBalance().subtract(balanceParams.getOrderMoney()));
                    }
                    updateBalance.setBalance(hybBalance.getBalance());
                } else if (balanceParams.getBiztype() == BalanceParams.BizType.Virtualbalance.getValue()) {
                    if(balanceParams.getChangeType() == BalanceParams.ChangeType.Add.getValue()){
                        hybBalance.setVirtualbalance(hybBalance.getVirtualbalance().add(balanceParams.getOrderMoney()));
                    }else if(balanceParams.getChangeType() == BalanceParams.ChangeType.Reduce.getValue()){
                        hybBalance.setVirtualbalance(hybBalance.getVirtualbalance().subtract(balanceParams.getOrderMoney()));
                    }
                    updateBalance.setVirtualbalance(hybBalance.getVirtualbalance());
                } else {
                    return ResultFactory.getErrorResult("暂不支持此业务");
                }
                int flag = hybBalanceMapper.updateBalanceByPK(updateBalance);
                if (flag > 0) {
                    createBalanceRecord(hybBalance, balanceParams);
                    return ResultFactory.getSuccessResult(hybBalance);
                }


            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ResultFactory.getErrorResult("更新失败，请重试");
    }

    private void createBalanceRecord(HybBalance hybBalance, BalanceParams balanceParams) {
        HybBalanceChangeRecord hybBalanceChangeRecord=new HybBalanceChangeRecord();
        BigDecimal before_balance = null;
        if(balanceParams.getBiztype() == BalanceParams.BizType.Virtualbalance.getValue()){
            String vuuid = CommonUtil.getUUID();
            VirtualRecord virtualRecord = new VirtualRecord(vuuid, balanceParams.getPkregister(), balanceParams.getOrderMoney(), 7, balanceParams.getOrderSubject(), balanceParams.getOrderid(), balanceParams.getPkmuser());
            virtualRecord.setStatus(1);//0：未生效  1：已经生效， 未生效的数据只是客户端弹出提示使用，在" + SystemConstant.VIRTUAL_NAME + "记录中 显示的是重新生成的 状态等于1的记录
            hybBalanceChangeRecordMapper.addVirtualRecord(virtualRecord);
        }else{
            if(balanceParams.getChangeType() == BalanceParams.ChangeType.Add.getValue()){
                before_balance = hybBalance.getBalance().subtract(balanceParams.getOrderMoney());
            }else if(balanceParams.getChangeType() == BalanceParams.ChangeType.Reduce.getValue()){
                before_balance = hybBalance.getBalance().add(balanceParams.getOrderMoney());
            }
            hybBalanceChangeRecord.setBefore_balance(before_balance);
            hybBalanceChangeRecord.setAfter_balance(hybBalance.getBalance());
            hybBalanceChangeRecord.setAfter_totalinterest(new BigDecimal("0"));
            hybBalanceChangeRecord.setBefore_totalinterest(new BigDecimal("0"));
            hybBalanceChangeRecord.setChangeType(balanceParams.getChangeType());
            hybBalanceChangeRecord.setOrderMoney(balanceParams.getOrderMoney());
            hybBalanceChangeRecord.setOrderSubject(balanceParams.getOrderSubject());
            hybBalanceChangeRecord.setOrderTime(new Date());
            hybBalanceChangeRecord.setPkregister(balanceParams.getPkregister());
            hybBalanceChangeRecord.setBiztype(balanceParams.getBiztype());
            hybBalanceChangeRecord.setSource_type(balanceParams.getSource_type());
            hybBalanceChangeRecord.setOrderid(balanceParams.getOrderid());
            hybBalanceChangeRecordMapper.insertSelective(hybBalanceChangeRecord);
        }

    }


    @Override
    public Result createBalance(HybRegister hybRegister) {
        //构造用户平台余额信息
        HybSystem hybSystem = hybSystemMapper.selectSystem();
        HybBalance hybBalance = new HybBalance();
        hybBalance.setPkregister(hybRegister.getPkregister());
        hybBalance.setPkmuser(hybSystem.getPksystem());
        hybBalance.setCreatetime(new Date());
        hybBalance.setVirtualbalance(new BigDecimal("0"));
        hybBalance.setPkbalance(CommonUtil.getUUID());
        try {
            int flag = hybBalanceMapper.insertSelective(hybBalance);
            if (flag > 0)
                return ResultFactory.getSuccessResult(hybBalance);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ResultFactory.getErrorResult("资金账号生成失败");
    }
}

