package com.sen.common.utils;

/**
 * @author SEN
 * @version 1.0.0
 * @date 2024-07-17
 */
public interface ExcelHandlerAdapter {
    /**
     * 格式化
     *
     * @param value 单元格数据值
     * @param args excel注解args参数组
     *
     * @return 处理后的值
     */
    Object format(Object value, String[] args);
}
