package com.sen.common.constant.enums;

/**
 * @author SEN
 * @version 1.0.0
 * @date 2024-07-17
 */
public enum ExceptionEnum {

    SUCCESS(200, "成功！"),
    BODY_NOT_MATCH(400, "请求的数据格式异常"),
    NOT_FOUND(404, "未找到该资源"),
    INTERNAL_SERVER_ERROR(500, "服务器内部错误");


    private final Integer errorCode;

    private final String errorMsg;

    ExceptionEnum(Integer errorCode, String errorMsg) {
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
    }

    public Integer getErrorCode() {
        return errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }
}
