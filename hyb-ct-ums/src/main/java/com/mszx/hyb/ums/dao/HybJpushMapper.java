package com.mszx.hyb.ums.dao;

import com.mszx.hyb.ums.entity.PlatformJpush;
import com.mszx.hyb.ums.entity.PlatformJpushRecord;
import com.mszx.hyb.ums.entity.PlatformJpushTemplate;
import com.mszx.hyb.ums.param.SearchJpushRecord;
import org.apache.catalina.LifecycleState;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface HybJpushMapper {

    @Select("select * from hyb_ct_platform_jpush_template where template_code = #{template_code}")
    PlatformJpushTemplate findJpushTemplateCode(@Param("template_code") String template_code);

    @Select("select * from hyb_ct_platform_jpush where platformjpushid = #{id}")
    PlatformJpush findPlatformJpushById(@Param("id") Integer id);

    @Insert("INSERT into hyb_ct_platform_jpush_record(userid,business_code,title,content,status,create_at) " +
            "VALUES(#{userid}, #{business_code},#{title},#{content},#{status},#{create_at})")
    void saveJpushRecord(PlatformJpushRecord platformJpushRecord);

    @Select("<script>" +
            "  select * from hyb_ct_platform_jpush_record where business_code = #{business_code} "
            + "<if test='userid != null'>"
            + " and userid = #{userid}"
            + "</if>"
            +"</script>")
    List<PlatformJpushRecord> findByRecordList(SearchJpushRecord searchJpushRecord);

    @Select("<script>" + "select * from  (" +
            "select * from hyb_ct_platform_jpush_record where business_code = 'server_notification' " +
            " union " +
            " select * from hyb_ct_platform_jpush_record where business_code = 'user_notification' and userid = #{userid} ) a order by create_at desc"
            +"</script>")
    List<PlatformJpushRecord> findBYRecordAll(SearchJpushRecord searchJpushRecord);
}
