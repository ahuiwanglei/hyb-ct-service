package com.mszx.hyb.configure.quartz;

import com.mszx.hyb.configure.service.ScanConfigureService;
import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

@DisallowConcurrentExecution
@PersistJobDataAfterExecution
public class ScanConfigureTask implements Job {

    protected static final Logger logger = LoggerFactory.getLogger(ScanConfigureTask.class);

    @Autowired
    private ScanConfigureService scanConfigureService;

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        logger.info("*******************缓存刷新开始执行************************************************");
        scanConfigureService.scanConfigure();
        logger.info("*******************缓存刷新执行结束************************************************");
    }
}
