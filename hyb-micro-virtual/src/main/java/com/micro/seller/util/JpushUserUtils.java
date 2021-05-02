package com.micro.seller.util;

import cn.jpush.api.JPushClient;
import cn.jpush.api.common.TimeUnit;
import cn.jpush.api.common.resp.APIConnectionException;
import cn.jpush.api.common.resp.APIRequestException;
import cn.jpush.api.push.PushResult;
import cn.jpush.api.push.model.Message;
import cn.jpush.api.push.model.Options;
import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.push.model.audience.Audience;
import cn.jpush.api.push.model.notification.AndroidNotification;
import cn.jpush.api.push.model.notification.IosNotification;
import cn.jpush.api.push.model.notification.Notification;
import cn.jpush.api.report.MessagesResult;
import cn.jpush.api.report.ReceivedsResult;
import cn.jpush.api.report.UsersResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
* @author lifengjin
* @createtime 2015年12月15日 上午11:41:15
* @updatetime 2015年12月15日 上午11:41:15
* @version v1.0.0
*/
public class JpushUserUtils {
	
	protected static final Logger LOG = LoggerFactory.getLogger(JpushUserUtils.class);
	
	public static final String MASTER_SECRET = "089d5a7c3c120ee12ab09add";	
	public static final String APP_KEY = "95d244d92b4d7361c20caaa6";
	public static final boolean IS_PRODUCTION = true;
	
	
	/**  
	 * 获取推送所有人的构造PushPayload
	 * @param content 推送内容
	 * @return
	 */
	public static PushPayload buildPushObject_all_all_alert(String content, Map<String, String> extras, String title) {
		return PushPayload.newBuilder()
        .setPlatform(Platform.all())
        .setAudience(Audience.all())
        .setNotification(
        		Notification.newBuilder()
        		.addPlatformNotification(
        				IosNotification.newBuilder()
                                .setAlert(title)
                                .setBadge(1)
                                .setSound("happy.caf")
                                .addExtras(extras)
                                .build()
        			).addPlatformNotification(
    					AndroidNotification.newBuilder()
        				.setAlert(content)
        				.setTitle(title)
        				.addExtras(extras)
        				.build()	
        			).build()
        		)
        .build();
//        return PushPayload.alertAll(content);
    }
	
	public static PushPayload buildPushObjectIos(String title, String content, Map<String, String> extras){
		return PushPayload.newBuilder()
                .setPlatform(Platform.ios())
                .setAudience(Audience.all())
                .setNotification(Notification.newBuilder()
                        .addPlatformNotification(IosNotification.newBuilder()
                                .setAlert(title)
                                .setBadge(1)
                                .setSound("happy.caf")
                                .addExtras(extras)
                                .build())
                        .build())
                 .setMessage(Message.content(content))
                 .setOptions(Options.newBuilder()
                         .setApnsProduction(IS_PRODUCTION)
                         .build())
                 .build();
	}
	
	public static PushPayload buildPushObjectAndroid(String title, String content,Map<String, String> extras){
		return PushPayload.newBuilder()
                .setPlatform(Platform.android())
                .setNotification(Notification.newBuilder().
                		addPlatformNotification(
                				AndroidNotification.newBuilder()
                				.setAlert(content)
                				.setTitle(title)
                				.addExtras(extras)
                				.build()
                			).build())
                 .setMessage(Message.content(content))
                 .setOptions(Options.newBuilder()
                         .setApnsProduction(IS_PRODUCTION)
                         .build())
                 .build();
	}
	/**
	 * 获取指定alias的PushPayload
	 * @param content
	 * @param alias 
	 * @return
	 */
	 public static PushPayload buildPushObject_all_alias_alert(String content, String[] alias) {
        return PushPayload.newBuilder()
                .setPlatform(Platform.all())
                .setAudience(Audience.alias(alias)) 
                .setNotification(Notification.alert(content))
                .build();
     }
	 
