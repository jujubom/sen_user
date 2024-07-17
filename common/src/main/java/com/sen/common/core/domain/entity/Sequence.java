package com.sen.common.core.domain.entity;

import com.sen.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * @author SEN
 * @version 1.0.0
 * @date 2024-07-17
 */
public class Sequence extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 序列号表主键
     */
    private Long id;

    /**
     * 序列号业务名称
     */
    private String name;

    /**
     * 序列号值
     */
    private long value;

    /**
     * 序列号自增位数
     */
    private int digit;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getValue() {
        return value;
    }

    public void setValue(long value) {
        this.value = value;
    }

    public int getDigit() {
        return digit;
    }

    public void setDigit(int digit) {
        this.digit = digit;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("name", getName())
                .append("value", getValue())
                .append("digit", getDigit())
                .append("createBy", getCreateBy())
                .append("createTime", getCreateTime())
                .append("updateBy", getUpdateBy())
                .append("updateTime", getUpdateTime())
                .append("remark", getRemark())
                .toString();
    }
}
