package com.micro.seller.service.impl;

import com.github.pagehelper.StringUtil;
import com.micro.seller.dao.*;
import com.micro.seller.entity.db.*;
import com.micro.seller.service.MicroVirtualIncomeService;
import com.micro.seller.util.JpushUserUtils;
import com.micro.seller.util.MoneyUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.*;

@Service
public class MicroVirtualIncomeServiceImpl implements MicroVirtualIncomeService {

    protected static final Logger logger = LoggerFactory.getLogger(MicroVirtualIncomeServiceImpl.class);

    @Autowired
    private HybDictionaryRepository hybDictionaryRepository;

    @Autowired
    private HybMemberInviterRepository hybMemberInviterRepository;

    @Autowired
    private HybRegisterRepository hybRegisterRepository;

    @Autowired
    private HybBalanceRepository hybBalanceRepository;

    @Autowired
    private HybBalanceChangeRecordRepository hybBalanceChangeRecordRepository;

    @Autowired
    private  HybPushRecordsRepository hybPushRecordsRepository;

    BigDecimal totalIncome = new BigDecimal(0);
    int totalPerson = 0;


    @Override
    @Transactional(readOnly = false)
    public void incomeByVirutalBalance() {
        logger.info("*****************************");
        totalPerson = 0;
        List<HybDictionary> hybDictionaryList = hybDictionaryRepository.findTbCode("root_phone_virutal_income");
        totalIncome = new BigDecimal(0);
        for (int i = 0; i < hybDictionaryList.size(); i++) {
            String code = hybDictionaryList.get(i).getCode();
            String rateStr = code.split("\\|")[1];
            String rootPhone = code.split("\\|")[0];
            if (new BigDecimal(rateStr).compareTo(new BigDecimal(0)) > 0 && StringUtil.isNotEmpty(rootPhone)) {
                rootPhoneVirutalIncome(new BigDecimal(rateStr), rootPhone);
            }
        }
        logger.info("总共执行收益：" + totalIncome +" 总共推广人数=" + totalPerson);
    }

    private void rootPhoneVirutalIncome(BigDecimal rate, String phoneno) {
        //更新积分
        logger.info("开始处理用户：" + phoneno + " 年化率：" + rate);
//        totalIncome = totalIncome.add(updateParentVirtualIncome(rate, phoneno));
        List<HybRegister> hybRegisters = hybMemberInviterRepository.findRegisterByParentPhone(phoneno);
        logger.info("用户" + phoneno+ " 推荐的用户有" + hybRegisters.size() +" 个");
        if (hybRegisters != null && hybRegisters.size() > 0) {
            totalPerson += hybRegisters.size();
            for (int i = 0; i < hybRegisters.size(); i++) {
                HybRegister hybRegister = hybRegisters.get(i);
                rootPhoneVirutalIncome(rate, hybRegister.getPhoneno());
            }
        }
    }

//    private BigDecimal updateParentVirtualIncome(BigDecimal rate, String phoneno) {
//        String pksystem = hybBalanceRepository.hybSystem();
//        HybBalance hybBalance = hybBalanceRepository.findRegisterBalance(pkregister,pksystem);
//        if(hybBalance == null){
//            return new BigDecimal(0);
//        }
//        BigDecimal currentBalance = (hybBalance.getBalance().add(hybBalance.getTotalinterest()));
//        BigDecimal beforeTotalInterest = hybBalance.getTotalinterest();
//        BigDecimal virtualBalance = hybBalance.getVirtualbalance();
//        logger.info("更新用户=" + phoneno+" 总积分=" + hybBalance.getVirtualbalance() + " 当前余额=" + currentBalance +" 积分：" + virtualBalance);
//        BigDecimal income = MoneyUtil.getIncome(virtualBalance, 1, rate);
//        logger.info("当前费率：" + rate +"  积分涨利：" + income);
//        if(income.compareTo(new BigDecimal("0.01"))< 0){
//            logger.info(phoneno + "积分涨利金额太小，已忽略");
//            return new BigDecimal(0);
//        }
//        hybBalance.setTotalinterest(hybBalance.getTotalinterest().add(income));
//        int flag = hybBalanceRepository.updateBalanceByPKbalanceId(hybBalance.getTotalinterest(), hybBalance.getPkbalance());
//        if(flag >0){
//            HybBalanceChangeRecord hybBalanceChangeRecord = new HybBalanceChangeRecord();
//            hybBalanceChangeRecord.setAfter_balance(hybBalance.getBalance());
//            hybBalanceChangeRecord.setAfter_totalinterest(hybBalance.getTotalinterest());
//            hybBalanceChangeRecord.setBefore_balance(hybBalance.getBalance());
//            hybBalanceChangeRecord.setBefore_totalinterest(beforeTotalInterest);
//            hybBalanceChangeRecord.setChangeType(HybBalanceChangeRecord.CHANGETYPE.Add.getValue());
//            hybBalanceChangeRecord.setOrderMoney(income);
//            hybBalanceChangeRecord.setOrderSubject("积分涨利");
//            hybBalanceChangeRecord.setOrderTime(new Date());
//            hybBalanceChangeRecord.setPkregister(hybBalance.getPkregister());
//            hybBalanceChangeRecord.setBiztype(HybBalanceChangeRecord.BIZTYPE.PlatformBalance.getValue());
//            hybBalanceChangeRecordRepository.save(hybBalanceChangeRecord);
//            logger.info("生成余额记录 成功...");
//
//            Map<String,String> map = new HashMap<>();
//            map.put("type", "3");
//            String content = "您有一笔积分涨利：" +income.stripTrailingZeros() + "元到账了，请注意查收";
//            List<String> phones = new ArrayList<>();
//            phones.add(phoneno);
//            try {
//                JpushUserUtils.sentAlertByAliasExtras(content, phones ,map);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//
////            HybPushRecords pushrecords = new HybPushRecords();
////            pushrecords.setPkpushrecords(UUID.randomUUID().toString());
////            pushrecords.setPkmuser(pksystem);
////            pushrecords.setPkregister(hybBalance.getHybRegister().getPkregister());
////            pushrecords.setImage(content);
////            pushrecords.setType(3);
////            pushrecords.setSenddate(System.currentTimeMillis() + "");
////            hybPushRecordsRepository.save(pushrecords);
//        }
//
//
//         return income;
//    }

}