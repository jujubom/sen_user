package com.sen.common.exception;

/**
 * @author SEN
 * @version 1.0.0
 * @date 2024-07-17
 */
public class UserPasswordNotMatchException extends UserException {
    private static final long serialVersionUID = 1L;

    public UserPasswordNotMatchException() {
        super("user.password.not.match", null);
    }

    public UserPasswordNotMatchException(String message) {
        super(message, null);
    }

}