	 /** 1 TODO
	  * 获取指定alias的PushPayload  带标示extras
	  * @param content
	  * @param alias
	  * @param extras 7:预约订单成功向商户推送
	  * @return
	  */
	 public static PushPayload buildPushObject_all_alias_alert_extras(String content, String[] alias,Map<String,String> extras) {
		 return PushPayload.newBuilder()
			        .setPlatform(Platform.all())
			        .setAudience(Audience.alias(alias))
			        .setNotification(
			        		Notification.newBuilder()
			        		.addPlatformNotification(
			        				IosNotification.newBuilder()
			                                .setAlert(content)
			                                .setBadge(1)
			                                .setSound("happy.caf")
			                                .addExtras(extras)
			                                .build()
			        			).addPlatformNotification(
			    					AndroidNotification.newBuilder()
			        				.setAlert(content)
			        				.setTitle(SystemConstant.APP_NAME)
			        				.setBuilderId(2)
			        				.addExtras(extras)
			        				.build()	
			        			).build()
			        		)
			        .setOptions(Options.newBuilder()
                    .setApnsProduction(IS_PRODUCTION)//测试环境 false 正式环境true
                    .setTimeToLive(0)//从消息推送时起，保存离线的时长。秒为单位。最多支持10天（864000秒）。
				     //又名缓慢推送，把原本尽可能快的推送速度，降低下来，给定的n分钟内，均匀地向这次推送的目标用户推送。最大值为1400.未设置则不是定速推送。  
				    //0 表示该消息不保存离线。即：用户在线马上发出，当前不在线用户将不会收到此消息。
				                   //此参数不设置则表示默认，默认为保存1天的离线消息（86400秒）。
                    .build())
			     .build();
	 }
	 
	/*** 2 TODO
	 * 通过【RegistrationId】唯一标识向用户推送
	 * @param content
	 * @param RegistrationId
	 * @param extras
	 * @return
	 */
	 public static PushPayload buildPushObject_RegistrationIds_alert_extras(String content, String[] RegistrationId,Map<String,String> extras) {
		 return PushPayload.newBuilder()
			        .setPlatform(Platform.all())
			        .setAudience(Audience.registrationId(RegistrationId))
			        .setNotification(
			        		Notification.newBuilder()
			        		.addPlatformNotification(
			        				IosNotification.newBuilder()
			                                .setAlert(content)
			                                .setBadge(1)
			                                .setSound("happy.caf")
			                                .addExtras(extras)
			                                .build()
			        			).addPlatformNotification(
			    					AndroidNotification.newBuilder()
			        				.setAlert(content)
			        				.setTitle(SystemConstant.APP_NAME)
			        				.setBuilderId(2)
			        				.addExtras(extras)
			        				.build()	
			        			).build()
			        		)
			        .setOptions(Options.newBuilder()
                    .setApnsProduction(IS_PRODUCTION)//测试环境 false 正式环境true
                     .setTimeToLive(300)//从消息推送时起，保存离线的时长。秒为单位。最多支持10天（864000秒）。
				                    //0 表示该消息不保存离线。即：用户在线马上发出，当前不在线用户将不会收到此消息。
				                   //此参数不设置则表示默认，默认为保存1天的离线消息（86400秒）。
                    .build())
			     .build();
	 }
	 
	 /** 
	  * 获取指定tags的PushPayload
	  * @param content
	  * @param tags
	  * @return
	  */
	 public static PushPayload buildPushObject_all_tags_alert(String content, String[] tags) {
		 return PushPayload.newBuilder()
				 .setPlatform(Platform.all())
				 .setAudience(Audience.tag(tags)) 
				 .setNotification(Notification.alert(content))
				 .build();
	 }
	 
