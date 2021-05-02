package com.mszx.hyb.ums.dao;

import com.mszx.hyb.ums.entity.PlatformSms;
import com.mszx.hyb.ums.entity.PlatformSmsTemplate;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface HybPlatformSmsMapper {


    @Select("select * from hyb_ct_platform_sms where platformsmsid = #{id}")
    public PlatformSms findPlatformSmsById(Integer id);

    @Select("select * from hyb_ct_platform_sms_template where template_code = #{template_code}")
    PlatformSmsTemplate findPlatformSmsTemplateByCode(String template_code);

    @Select("select * from hyb_ct_platform_sms_template where smstemplateid = #{templateid}")
    PlatformSmsTemplate findPlatformSmsTemplateById(int templateid);
}
