package com.mszx.hyb.configure.quartz;

import com.mszx.hyb.configure.service.ScanWxGiftService;
import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

@DisallowConcurrentExecution
@PersistJobDataAfterExecution
public class ScanWxGiftTask implements Job {

    protected static final Logger logger = LoggerFactory.getLogger(ScanWxGiftTask.class);

    @Autowired
    private ScanWxGiftService scanWxGiftService;
    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        logger.info("*******************:************************************************");
        scanWxGiftService.expiredWxGift();
        logger.info("*******************扫描微信礼品券过期执行结束************************************************");
    }
}
