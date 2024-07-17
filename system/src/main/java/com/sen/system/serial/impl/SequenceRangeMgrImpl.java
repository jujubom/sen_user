package com.sen.system.serial.impl;

import com.sen.common.exception.SequenceException;
import com.sen.system.domain.SequenceRange;
import com.sen.system.mapper.SequenceMapper;
import com.sen.system.serial.SequenceRangeMgr;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.*;

/**
 * @author SEN
 * @version 1.0.0
 * @date 2024-07-02
 */

@Service
public class SequenceRangeMgrImpl implements SequenceRangeMgr {

    @Resource
    private SequenceMapper sequenceMapper;

    private static final long DELTA = 100000000L;

    /**
     * 区间步长
     */
    private int step = 1;

    /**
     * 获取区间失败重试次数
     */
    private int retryTimes = 100;


    @Override
    public SequenceRange nextRange(String name) throws SequenceException {
        if (isEmpty(name)) {
            throw new SecurityException("[SequenceRangeMgrImpl-nextRange] name is empty.");
        }

        Long oldValue;
        Long newValue;

        for (int i = 0; i < getRetryTimes(); i++) {

            // 查询旧值
            oldValue = selectOldValue(name);

            if (null == oldValue) {
                //区间不存在，重试
                continue;
            }

            newValue = oldValue + getStep();

            if (updateValue(newValue, oldValue, name)) {
                return new SequenceRange(oldValue + 1, newValue);
            }
            //else 失败重试
        }

        throw new SequenceException("Retried too many times, retryTimes = " + getRetryTimes());
    }

    private boolean updateValue(Long newValue, Long oldValue, String name) {
        try {
            int affectedRows = sequenceMapper.updateTopSeq(newValue, new Timestamp(System.currentTimeMillis()), name, oldValue);
            return affectedRows > 0;
        } catch (Exception e) {
            throw new SequenceException(e);
        }
    }

    private Long selectOldValue(String name) {

        long oldValue;
        try {

            oldValue = sequenceMapper.getTopSeqByName(name);

            if (oldValue < 0) {
                String msg =
                        "Sequence value cannot be less than zero, value = " + oldValue + ", please check table t_sequence";
                throw new SequenceException(msg);
            }

            if (oldValue > Long.MAX_VALUE - DELTA) {
                String msg =
                        "Sequence value overflow, value = " + oldValue + ", please check table t_sequence";
                throw new SequenceException(msg);
            }

            return oldValue;
        } catch (Exception e) {
            throw new SequenceException(e);
        }
    }


    private boolean isEmpty(String str) {
        return null == str || str.trim().length() == 0;
    }



    public int getStep() {
        return step;
    }

    public void setStep(int step) {
        this.step = step;
    }

    public int getRetryTimes() {
        return retryTimes;
    }

    public void setRetryTimes(int retryTimes) {
        this.retryTimes = retryTimes;
    }

}
