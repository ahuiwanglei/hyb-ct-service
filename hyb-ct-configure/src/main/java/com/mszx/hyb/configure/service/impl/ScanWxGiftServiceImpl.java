package com.mszx.hyb.configure.service.impl;

import com.mszx.hyb.configure.dao.hyb.HybWxBalanceRecordRepository;
import com.mszx.hyb.configure.dao.hyb.HybWxBalanceRepository;
import com.mszx.hyb.configure.dao.hyb.HybWxGiftTicketRepository;
import com.mszx.hyb.configure.model.db.hyb.wx.HybWxBalance;
import com.mszx.hyb.configure.model.db.hyb.wx.HybWxBalanceRecord;
import com.mszx.hyb.configure.model.db.hyb.wx.HybWxGiftTicket;
import com.mszx.hyb.configure.quartz.ScanWxGiftTask;
import com.mszx.hyb.configure.service.ScanWxGiftService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
public class ScanWxGiftServiceImpl implements ScanWxGiftService {

    protected static final Logger logger = LoggerFactory.getLogger(ScanWxGiftServiceImpl.class);

    @Autowired
    private HybWxBalanceRecordRepository hybWxBalanceRecordRepository;
    @Autowired
    private HybWxBalanceRepository hybWxBalanceRepository;
    @Autowired
    private HybWxGiftTicketRepository hybWxGiftTicketRepository;

    @Override
    public void expiredWxGift() {
        HybWxGiftTicket queryParam = new HybWxGiftTicket();
        queryParam.setPay_status(HybWxGiftTicket.PayStatus.Payed.getValue());
        queryParam.setStatus(HybWxGiftTicket.Status.NoReceiver.getValue());
        Example<HybWxGiftTicket> example = Example.of(queryParam);
        List<HybWxGiftTicket> hybWxGiftTicketList = hybWxGiftTicketRepository.findAll(example);
        for (int i=0;i<hybWxGiftTicketList.size();i++){
            HybWxGiftTicket hybWxGiftTicket = hybWxGiftTicketList.get(i);
            if (getExpiredTime(hybWxGiftTicket.getCreatetime(), HybWxGiftTicket.expired_day) < 1000) {
                if (hybWxGiftTicket.getSource_from() == HybWxGiftTicket.SourceFrom.Buy.getValue() ||
                        hybWxGiftTicket.getSource_from() == HybWxGiftTicket.SourceFrom.BalanceToTicket.getValue()) {
                    logger.info("用户礼品券{}到期未领取，执行回退逻辑",hybWxGiftTicket.getPkticket());

                    hybWxGiftTicket.setStatus(HybWxGiftTicket.Status.Expired.getValue());
                    hybWxGiftTicketRepository.save(hybWxGiftTicket);

                    HybWxBalance queryBalanceParam = new HybWxBalance();
                    queryBalanceParam.setOpenid(hybWxGiftTicket.getFrom_user());
                    Example<HybWxBalance> hybWxBalanceExample = Example.of(queryBalanceParam);
                    HybWxBalance hybWxBalance = hybWxBalanceRepository.findOne(hybWxBalanceExample);

                    BigDecimal before_balance = hybWxBalance.getBalance().add(hybWxBalance.getTry_money());
                    BigDecimal after_balance = before_balance.add(hybWxGiftTicket.getAmount());

                    HybWxBalanceRecord hybWxBalanceRecord = new HybWxBalanceRecord();
                    hybWxBalanceRecord.setPkrecord(getUUID());
                    hybWxBalanceRecord.setType(0);
                    hybWxBalanceRecord.setOpenid(hybWxGiftTicket.getFrom_user());
                    hybWxBalanceRecord.setBefore_balance(before_balance);
                    hybWxBalanceRecord.setOrder_amount(hybWxGiftTicket.getAmount());
                    hybWxBalanceRecord.setAfter_balance(after_balance);
                    hybWxBalanceRecord.setOrder_subject("券过期回退");
                    hybWxBalanceRecord.setChange_type(1);
                    hybWxBalanceRecord.setDeal_type(HybWxBalanceRecord.DealType.ExpiredBack.getValue()+"");
                    hybWxBalanceRecord.setOutid(hybWxGiftTicket.getPkticket());
                    hybWxBalanceRecord.setCreatetime(new Date());
                    hybWxBalanceRecordRepository.save(hybWxBalanceRecord);

                    logger.info("用户账户{}",hybWxBalance.getPkwxbalance());
                    hybWxBalance.setBalance(hybWxBalance.getBalance().add(hybWxGiftTicket.getAmount()));
                    hybWxBalance.setFreeze_balance(hybWxBalance.getFreeze_balance().subtract(hybWxGiftTicket.getAmount()));
                    hybWxBalanceRepository.save(hybWxBalance);

                    logger.info("用户礼品券{} 过期 回退金额 {}成功 回退前余额{}, 回退后余额{}",
                            hybWxGiftTicket.getPkticket(),
                            hybWxGiftTicket.getAmount(),
                            before_balance, after_balance);

                }else if(hybWxGiftTicket.getSource_from() == HybWxGiftTicket.SourceFrom.PlatformTicket.getValue()){
                    hybWxGiftTicket.setStatus(HybWxGiftTicket.Status.Expired.getValue());
                    hybWxGiftTicketRepository.save(hybWxGiftTicket);
                    logger.info("平台券{}到期未领取，更新为已过期",hybWxGiftTicket.getPkticket());
                }
            }
        }
    }

    public static Long getExpiredTime(Date createtime, Integer expired_day) {
        if (createtime == null) return 0L;
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(createtime);
        calendar.add(Calendar.DAY_OF_MONTH, expired_day);
        return calendar.getTime().getTime() - new Date().getTime();
    }

    public static String getUUID() {
        return UUID.randomUUID().toString().replace("-", "");
    }

}
