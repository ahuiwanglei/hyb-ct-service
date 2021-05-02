package com.mszx.hyb.common.interceptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Enumeration;

@Component
public class LogCommonInterceptor extends HandlerInterceptorAdapter {
	public Logger logger = LoggerFactory.getLogger(LogCommonInterceptor.class);

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		if (request.getRequestURI().contains("/resource/") 
				|| request.getRequestURI().contains("/wap/")) {
			return true;
		}
		logger.info("********************************start***********************************************");
//		logger.info("time:" + DateTimeUtil.getDateStringDatetime(new Date()));
		logger.info("Method:" + request.getMethod());
		logger.info("URL:" + request.getRequestURI());
//		logger.info("header parameters:");
//		Enumeration<String> headParams = request.getHeaderNames();
//		for (Enumeration e = headParams; e.hasMoreElements();) {
//			String thisName = e.nextElement().toString();
//			logger.info(thisName +":" + request.getHeader(thisName));
//		}
		logger.info("request parameters:");
		Enumeration<String> params = request.getParameterNames();
		for (Enumeration e = params; e.hasMoreElements();) {
			String thisName = e.nextElement().toString();
			logger.info(thisName +":" + request.getParameter(thisName));
		}
//		logger.info("IP:" + CommonUtil.getIpAddr(request));
		logger.info("UA:" + request.getHeader("User-Agent"));
		logger.info("DeviceInfo: " + request.getHeader("deviceinfo"));
		logger.info("*********************************end**********************************************");
		return true;
	}

}
