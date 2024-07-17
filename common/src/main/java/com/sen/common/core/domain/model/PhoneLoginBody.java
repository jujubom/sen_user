package com.sen.common.core.domain.model;

import lombok.Data;

/**
 * @author SEN
 * @version 1.0.0
 * @date 2024-07-17
 */
@Data
public class PhoneLoginBody {

    private String phone;

    private String code;

    private String uuid;
}
