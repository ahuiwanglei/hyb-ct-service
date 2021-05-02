package com.mszx.hyb.common.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.Map;


/**
 * @desc Http常用类
 * @author 
 * @Copyright: (c) 2016年4月15日 上午2:00:09
 * @company: 民生在线  
 */
public class HttpUtil {
	private static Logger log = LoggerFactory.getLogger(HttpUtil.class);
	public static String getPostResult(String url, Map<String, Object> map){
		PrintWriter out = null;
		BufferedReader in = null;
		String result = "";
		String params = convertParams(map);
		try {
			URL realUrl = new URL(url);
			// 打开和URL之间的连接
			URLConnection conn = realUrl.openConnection();
			// 设置通用的请求属性
			conn.setRequestProperty("accept", "*/*");
			conn.setRequestProperty("connection", "Keep-Alive");
			conn.setRequestProperty("user-agent",
					"Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
			conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset=utf-8");
			// 发送POST请求必须设置如下两行
			conn.setDoOutput(true);
			conn.setDoInput(true);
			// 获取URLConnection对象对应的输出流
			out = new PrintWriter(conn.getOutputStream());
			// 发送请求参数
			out.print(params);
			// flush输出流的缓冲
			out.flush();
			// 定义BufferedReader输入流来读取URL的响应
			in = new BufferedReader(
					new InputStreamReader(conn.getInputStream()));
			String line;
			while ((line = in.readLine()) != null) {
				result += line;
			}
		} catch (Exception e) {
			log.info("发送 POST 请求出现异常！" + e);
			e.printStackTrace();
		}
		// 使用finally块来关闭输出流、输入流
		finally {
			try {
				if (out != null) {
					out.close();
				}
				if (in != null) {
					in.close();
				}
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		/*try {
			result = new String(result.getBytes("utf-8"), "gbk");
		} catch (Exception e) {
			e.printStackTrace();
		}*/
		return result;
	}
	
	/**
	 * map请求参数转化为 name1=value1&name2=value2 的形式
	 * @param map
	 * @return
	 */
	private static String convertParams(Map<String, Object> map){
		StringBuilder params = new StringBuilder("");
		if(map != null && !map.isEmpty()){
			for(Map.Entry<String, Object> entry : map.entrySet()){
				String key = entry.getKey();
				if(key != null && entry.getValue() != null){
					params.append(key);
					params.append("=");
					params.append(entry.getValue());
					params.append("&");
				}
			}
		}
		log.info("params:" + params);
		return params.toString();
	}

}
