package com.sen.common.exception;

import com.sen.common.exception.base.BaseException;

/**
 * @author SEN
 * @version 1.0.0
 * @date 2024-07-17
 */
public class FileException extends BaseException {
    private static final long serialVersionUID = 1L;

    public FileException(String code, Object[] args) {
        super("file", code, args, null);
    }

}
