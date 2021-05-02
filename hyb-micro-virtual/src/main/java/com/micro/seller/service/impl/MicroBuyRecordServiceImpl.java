package com.micro.seller.service.impl;

import com.alibaba.fastjson.JSON;
import com.micro.seller.dao.HybMemberInviterRepository;
import com.micro.seller.dao.MicroBalanceRecordRepository;
import com.micro.seller.dao.MicroBalanceRepository;
import com.micro.seller.dao.MicroProductBuyRecordRepository;
import com.micro.seller.entity.db.*;
import com.micro.seller.quartz.ScanMicroBuyRecordQuartz;
import com.micro.seller.service.MicroBuyRecordService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MicroBuyRecordServiceImpl implements MicroBuyRecordService {

    protected static final Logger logger = LoggerFactory.getLogger(ScanMicroBuyRecordQuartz.class);


    @Autowired
    MicroBalanceRepository microBalanceRepository;
    @Autowired
    MicroBalanceRecordRepository microBalanceRecordRepository;
    @Autowired
    MicroProductBuyRecordRepository microProductBuyRecordRepository;
    @Autowired
    HybMemberInviterRepository hybMemberInviterRepository;
    /**
     * 产宝石和钻石
     */
    @Override
    @Transactional
    public void produceGem() {

        logger.info("开始增加宝石。。。");
        incrementGem();

        //每日 钻石基数  推荐人购买产品数增加钻石
        incrementDiamond();

    }

    private void incrementDiamond() {
        List<MicroBalance> microBalanceList = microBalanceRepository.findAll();
        for(int i=0;i<microBalanceList.size();i++){
            logger.info("-----------------index="+i+"--------start--------------");
            MicroBalance microBalanceUpdate = microBalanceList.get(i);

            List<HybRegister> subUsers = hybMemberInviterRepository.findRegisterByParentPkRegister(microBalanceUpdate.getPkregister());

            List<String> ids = subUsers.stream().map(user-> user.getPkregister()).collect(Collectors.toList());
            if(ids.size()==0){
                continue;
            }
            logger.info("parentuser=" + microBalanceUpdate.getPkregister());
            for (int j=0;j< ids.size();j++){
                logger.info(" --推荐人"+j+"-->" + ids.get(j));
            }
            List<MicroProductBuyRecord> records = microProductBuyRecordRepository.findPkregistersByRecords(ids);
            BigDecimal totalDiamond = microBalanceUpdate.getEveyday_increment_diamond_base();
            logger.info(" -diamond base value--" + totalDiamond + " records:" + records.size());
            for (int j=0;j< records.size();j++){
                logger.info(("record:" + JSON.toJSONString(records.get(j))));
                if ((records.get(j).getProduct().getType() & MicroProduct.TYPE.Diamond.getValue()) == MicroProduct.TYPE.Diamond.getValue()) {
                    totalDiamond = totalDiamond.add(records.get(j).getProduct().getDay_diamond_count());
                    break;
                }
            }
            logger.info(" -diamond add value--" + totalDiamond);
            if (totalDiamond.compareTo(new BigDecimal(0)) > 0) {
                MicroBalanceRecord microBalanceRecord = createMicroBalanceRecord(MicroBalanceRecord.TYPE.DiamondRecord.getValue(),
                        microBalanceUpdate.getPkregister(),
                        MicroBalanceRecord.FlowType.InFlow.getValue(),
                        "每日产钻石", totalDiamond);
                microBalanceRecordRepository.save(microBalanceRecord);

                microBalanceUpdate.setDiamond_balance(microBalanceUpdate.getDiamond_balance().add(totalDiamond));
                microBalanceRepository.save(microBalanceUpdate);


            }
            logger.info("---------------------------------------");
        }
    }

    //宝石
    private void incrementGem() {
        List<MicroProductBuyRecord> microProductBuyRecordList = microProductBuyRecordRepository.findByBuyRecord();
        for (int i = 0; i < microProductBuyRecordList.size(); i++) {
            MicroProductBuyRecord microProductBuyRecord = microProductBuyRecordList.get(i);
            if(microProductBuyRecord != null && microProductBuyRecord.getProduct() != null){
                MicroProduct microProduct = microProductBuyRecord.getProduct();
                if ((microProductBuyRecord.getProduct().getType() & MicroProduct.TYPE.Gem.getValue()) == MicroProduct.TYPE.Gem.getValue()) {
                    //产宝石
                    //加宝石
                    logger.info("---->add gem register:" + microProductBuyRecord.getPkregister() +"  start");
                    MicroBalance microBalance = getUserMicroBalance(microProductBuyRecord.getPkregister());
                    if(microBalance != null){
                        BigDecimal addAmount = microBalance.getEveyday_increment_gem_base().add(microProduct.getDay_gem_count());
//                        BigDecimal amount = microBalance.getGem_balance().add(addAmount);
                        if(addAmount.compareTo(new BigDecimal("0.1"))<0){
                            continue;
                        }
                        logger.info("->记录" + microProductBuyRecord.getBuy_record_id() + " 增加宝石" + addAmount);
                        MicroBalanceRecord microBalanceRecord = createMicroBalanceRecord(MicroBalanceRecord.TYPE.GemRecord.getValue(),
                                microProductBuyRecord.getPkregister(),
                                MicroBalanceRecord.FlowType.InFlow.getValue(),
                                "每日产宝石", addAmount);

                        microBalanceRecordRepository.save(microBalanceRecord);

                        microBalance.setGem_balance(microBalance.getGem_balance().add(addAmount));
                        microBalanceRepository.save(microBalance);
                        //宝石记录

                    }else{
                        logger.info("---->add gem register:" + microProductBuyRecord.getPkregister() +" no balance");
                    }
                }
            }else{
                logger.info("---->not gem rule");
            }
        }
    }

    @Override
    public MicroBalance getUserMicroBalance(String pkregister){
        MicroBalance microBalance = microBalanceRepository.findByPkregister(pkregister);
        if(microBalance == null){
            microBalance = new MicroBalance();
            microBalance.setCreated_at(new Date());
            microBalance.setPkregister(pkregister);
            microBalance.setGem_balance(new BigDecimal(0));
            microBalance.setWithdraw_balance(new BigDecimal(0));
            microBalance.setRecommend_balance(new BigDecimal(0));
            microBalance.setStatus(1);
            microBalance.setUpdated_at(new Date());
        }
        return microBalance;
    }


    private MicroBalanceRecord createMicroBalanceRecord(int type, String pkregister, int flow_type, String order_title, BigDecimal amount){
        MicroBalanceRecord microBalanceRecord = new MicroBalanceRecord();
        microBalanceRecord.setPkregister(pkregister);
        microBalanceRecord.setType(type);
        microBalanceRecord.setFlow_type(flow_type);
        microBalanceRecord.setOrder_title(order_title);
        microBalanceRecord.setAmount(amount);
        return microBalanceRecord;
    }


}
