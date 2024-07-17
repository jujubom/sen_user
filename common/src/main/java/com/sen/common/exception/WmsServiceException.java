package com.sen.common.exception;

import lombok.Getter;

/**
 * @author SEN
 * @version 1.0.0
 * @date 2024-07-17
 */
@Getter
public class WmsServiceException extends ServiceException {
    private static final long serialVersionUID = 1L;

    /**
     * 数据
     */
    private Object data;


    /**
     * 空构造方法，避免反序列化问题
     */
    public WmsServiceException() {
    }

    public WmsServiceException(String message) {
        super(message);
    }

    public WmsServiceException(String message, Integer code) {
        super(message, code);
    }

    public WmsServiceException(String message, Integer code, Object data) {
        super(message, code);
        this.data = data;
    }

}