	 /**
	  * 获取指定tags的PushPayload
	  * @param content
	  * @param extras
	  * @return
	  */
	 public static PushPayload buildPushObject_all_tags_alert_extras(String content, String[] tags,Map<String,String> extras) {
		 return PushPayload.newBuilder()
			        .setPlatform(Platform.all())
			        .setAudience(Audience.tag(tags))
			        .setNotification(
			        		Notification.newBuilder()
			        		.addPlatformNotification(
			        				IosNotification.newBuilder()
			                                .setAlert(SystemConstant.APP_NAME)
			                                .setBadge(1)
			                                .setSound("happy.caf")
			                                .addExtras(extras)
			                                .build()
			        			).addPlatformNotification(
			    					AndroidNotification.newBuilder()
			        				.setAlert(content)
			        				.setTitle(SystemConstant.APP_NAME)
			        				.setBuilderId(2)
			        				.addExtras(extras)
			        				.build()	
			        			).build()
			        		)
			        .build();
	 }
	 
	 /**
		 * 获取指定registrationIds的PushPayload
		 * @param content
		 * @param registrationIds
		 * @return
		 */
	 public static PushPayload buildPushObject_all_registrationIds_alert(String content, String[] registrationIds) {
        return PushPayload.newBuilder()
                .setPlatform(Platform.all())	                
                .setAudience(Audience.registrationId(registrationIds))	              
                .setNotification(Notification.alert(content))
                .build();
     }
		 
	/**
	 * 获取JpushClient对象
	 * @return
	 */
	public static JPushClient getJpushClient(){
		return new JPushClient(MASTER_SECRET, APP_KEY, 3);
	}
	
	/**
	 * 对所有平台 所有客户端 发送提醒推送
	 * @param content
	 * @return
	 * @throws APIConnectionException
	 * @throws APIRequestException
	 */
	public static PushResult sentAlertAll(String content,Map<String, String> extras, String title) throws Exception {
            PushResult result = getJpushClient().sendPush(buildPushObject_all_all_alert(content, extras, title ));
            return result;
	}
	
	/**
	 * 对IOS平台  所有客户端 发送提醒和消息
	 * @param title
	 * @param content
	 * @param extras
	 * @return
	 * @throws APIConnectionException
	 * @throws APIRequestException
	 */
	public static PushResult sentAM4Ios(String title , String content, Map<String, String> extras) {
        try{    
        	PushResult result = getJpushClient().sendPush(buildPushObjectIos(title, content, extras));
        	return result;
        }catch(APIRequestException e){
        	 LOG.error("Error response from JPush server. Should review and fix it. ", e);
             LOG.info("HTTP Status: " + e.getStatus());
             LOG.info("Error Code: " + e.getErrorCode());
             LOG.info("Error Message: " + e.getErrorMessage());
        } catch (APIConnectionException e) {
        	 LOG.error("Connection error. Should retry later. ", e);
		}    
		
        return null;
	}
	
	/**
	 * 对android平台  所有客户端 发送提醒和消息
	 * @param title
	 * @param content
	 * @param extras
	 * @return
	 * @throws APIConnectionException
	 * @throws APIRequestException
	 */
	public static PushResult sentAM4Android(String title , String content, Map<String, String> extras) throws Exception {
            PushResult result = getJpushClient().sendPush(buildPushObjectAndroid(title, content, extras));
            return result;
	}
	
	/**
	 * 对所用平台 指定alias客户端 发送提醒推送
	 * @param content
	 * @return
	 * @throws APIConnectionException
	 * @throws APIRequestException
	 */
	public static PushResult sentAlertByAlias(String content, String[] alias) throws Exception {
        PushResult result = getJpushClient().sendPush(buildPushObject_all_alias_alert(content, alias));
        return result;
	}
	
	/*** 1
	 * 通过【alias】向用户推送消息
	 * @param content
	 * @param alias
	 * @param extras
	 * @return
	 * @throws Exception
	 */
	public static PushResult sentAlertByAliasExtras(String content, String[] alias,Map<String,String> extras) throws Exception {
		PushResult result = getJpushClient().sendPush(buildPushObject_all_alias_alert_extras(content, alias,extras));
		LOG.info("极光推送返参："+result);
		return result;
	}
	
