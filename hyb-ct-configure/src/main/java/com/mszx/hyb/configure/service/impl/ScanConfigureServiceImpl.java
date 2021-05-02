package com.mszx.hyb.configure.service.impl;

import com.alibaba.druid.support.json.JSONUtils;
import com.alibaba.fastjson.JSON;
import com.mszx.hyb.configure.dao.hyb.HybConfigurationRepository;
import com.mszx.hyb.configure.dao.paycenter.PayMerchantRepository;
import com.mszx.hyb.configure.model.db.hyb.HybConfiguration;
import com.mszx.hyb.configure.model.db.paycenter.PayMerchant;
import com.mszx.hyb.configure.quartz.ScanConfigureTask;
import com.mszx.hyb.configure.service.ScanConfigureService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ScanConfigureServiceImpl implements ScanConfigureService {

    protected static final Logger logger = LoggerFactory.getLogger(ScanConfigureServiceImpl.class);

    @Autowired
    PayMerchantRepository payMerchantRepository;
    @Autowired
    HybConfigurationRepository hybConfigurationRepository;
    @Autowired
    RedisTemplate redisTemplate;

    @Override
    public void scanConfigure() {
       List<PayMerchant> payMerchants =  payMerchantRepository.findAll();

        Map<String, String> payconfigToken = new HashMap<>();
       for (int i=0;i< payMerchants.size();i++){
           logger.info("key: + " + "cache_paycenter_token_"+payMerchants.get(i).getOutid() +"    value:" + payMerchants.get(i).getMerchant_token());
           payconfigToken.put("cache_paycenter_token_"+payMerchants.get(i).getOutid(), payMerchants.get(i).getMerchant_token());
           payconfigToken.put("cache_paycenter_"+ payMerchants.get(i).getOutid(), JSON.toJSONString(payMerchants.get(i)));
           logger.info("key: + " + "cache_paycenter_"+ payMerchants.get(i).getOutid() +"    value:" + JSON.toJSONString(payMerchants.get(i)));

       }
        redisTemplate.opsForValue().multiSet(payconfigToken);
//        logger.info("shangfeng_without_sign_validate get:" + redisTemplate.opsForValue().get("shangfeng_without_sign_validate"));


        Map<String, String> hybConfigurationValueMap = new HashMap<>();
       List<HybConfiguration> hybConfigurations = hybConfigurationRepository.findAll();
        for (int i=0;i< hybConfigurations.size();i++){
            HybConfiguration config = hybConfigurations.get(i);
            hybConfigurationValueMap.put("cache_hyb_config_val_" + config.getConfig_key(), config.getConfig_val());
            logger.info("key: + " + "cache_hyb_config_val_" + config.getConfig_key() +"    value:" + config.getConfig_val());

            hybConfigurationValueMap.put("cache_hyb_config_"+ config.getConfig_key(), JSON.toJSONString(config));
            logger.info("key: + " + "cache_hyb_config_val_" + config.getConfig_key() +"    value:" + JSON.toJSONString(config));

        }
        redisTemplate.opsForValue().multiSet(hybConfigurationValueMap);
    }
}
