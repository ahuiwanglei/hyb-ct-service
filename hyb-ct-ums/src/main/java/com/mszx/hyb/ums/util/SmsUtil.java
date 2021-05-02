package com.mszx.hyb.ums.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.QuerySendDetailsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.QuerySendDetailsResponse;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.mszx.hyb.ums.controller.SmsController;
import com.mszx.hyb.ums.util.huaxin.*;
import org.apache.http.HttpEntity;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.concurrent.FutureCallback;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.soap.*;
import java.io.ByteArrayInputStream;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.lang3.StringUtils;

import static com.mszx.hyb.ums.util.huaxin.SendMessageHwasin.SM_UserName;

public class SmsUtil {
    protected static final Logger logger = LoggerFactory.getLogger(SmsUtil.class);
    final static String product = "Dysmsapi";//短信API产品名称（短信产品名固定，无需修改）
    final static String domain = "dysmsapi.aliyuncs.com";//短信API产品域名（接口地址固定，无需修改）

    public static String SM_Url = "http://dx.ipyy.net/smsJson.aspx?";


    /*  <br>　　　　　2018年3月已知
    中国电信号段
        133,149,153,173,177,180,181,189,199
    中国联通号段
        130,131,132,145,155,156,166,175,176,185,186
    中国移动号段
        134(0-8),135,136,137,138,139,147,150,151,152,157,158,159,178,182,183,184,187,188,198
    其他号段
        14号段以前为上网卡专属号段，如中国联通的是145，中国移动的是147等等。
        虚拟运营商
            电信：1700,1701,1702
            移动：1703,1705,1706
            联通：1704,1707,1708,1709,171
        卫星通信：148(移动) 1349
    */
    public static boolean isMobile(String str) {
        Pattern p = null;
        Matcher m = null;
        boolean b = false;
        String s2="^[1](([3][0-9])|([4][5,7,9])|([5][0-9])|([6][6])|([7][3,5,6,7,8])|([8][0-9])|([9][8,9]))[0-9]{8}$";// 验证手机号
        if(StringUtils.isNotBlank(str)){
            p = Pattern.compile(s2);
            m = p.matcher(str);
            b = m.matches();
        }
        return b;
    }

    /**
     *
     * @param access_key
     * @param access_secret
     * @param signname
     * @param tempate_code
     * @param phone
     * @param args
     * @return
     */
    public static boolean aliSendSms(String access_key, String access_secret,String signname,String tempate_code, String phone, String[] args,String content){
        //初始化acsClient,暂不支持region化
        try {
            //初始化acsClient,暂不支持region化
            IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", access_key, access_secret);
            DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", product, domain);
            IAcsClient acsClient = new DefaultAcsClient(profile);

            //组装请求对象-具体描述见控制台-文档部分内容
            SendSmsRequest request = new SendSmsRequest();
            //必填:待发送手机号
            request.setPhoneNumbers(phone);
            //必填:短信签名-可在短信控制台中找到
            request.setSignName(signname);
            //必填:短信模板-可在短信控制台中找到
            request.setTemplateCode(tempate_code);
            //可选:模板中的变量替换JSON串,如模板内容为"亲爱的${name},您的验证码为${code}"时,此处的值为
            JSONObject jsonObject = new JSONObject();
            List<String> pname = getTempParamName(content);
            if(args != null && args.length >0){
                for (int i=0;i< args.length;i++){
                    jsonObject.put(pname.get(i), args[i]);
                }
                request.setTemplateParam(jsonObject.toJSONString());
            }

//            logger.info("短信模版Code：" + tempate_code +"  签名：" + signname +" 手机号：" + phone +" 参数：" + jsonObject.toJSONString());
            SendSmsResponse sendSmsResponse = acsClient.getAcsResponse(request);
            logger.info("阿里短信发送结果：" + JSON.toJSONString(sendSmsResponse));
            return sendSmsResponse.getCode() != null && sendSmsResponse.getCode().equals("OK");
        } catch (ClientException e) {
            e.printStackTrace();
        }
        return false;
    }

    private static List<String> getTempParamName(String content){
        String[] bb  = content.split("\\$\\{");
        List<String> pname = new ArrayList<>();
        for (int i=0;i<bb.length;i++){
            if(bb[i].indexOf("}") > 0){
                pname.add(bb[i].split("\\}")[0]);
            }
        }
        return pname;

    }

