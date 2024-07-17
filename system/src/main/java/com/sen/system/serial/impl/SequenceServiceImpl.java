package com.sen.system.serial.impl;

import com.sen.common.exception.SequenceException;
import com.sen.system.domain.SequenceRange;
import com.sen.system.serial.SequenceService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author SEN
 * @version 1.0.0
 * @date 2024-07-03
 */
@Service
public class SequenceServiceImpl implements SequenceService {

    /**
     * 获取区间是加一把独占锁防止资源冲突
     */
    private final Lock lock = new ReentrantLock();

    /**
     * 序列号区间管理器
     */
    @Resource
    private SequenceRangeMgrImpl seqRangeMgr;

    /**
     * 当前序列号区间
     */
    private volatile SequenceRange currentRange;


    @Override
    public String nextValue(String name) throws SequenceException {
        //当前区间不存在，重新获取一个区间
        if (null == currentRange) {
            lock.lock();
            try {
                if (null == currentRange) {
                    currentRange = seqRangeMgr.nextRange(name);
                }
            } finally {
                lock.unlock();
            }
        }

        //当value值为-1时，表明区间的序列号已经分配完，需要重新获取区间
        long value = currentRange.getAndIncrement();
        if (value == -1) {
            lock.lock();
            try {
                for ( ; ; ) {
                    if (currentRange.isOver()) {
                        currentRange = seqRangeMgr.nextRange(name);
                    }
                    value = currentRange.getAndIncrement();
                    if (value == -1) {
                        continue;
                    }

                    break;
                }
            } finally {
                lock.unlock();
            }
        }

        if (value < 0) {
            throw new SequenceException("Sequence value overflow, value = " + value);
        }

        return name + value;
    }

}
