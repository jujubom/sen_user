package com.sen.common.exception;


/**
 * @author SEN
 * @version 1.0.0
 * @date 2024-07-17
 */
public class CaptchaException extends UserException {
    private static final long serialVersionUID = 1L;

    public CaptchaException() {
        super("user.jcaptcha.error", null);
    }
}
