package com.sen.common.core.domain;

import com.sen.common.constant.HttpStatus;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * 接口统一返回类
 *
 * @author SEN
 * @version 1.0.0
 * @date 2024-06-29
 */
public class ResponseData extends HashMap<String, Object> {

    private static final long serialVersionUID = 1L;

    /**
     * 状态码
     */
    public static final String CODE = "code";

    /**
     * 返回消息
     */
    public static final String MSG = "msg";

    /**
     * 返回数据内容
     */
    public static final String DATA = "data";


    /**
     * 初始化一个ResponseData对象
     */
    public ResponseData() {
    }

    /**
     * 初始化一个ResponseData对象
     *
     * @param code 状态码
     * @param msg 返回消息
     */
    public ResponseData(int code, String msg) {
        super.put(CODE, code);
        super.put(MSG, msg);
    }

    /**
     * 初始化一个ResponseData对象
     *
     * @param code 状态码
     * @param msg 返回消息
     * @param data 返回数据
     */
    public ResponseData(int code, String msg, Object data) {
        super.put(CODE, code);
        super.put(MSG, msg);
        if (!Objects.isNull(data)) {
            super.put(DATA, data);
        }
    }

    /**
     * 返回成功消息
     *
     * @return 成功消息
     */
    public static ResponseData success() {
        return ResponseData.success("操作成功");
    }

    /**
     * 返回成功数据
     *
     * @return 成功消息
     */
    public static ResponseData success(Object data) {
        return ResponseData.success("操作成功", data);
    }


    /**
     * 返回成功消息
     *
     * @param msg 返回内容
     * @return 成功消息
     */
    public static ResponseData success(String msg) {
        return ResponseData.success(msg, null);
    }


    /**
     * 返回成功消息
     *
     * @param msg  返回内容
     * @param data 数据对象
     * @return 成功消息
     */
    public static ResponseData success(String msg, Object data) {
        return new ResponseData(HttpStatus.SUCCESS, msg, data);
    }

    /**
     * 返回错误消息
     *
     * @return
     */
    public static ResponseData error() {
        return ResponseData.error("操作失败");
    }


    /**
     * 返回错误消息
     *
     * @param msg 返回内容
     * @return 警告消息
     */
    public static ResponseData error(String msg) {
        return ResponseData.error(msg, null);
    }


    /**
     * 返回错误消息
     *
     * @param msg  返回内容
     * @param data 数据对象
     * @return 警告消息
     */
    public static ResponseData error(String msg, Object data) {
        return new ResponseData(HttpStatus.ERROR, msg, data);
    }


    /**
     * 返回错误消息
     *
     * @param code 状态码
     * @param msg  返回内容
     * @param data 数据对象
     * @return 警告消息
     */
    public static ResponseData error(int code, String msg, Object data) {
        return new ResponseData(code, msg, data);
    }


    /**
     * 返回错误消息
     *
     * @param code 状态码
     * @param msg  返回内容
     * @return 警告消息
     */
    public static ResponseData error(int code, String msg) {
        return new ResponseData(code, msg, null);
    }

}
