package com.sen.common.exception;

/**
 * @author SEN
 * @version 1.0.0
 * @date 2024-07-17
 */
public class CaptchaExpireException extends UserException {
    private static final long serialVersionUID = 1L;

    public CaptchaExpireException() {
        super("user.jcaptcha.expire", null);
    }
}