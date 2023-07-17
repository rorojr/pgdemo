package com.css.demo.component.error;

import com.css.demo.model.common.BaseMessage;

public class BizException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private int code;
    private String message;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public BizException() {
        super();
    }

    public BizException(int code, String message) {
        super(message);
        this.code = code;
        this.message = message;
    }

    public BizException(String message) {
        super(message);
        this.message = message;
    }

    public BizException(String message, Throwable cause) {
        super(message);
        this.message = message;
    }

    public BizException(int code, String message, Throwable cause) {
        super(message, cause);
        this.code = code;
        this.message = message;
    }

    public BizException(BaseMessage baseMessage) {
        this(baseMessage.getCode(), baseMessage.getMessage());
    }

    public BizException(BaseMessage baseMessage, Throwable cause) {
        this(baseMessage.getCode(), baseMessage.getMessage(), cause);
    }

    protected BizException(int code, String message, Throwable cause,
                           boolean enableSuppression,
                           boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.code = code;
        this.message = message;
    }
}
