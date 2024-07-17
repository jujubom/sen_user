package com.sen.common.core.domain;

import java.util.Map;

/**
 * @author SEN
 * @version 1.0.0
 * @date 2024-07-17
 */
public interface IQuery {
    /**
     * @return 搜索值
     */
    String getSearchValue();

    /**
     * @return 所有参数
     */
    Map<String, Object> getParams();
}
