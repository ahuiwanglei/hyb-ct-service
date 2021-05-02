package com.mszx.hyb.common.base;

import com.mszx.hyb.common.exception.HybCtException;
import com.mszx.hyb.common.model.Result;
import com.mszx.hyb.common.util.ParameterMap;
import com.mszx.hyb.common.util.ResultFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BaseController {
    public Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 成功的Status Code
     */
    private static final int RESCODE_OK = 200;
    /**
     * 失败的Status Code
     */
    private static final int RESCODE_FAIL = 201;

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Result handleException(Exception ex, HttpServletRequest request) {
        logger.error(this.getRequestMap(request) + ex.getMessage());
        if (ex instanceof HybCtException) {
            HybCtException me = (HybCtException) ex;
            logger.error("业务异常", ex);
            ex.printStackTrace();
            return getFailResult(me.getErrorMsg(), Integer.parseInt(me.getErrorCode()));
        }
        else if(ex instanceof MethodArgumentNotValidException){
            logger.error("参数校验异常", ex);
            List<ObjectError> errorList = ((MethodArgumentNotValidException) ex).getBindingResult().getAllErrors();
            return getFailResult(errorList.get(0).getDefaultMessage(), RESCODE_FAIL);
        } else {
            logger.error("系统运行异常", ex);
            ex.printStackTrace();
        }

        //其他异常

        return getFailResult("参数错误，请修改后重试", RESCODE_FAIL);
    }

    protected Result<String> getFailResult(String msg, Integer code) {
        return ResultFactory.getErrorResult(code,msg);
    }

    public Map<String, String> getRequestMap(HttpServletRequest request) {
        Map<String, String> resultMap = new HashMap<String, String>();
        Map<?, ?> requestMap = request.getParameterMap();
        for (Object key : requestMap.keySet()) {
            resultMap.put(String.valueOf(key), request.getParameter(String.valueOf(key)) == null ? "" : request.getParameter(String.valueOf(key)).trim());
        }
        return resultMap;
    }



    /**
     * springMVC 获取requset
     *
     * @return
     */
    public HttpServletRequest getRequest() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
                .getRequest();
        return request;
    }

    /**
     * 获取response
     *
     * @return
     */
    public HttpServletResponse getResponse() {
        HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
                .getResponse();
        return response;
    }

    /**
     * 获取session
     *
     * @return
     */
    public HttpSession getSession() {
        HttpSession session = this.getRequest().getSession();
        return session;
    }

    /**
     * 获取ServletContext
     *
     * @return
     */
    public ServletContext getServletContent() {
        // ServletContext application= request.getServletContext();

        WebApplicationContext webApplicationContext = ContextLoader.getCurrentWebApplicationContext();
        ServletContext servletContext = webApplicationContext.getServletContext();
        return servletContext;
    }

    /**
     * 获取ModelAndView
     *
     * @return
     */
    public ModelAndView getModelAndView() {
        return new ModelAndView();
    }

    public ModelAndView get404ModelAndView() {
        ModelAndView view = new ModelAndView();
        view.setViewName("404");
        return view;
    }

    /**
     * 获取ip
     *
     * @return
     */
    public String getRemortIP() {
        HttpServletRequest request = this.getRequest();
        String ip = "";
        if (request.getHeader("x-forwarded-for") == null) {
            ip = request.getRemoteAddr();
        } else {
            ip = request.getHeader("x-forwarded-for");
        }
        return ip;
    }

    /**
     * 获取port
     *
     * @return
     */
    public int getPort() {
        return this.getRequest().getServerPort();
    }

    /**
     * 获取ip
     * @param
     * @return
     */
    public String getIpAddr() {
        HttpServletRequest request = this.getRequest();
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }

    /**
     * 过滤参数
     *
     * @return
     */
    public ParameterMap getParameterMap() {
        ParameterMap pm = new ParameterMap(this.getRequest());
        pm.put("rip", getRemortIP());
        return pm;
    }

}
