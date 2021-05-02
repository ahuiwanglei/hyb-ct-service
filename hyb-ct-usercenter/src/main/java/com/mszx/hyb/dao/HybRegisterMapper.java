package com.mszx.hyb.dao;

import com.mszx.hyb.entity.HybRegister;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;

public interface HybRegisterMapper {
    int deleteByPrimaryKey(String pkregister);

    int insert(HybRegister record);

    int insertSelective(HybRegister record);

    HybRegister selectByPrimaryKey(String pkregister);

    int updateByPrimaryKeySelective(HybRegister record);

    int updateByPrimaryKey(HybRegister record);
    
    /*自定义操作*/
    HybRegister selectByPhoneKey(@Param("phoneno") String phone,@Param("appid") String appid);
    HybRegister selectByWeixinKey(@Param("openid") String openid, @Param("appid") String appid);
    HybRegister selectByQQKey(@Param("openid") String openid,@Param("appid") String appid);

}