	/*** 2
	 * 通过【RegistrationId】向用户推送消息
	 * @param content
	 * @param alias
	 * @param extras
	 * @return
	 * @throws Exception
	 */
	public static PushResult sentAlertByRegistrationIdExtras(String content, String[] alias,Map<String,String> extras) throws Exception {
		PushResult result = getJpushClient().sendPush(buildPushObject_RegistrationIds_alert_extras(content, alias,extras));
		LOG.info("极光推送返参："+result);
		return result;
	}
	
	/**
	 * 对所用平台 根据registrationIds客户端 发送提醒推送
	 * @param content
	 * @return
	 * @throws APIConnectionException
	 * @throws APIRequestException
	 */
	public static PushResult sentAlertByRegistrationIds(String content, String[] registrationIds) throws Exception {
        PushResult result = getJpushClient().sendPush(buildPushObject_all_registrationIds_alert(content, registrationIds));
        return result;
	}
	
	/**
	 * 对所用平台 指定tags客户端 发送提醒推送
	 * @param content
	 * @return
	 * @throws APIConnectionException
	 * @throws APIRequestException
	 */
	public static PushResult sentAlertByTags(String content, String[] tags) throws Exception {
        PushResult result = getJpushClient().sendPush(buildPushObject_all_tags_alert(content, tags));
        return result;
	}
	
	/**
	 * 对所用平台 指定tags客户端 发送提醒推送
	 * @param content
	 * @return
	 * @throws APIConnectionException
	 * @throws APIRequestException
	 */
	public static PushResult sentAlertByTagsExtras(String content, String[] tags,Map<String, String> extras) throws Exception {
        PushResult result = getJpushClient().sendPush(buildPushObject_all_tags_alert_extras(content, tags,extras));
        return result;
	}
	
	/**
	 * 对所用平台 指定alias客户端 发送提醒推送
	 * @param content
	 * @param alias
	 * @return
	 * @throws APIConnectionException
	 * @throws APIRequestException
	 */
	public static PushResult sentAlertByAlias(String content, List<String> alias) throws Exception{
        
		if(alias != null){
			String[] aliasArray = new String[alias.size()];
			aliasArray = alias.toArray(aliasArray);
			return sentAlertByAlias(content , aliasArray);
        }
		return null;
	}
	
	/***
	 * 向用户推送消息 【别名】推送
	 * @param content
	 * @param alias
	 * @param extras
	 * @return
	 * @throws Exception
	 */
	public static PushResult sentAlertByAliasExtras(String content, List<String> alias,Map<String,String> extras) throws Exception{
		try {
			if(alias != null){
				String[] aliasArray = new String[alias.size()];
				aliasArray = alias.toArray(aliasArray);
				return sentAlertByAliasExtras(content , aliasArray,extras);
			}
		} catch (Exception e) {
		}
		return null;
	}
	
	/***
	 * 向用户推送消息 【RegistrationId】推送
	 * @param content
	 * @param RegistrationIds
	 * @param extras
	 * @return
	 * @throws Exception
	 */
	public static PushResult sentAlertByRegistrationIdsExtras(String content, List<String> RegistrationIds,Map<String,String> extras) throws Exception{
		try {
			if(RegistrationIds != null){
				String[] aliasArray = new String[RegistrationIds.size()];
				aliasArray = RegistrationIds.toArray(aliasArray);
				return sentAlertByRegistrationIdExtras(content , aliasArray,extras);
			}
		} catch (Exception e) {
		}
		return null;
	}
	
	/**
	 * 对所用平台 指定registrationIds客户端 发送提醒推送
	 * @param content
	 * @param registrationIds
	 * @return
	 * @throws APIConnectionException
	 * @throws APIRequestException
	 */
	public static PushResult sentAlertByRegistrationIds(String content, List<String> registrationIds) throws Exception{
        
		if(registrationIds != null){
			String[] registrationIdsArray = new String[registrationIds.size()];
			registrationIdsArray = registrationIds.toArray(registrationIdsArray);
			return sentAlertByRegistrationIds(content , registrationIdsArray);
        }
		return null;
	}
	
