package com.sen.common.core.domain.model.vo;

import lombok.Data;

/**
 * 对外用户登入实体类
 *
 * @author SEN
 * @version 1.0.0
 * @date 2024-06-29
 */
@Data
public class LoginBody1 {

    /**
     * 用户名
     */
    private String username;

    /**
     * 用户密码
     */
    private String password;

    /**
     * 验证码
     */
    private String code;


    /**
     * 唯一标识
     */
    private String uuid;
}
