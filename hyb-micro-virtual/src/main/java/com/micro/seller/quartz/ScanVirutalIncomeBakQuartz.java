package com.micro.seller.quartz;

import com.micro.seller.service.MicroVirtualIncomeServiceV2;
import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 市场交易量
 */
@DisallowConcurrentExecution
@PersistJobDataAfterExecution
public class ScanVirutalIncomeBakQuartz implements Job {

    protected static final Logger logger = LoggerFactory.getLogger(ScanVirutalIncomeBakQuartz.class);

//    @Autowired
//    MicroVirtualIncomeService microVirtualIncomeService;

    @Autowired
    MicroVirtualIncomeServiceV2 microVirtualIncomeServiceV2;

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        logger.info("........开始执行积分涨利 异常用户...start.......");
        microVirtualIncomeServiceV2.incomeByVirutalBalanceAndExcption();
        logger.info("........开始执行积分涨利 异常用户...end.......");

    }

}
