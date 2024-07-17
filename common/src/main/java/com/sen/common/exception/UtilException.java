package com.sen.common.exception;

/**
 * @author SEN
 * @version 1.0.0
 * @date 2024-07-17
 */
public class UtilException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public UtilException(Throwable e) {
        super(e.getMessage(), e);
    }

    public UtilException(String message) {
        super(message);
    }

    public UtilException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
