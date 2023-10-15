package com.chocolate.blogsch.core.exception;


import com.chocolate.blogsch.core.domain.ErrorCode;

public class ServiceException extends RuntimeException {

    ErrorCode errorCode;

    public ServiceException(String message) {
        super(message);
    }

    public ServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public ServiceException(ErrorCode errorCode) {
        super();
        this.errorCode = errorCode;
    }

    public ServiceException(String message, ErrorCode errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public ServiceException(String message, ErrorCode errorCode, Throwable cause) {
        super(message, cause);
        this.errorCode = errorCode;
    }
}
