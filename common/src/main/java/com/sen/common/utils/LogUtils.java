package com.sen.common.utils;

/**
 * @author SEN
 * @version 1.0.0
 * @date 2024-07-17
 */
public class LogUtils {
    public static String getBlock(Object msg) {
        if (msg == null) {
            msg = "";
        }
        return "[" + msg.toString() + "]";
    }
}
