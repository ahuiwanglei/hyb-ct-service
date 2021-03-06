package com.micro.seller.service.impl;

import com.github.pagehelper.StringUtil;
import com.micro.seller.dao.*;
import com.micro.seller.entity.db.*;
import com.micro.seller.service.MicroVirtualIncomeService;
import com.micro.seller.service.MicroVirtualIncomeServiceV2;
import com.micro.seller.util.JpushUserUtils;
import com.micro.seller.util.MoneyUtil;
import com.micro.seller.util.TimeUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;

@Service
public class MicroVirtualIncomeServiceV2Impl implements MicroVirtualIncomeServiceV2 {

    protected static final Logger logger = LoggerFactory.getLogger(MicroVirtualIncomeServiceV2Impl.class);

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
    private HybPushRecordsRepository hybPushRecordsRepository;

    @Autowired
    private HybRegisterIncomeRepository hybRegisterIncomeRepository;

    BigDecimal totalIncome = new BigDecimal(0);
    int totalPerson = 0;
    int exceptionPerson = 0;

    @Override
    @Transactional(readOnly = false)
    public void incomeByVirutalBalance() {
        logger.info("*****************************");
        totalPerson = 0;
        totalIncome = new BigDecimal(0);
        List<HybDictionary> hybDictionaryList = hybDictionaryRepository.findTbCode("root_phone_virutal_income");

        for (int i = 0; i < hybDictionaryList.size(); i++) {
            String code = hybDictionaryList.get(i).getCode();
            String rateStr = code.split("\\|")[1];
            String rootPhone = code.split("\\|")[0];
            if (new BigDecimal(rateStr).compareTo(new BigDecimal(0)) > 0 && StringUtil.isNotEmpty(rootPhone)) {
                rootPhoneVirutalIncome(new BigDecimal(rateStr));
            }
        }
        logger.info("?????????????????????" + totalIncome +" ??????????????????=" + totalPerson);
    }

    private void rootPhoneVirutalIncome(BigDecimal rate) {
        //????????????
        List<HybRegister> hybRegisters = hybRegisterRepository.findByShenAllUser();
        totalPerson = hybRegisters.size();
        for (HybRegister hybRegister : hybRegisters){
            logger.info("?????????????????????" + hybRegister.getPhoneno() + " ????????????" + rate);
            totalIncome = totalIncome.add(updateParentVirtualIncome(rate, hybRegister.getPhoneno(), hybRegister.getPkregister()));
        }
    }

    private BigDecimal updateParentVirtualIncome(BigDecimal rate, String phoneno, String pkregister) {
        String pksystem = hybBalanceRepository.hybSystem();
        HybBalance hybBalance = hybBalanceRepository.findPkRegisterBalance(pkregister,pksystem);
        if(hybBalance == null){
            return new BigDecimal(0);
        }
        BigDecimal currentBalance = (hybBalance.getBalance().add(hybBalance.getTotalinterest()));
        BigDecimal beforeTotalInterest = hybBalance.getTotalinterest();
        BigDecimal virtualBalance = hybBalance.getVirtualbalance();
        logger.info("????????????=" + phoneno+" ?????????=" + hybBalance.getVirtualbalance() + " ????????????=" + currentBalance +" ?????????" + virtualBalance);
        BigDecimal income = MoneyUtil.getIncome(virtualBalance, 1, rate);
        logger.info("???????????????" + rate +"  ???????????????" + income);
        if(income.compareTo(new BigDecimal("0.01"))< 0){
            logger.info(phoneno + "????????????????????????????????????");
            return new BigDecimal(0);
        }
        hybBalance.setTotalinterest(hybBalance.getTotalinterest().add(income));
        int flag = hybBalanceRepository.updateBalanceByPKbalanceId(hybBalance.getTotalinterest(), hybBalance.getPkbalance());
        if(flag >0){
            HybBalanceChangeRecord hybBalanceChangeRecord = new HybBalanceChangeRecord();
            hybBalanceChangeRecord.setAfter_balance(hybBalance.getBalance());
            hybBalanceChangeRecord.setAfter_totalinterest(hybBalance.getTotalinterest());
            hybBalanceChangeRecord.setBefore_balance(hybBalance.getBalance());
            hybBalanceChangeRecord.setBefore_totalinterest(beforeTotalInterest);
            hybBalanceChangeRecord.setChangeType(HybBalanceChangeRecord.CHANGETYPE.Add.getValue());
            hybBalanceChangeRecord.setOrderMoney(income);
            hybBalanceChangeRecord.setOrderSubject("????????????");
            hybBalanceChangeRecord.setOrderTime(new Date());
            hybBalanceChangeRecord.setPkregister(hybBalance.getPkregister());
            hybBalanceChangeRecord.setBiztype(HybBalanceChangeRecord.BIZTYPE.PlatformBalance.getValue());
            hybBalanceChangeRecordRepository.save(hybBalanceChangeRecord);
            logger.info("?????????????????? ??????...");

//            Map<String,String> map = new HashMap<>();
//            map.put("type", "3");
//            String content = "???????????????????????????" +income.stripTrailingZeros() + "??????????????????????????????";
//            List<String> phones = new ArrayList<>();
//            phones.add(phoneno);
//            try {
//                JpushUserUtils.sentAlertByAliasExtras(content, phones ,map);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }

            HybRegisterIncomeBak hybRegisterIncomeBak = new HybRegisterIncomeBak();
            hybRegisterIncomeBak.setPkincomebak(UUID.randomUUID().toString());
            hybRegisterIncomeBak.setPkregister(hybBalance.getPkregister());
            hybRegisterIncomeBak.setAmount(income);
            hybRegisterIncomeRepository.save(hybRegisterIncomeBak);

//            HybPushRecords pushrecords = new HybPushRecords();
//            pushrecords.setPkpushrecords(UUID.randomUUID().toString());
//            pushrecords.setPkmuser(pksystem);
//            pushrecords.setPkregister(hybBalance.getHybRegister().getPkregister());
//            pushrecords.setImage(content);
//            pushrecords.setType(3);
//            pushrecords.setSenddate(System.currentTimeMillis() + "");
//            hybPushRecordsRepository.save(pushrecords);
        }


         return income;
    }


