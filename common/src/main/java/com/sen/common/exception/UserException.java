package com.sen.common.exception;

import com.sen.common.exception.base.BaseException;

/**
 * @author SEN
 * @version 1.0.0
 * @date 2024-07-17
 */
public class UserException extends BaseException {
    private static final long serialVersionUID = 1L;

    public UserException(String code, Object[] args) {
        super("user", code, args, null);
    }
}
