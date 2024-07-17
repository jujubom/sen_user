package com.sen.common.core.domain.model;

import io.swagger.annotations.ApiModel;
import lombok.Data;

/**
 * @author SEN
 * @version 1.0.0
 * @date 2024-07-17
 */
@Data
@ApiModel("用户登入实体类")
public class LoginBody {

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
