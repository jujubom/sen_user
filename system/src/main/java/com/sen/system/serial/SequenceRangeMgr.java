package com.sen.system.serial;

import com.sen.common.exception.SequenceException;
import com.sen.system.domain.SequenceRange;

/**
 * 区间管理器
 *
 * @author SEN
 * @version 1.0.0
 * @date 2024-07-02
 */
public interface SequenceRangeMgr {

    /**
     * 获取指定区间名的下一个区间
     *
     * @param name 区间名
     * @return 返回区间
     * @throws SequenceException 异常
     */
    SequenceRange nextRange(String name) throws SequenceException;


}
