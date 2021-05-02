package com.mszx.hyb.sdk.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CSConfigException extends Exception {
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());
    private String errorCode ="201";

    private String errorMsg;

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    /**
     */
    public CSConfigException() {
        super();
        logger.warn("业务异常");
    }

    /**
     *
     * @param retCode
     * @param message
     */
    public CSConfigException(Integer retCode, String message) {
        super(message);
        this.errorCode = retCode.toString();
        this.errorMsg = message;
    }

    /**
     *
     * @param message
     */
    public CSConfigException(String message){
        super(message);
        this.errorMsg = message;
    }
}