    @Override
    @Transactional(readOnly = false)
    public void incomeByVirutalBalanceAndExcption() {
        logger.info("************Excption User*****************");
        totalPerson = 0;
        totalIncome = new BigDecimal(0);
        List<HybDictionary> hybDictionaryList = hybDictionaryRepository.findTbCode("root_phone_virutal_income");

        for (int i = 0; i < hybDictionaryList.size(); i++) {
            String code = hybDictionaryList.get(i).getCode();
            String rateStr = code.split("\\|")[1];
            String rootPhone = code.split("\\|")[0];
            if (new BigDecimal(rateStr).compareTo(new BigDecimal(0)) > 0 && StringUtil.isNotEmpty(rootPhone)) {
                rootPhoneVirutalIncomeExcption(new BigDecimal(rateStr));
            }
        }
        logger.info("?????????????????????" + totalIncome +" ??????????????????=" + totalPerson +" ????????????=" + exceptionPerson);
    }

    private void rootPhoneVirutalIncomeExcption(BigDecimal rate) {
        //????????????
        List<HybRegister> hybRegisters = hybRegisterRepository.findByShenAllUser();
        totalPerson = hybRegisters.size();
        for (HybRegister hybRegister : hybRegisters){

            int count = hybBalanceChangeRecordRepository.countTodayUser(hybRegister.getPkregister(),
                    TimeUtil.fomatDate(TimeUtil.currentDayStartTime(), TimeUtil.sdf_str_time),
                    TimeUtil.fomatDate(TimeUtil.currentDayEndTime()));
            if(count <=0){
                exceptionPerson ++;
                logger.info("?????????????????????" + hybRegister.getPhoneno() + " ????????????" + rate);
                BigDecimal income = updateParentVirtualIncomeException(rate, hybRegister.getPhoneno(), hybRegister.getPkregister());
                totalIncome = totalIncome.add(income);
            }
        }
    }

    private BigDecimal updateParentVirtualIncomeException(BigDecimal rate, String phoneno, String pkregister) {
        String pksystem = hybBalanceRepository.hybSystem();
        HybBalance hybBalance = hybBalanceRepository.findPkRegisterBalance(pkregister,pksystem);
        if(hybBalance == null){
            return new BigDecimal(0);
        }
        BigDecimal currentBalance = (hybBalance.getBalance().add(hybBalance.getTotalinterest()));
        BigDecimal beforeTotalInterest = hybBalance.getTotalinterest();
        BigDecimal virtualBalance = hybBalance.getVirtualbalance();
        logger.info("????????????=" + phoneno+" ?????????=" + hybBalance.getVirtualbalance() + " ????????????=" + currentBalance +" ?????????" + virtualBalance);
        BigDecimal income = MoneyUtil.getIncome(virtualBalance, 1, rate);
        logger.info("???????????????" + rate +"  ???????????????" + income);
        if(income.compareTo(new BigDecimal("0.01"))< 0){
            logger.info(phoneno + "????????????????????????????????????");
            return new BigDecimal(0);
        }
        hybBalance.setTotalinterest(hybBalance.getTotalinterest().add(income));
        int flag = hybBalanceRepository.updateBalanceByPKbalanceId(hybBalance.getTotalinterest(), hybBalance.getPkbalance());
        if(flag >0){
            HybBalanceChangeRecord hybBalanceChangeRecord = new HybBalanceChangeRecord();
            hybBalanceChangeRecord.setAfter_balance(hybBalance.getBalance());
            hybBalanceChangeRecord.setAfter_totalinterest(hybBalance.getTotalinterest());
            hybBalanceChangeRecord.setBefore_balance(hybBalance.getBalance());
            hybBalanceChangeRecord.setBefore_totalinterest(beforeTotalInterest);
            hybBalanceChangeRecord.setChangeType(HybBalanceChangeRecord.CHANGETYPE.Add.getValue());
            hybBalanceChangeRecord.setOrderMoney(income);
            hybBalanceChangeRecord.setOrderSubject("????????????");
            hybBalanceChangeRecord.setOrderTime(new Date());
            hybBalanceChangeRecord.setPkregister(hybBalance.getPkregister());
            hybBalanceChangeRecord.setBiztype(HybBalanceChangeRecord.BIZTYPE.PlatformBalance.getValue());
            hybBalanceChangeRecordRepository.save(hybBalanceChangeRecord);
            logger.info("?????????????????? ??????...");

            HybRegisterIncomeBak hybRegisterIncomeBak = new HybRegisterIncomeBak();
            hybRegisterIncomeBak.setPkincomebak(UUID.randomUUID().toString());
            hybRegisterIncomeBak.setPkregister(hybBalance.getPkregister());
            hybRegisterIncomeBak.setAmount(income);
            hybRegisterIncomeRepository.save(hybRegisterIncomeBak);

        }


        return income;
    }
}
