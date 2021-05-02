package com.mszx.hyb.common.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HybCtException extends Exception{
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
    public HybCtException() {
        super();
        logger.warn("业务异常");
    }

    /**
     *
     * @param retCode
     * @param message
     */
    public HybCtException(Integer retCode, String message) {
        super(message);
        this.errorCode = retCode.toString();
        this.errorMsg = message;
    }

    /**
     *
     * @Title:MdmException
     * @Description:TODO
     * @param message
     */
    public HybCtException(String message){
        super(message);
        this.errorMsg = message;
    }
}