    private static HttpClient httpclient;
    public static boolean huaxinSendSms(String userName, String password, String phone,String content, String signname) throws Exception{
         httpclient = new SSLClient();
        String wsdl = "https://dx.ipyy.net/webservice.asmx?wsdl";
        String mobiles=phone;							//多个手机号用“,”分隔
        String extNumber="";

        //定时短信需指定此字段，须UTC格式：2016-12-06T08:09:10+08:00
        String planSendTime="";

        HttpPost post = new HttpPost(wsdl);
        post.setHeader("Content-Type","application/soap+xml; charset=UTF-8");
        content = content +"【" + signname + "】";
        String soapData=buildSoapData(userName, password,mobiles, content,extNumber,planSendTime);
        StringEntity se=new StringEntity(soapData,"UTF-8");
        post.setEntity(se);
        HttpResponse response = httpclient.execute(post);
        try {
            System.out.println(response.getStatusLine());
            HttpEntity entity = response.getEntity();
            // 将字符转化为XML
            String returnString = EntityUtils.toString(entity, "UTF-8");

            SOAPMessage msg=formatSoapString(returnString);
            msg.writeTo(System.out);
            SOAPBody body=msg.getSOAPBody();
            Map<String,String> map=new HashMap<String,String>();
            Iterator<SOAPElement> iterator=body.getChildElements();
            parseSoap(iterator,map);
            logger.info("华信短信发送结果："+ JSON.toJSONString(map));
            logger.info("返回信息提示："+map.get("Description"));
            logger.info("返回状态为："+map.get("StatusCode"));
            logger.info("返回余额："+map.get("Amount"));
            logger.info("返回本次任务ID："+map.get("MsgId"));
            logger.info("返回成功短信数："+map.get("SuccessCounts"));
            int sc = Integer.parseInt(map.get("SuccessCounts"));
            return sc >0 ;
        } catch (Exception ex) {
            logger.info(ex.getMessage());
        }
        return false;
    }

    private static String buildSoapData(String userName,String password,String mobiles,String content,String extNumber,String planSendTime) throws UnsupportedEncodingException {
        StringBuffer soap=new StringBuffer();

        soap.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>");
        soap.append("<soap12:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap12=\"http://www.w3.org/2003/05/soap-envelope\">");
        soap.append("<soap12:Body>");
        soap.append("<SendSms xmlns=\"http://www.yysms.com/\">");
        soap.append("<userName>"+userName+"</userName>");
        soap.append("<password>"+password+"</password>");
        soap.append("<sms>");
        soap.append("<Msisdns>"+mobiles+"</Msisdns>");
        soap.append("<SMSContent><![CDATA["+content+"]]></SMSContent>");
        soap.append("<ExtNumber>"+extNumber+"</ExtNumber>");
        soap.append("<PlanSendTime xsi:nil='true'>"+planSendTime+"</PlanSendTime>");
        soap.append("</sms>");
        soap.append("</SendSms>");
        soap.append("</soap12:Body>");
        soap.append("</soap12:Envelope>");
        return soap.toString();
    }

    /**
     * 把soap字符串格式化为SOAPMessage
     *
     * @param soapString
     * @return
     * @see [类、类#方法、类#成员]
     */
    private static SOAPMessage formatSoapString(String soapString) {
        MessageFactory msgFactory;
        try {
            msgFactory = MessageFactory.newInstance(SOAPConstants.SOAP_1_2_PROTOCOL);
            SOAPMessage reqMsg = msgFactory.createMessage(new MimeHeaders(),
                    new ByteArrayInputStream(soapString.getBytes("UTF-8")));
            reqMsg.saveChanges();
            return reqMsg;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private static void parseSoap(Iterator<SOAPElement> iterator, Map<String, String> map){
        while(iterator.hasNext()){
            SOAPElement element=iterator.next();
            if("SendSmsResult".equals(element.getParentElement().getNodeName())){
                map.put(element.getNodeName(),element.getValue());
            }
            if(null==element.getValue() && element.getChildElements().hasNext()){
                parseSoap(element.getChildElements(),map);
            }
        }
    }
    public static void main(String[] args) {
        System.out.println("是正确格式的手机号:"+isMobile("13083342041"));
    }
}