	/**
	 * 对所用平台 指定tag客户端 发送提醒推送
	 * @param content
	 * @param tags
	 * @return
	 * @throws APIConnectionException
	 * @throws APIRequestException
	 */
	public static PushResult sentAlertByTags(String content, List<String> tags) throws Exception{
        
		if(tags != null){
			String[] aliasArray = new String[tags.size()];
			aliasArray = tags.toArray(aliasArray);
			return sentAlertByTags(content , aliasArray);
        }
		return null;
	}
	
	/**
	 * 对所用平台 指定tag客户端 发送提醒推送
	 * @param content
	 * @param extras
	 * @return
	 * @throws APIConnectionException
	 * @throws APIRequestException
	 */
	public static PushResult sentAlertByTagsExtras(String content, List<String> tags,Map<String,String> extras) throws Exception{
        
		if(tags != null){
			String[] aliasArray = new String[tags.size()];
			aliasArray = tags.toArray(aliasArray);
			return sentAlertByTagsExtras(content , aliasArray ,extras);
        }
		return null;
	}
	
	
	public static void testGetReport() {
        JPushClient jpushClient = new JPushClient(MASTER_SECRET, APP_KEY);
		try {
            ReceivedsResult result = jpushClient.getReportReceiveds("1942377665");
            LOG.debug("Got result - " + result);
            
        } catch (APIConnectionException e) {
            LOG.error("Connection error. Should retry later. ", e);
            
        } catch (APIRequestException e) {
            LOG.error("Error response from JPush server. Should review and fix it. ", e);
            LOG.info("HTTP Status: " + e.getStatus());
            LOG.info("Error Code: " + e.getErrorCode());
            LOG.info("Error Message: " + e.getErrorMessage());
        }
	}

    public static void testGetUsers() {
        JPushClient jpushClient = new JPushClient(MASTER_SECRET, APP_KEY);
        try {
            UsersResult result = jpushClient.getReportUsers(TimeUnit.DAY, "2014-06-10", 3);
            LOG.debug("Got result - " + result);

        } catch (APIConnectionException e) {
            LOG.error("Connection error. Should retry later. ", e);
            
        } catch (APIRequestException e) {
            LOG.error("Error response from JPush server. Should review and fix it. ", e);
            LOG.info("HTTP Status: " + e.getStatus());
            LOG.info("Error Code: " + e.getErrorCode());
            LOG.info("Error Message: " + e.getErrorMessage());
        }
    }

    public static void testGetMessages() {
    	JPushClient jpushClient = new JPushClient(MASTER_SECRET, APP_KEY);
        try {
            MessagesResult result = jpushClient.getReportMessages("269978303");
            LOG.debug("Got result - " + result);
            
        } catch (APIConnectionException e) {
            LOG.error("Connection error. Should retry later. ", e);
            
        } catch (APIRequestException e) {
            LOG.error("Error response from JPush server. Should review and fix it. ", e);
            LOG.info("HTTP Status: " + e.getStatus());
            LOG.info("Error Code: " + e.getErrorCode());
            LOG.info("Error Message: " + e.getErrorMessage());
        }
    }
    /***
     * TODO
     * @param args
     */
	public static void main(String[] args) {
//		try {
//			Map<String,String> map = new HashMap<String, String>();
//			map.put("type", "1");
//			List<String> alias = new ArrayList<>();
//			alias.add("15313771409");
//			PushResult result = JpushUserUtils.sentAlertByTagsExtras("hello",alias,map);
//			long msg_id = result.msg_id;
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
		
		try {
			List<String> alias = new ArrayList<>();
			Map<String,String> map = new HashMap<>();
			map.put("type", "3");
			alias.add("15156679232");
			JpushUserUtils.sentAlertByAliasExtras(SystemConstant.APP_NAME + "离线推送测试", alias,map);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
}
