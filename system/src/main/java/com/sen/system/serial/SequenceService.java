package com.sen.system.serial;

import com.sen.common.exception.SequenceException;

/**
 *
 * 序列号生成器接口
 *
 * @author SEN
 * @version 1.0.0
 * @date 2024-07-02
 */
public interface SequenceService {

    /**
     * 生成下一个序列号
     *
     * @return 序列号
     * @throws SequenceException 序列号异常
     */
    String nextValue(String name) throws SequenceException;


}
