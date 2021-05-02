package com.micro.seller.quartz;

import com.micro.seller.service.MicroBuyRecordService;
import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 市场交易量
 */
@DisallowConcurrentExecution
@PersistJobDataAfterExecution
public class ScanMicroBuyRecordQuartz implements Job {

    protected static final Logger logger = LoggerFactory.getLogger(ScanMicroBuyRecordQuartz.class);

    @Autowired
    MicroBuyRecordService microBuyRecordService;

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        logger.info("........开始增加宝石...start.......");
        microBuyRecordService.produceGem();
        logger.info("........开始增加宝石...end.......");

    }

}
