package com.sen.system.domain;

import com.sen.common.core.domain.BaseEntity;
import lombok.Data;

/**
 * @author SEN
 * @version 1.0.0
 * @date 2024-06-24
 */
@Data
public class SysParam extends BaseEntity {

    private static final long serialVersionUID = 1L;

    private long id;

    private String code;

    private String name;

    private String value;

    private String description;



}
