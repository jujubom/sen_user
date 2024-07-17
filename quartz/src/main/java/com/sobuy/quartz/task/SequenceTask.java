package com.sobuy.quartz.task;

import com.sobuy.common.constant.Constants;
import com.sobuy.common.core.domain.entity.Sequence;
import com.sobuy.system.mapper.SequenceMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * @author SEN
 * @version 1.0.0
 * @date 2024-07-11
 */
@Slf4j
@Service
public class SequenceTask implements SoBuyTask {

    @Resource
    private SequenceMapper sequenceMapper;

    @Override
    public void execute() {
        log.info("【{}】更新序列号初始值", new Date());

        String formattedDate = new SimpleDateFormat(Constants.SEQUENCE_DATE_FORMAT).format(new Date());

        List<Sequence> sequenceList = sequenceMapper.selectAll();

        if (!CollectionUtils.isEmpty(sequenceList)) {
            for (Sequence sequence : sequenceList) {
                long startNum = getStartNum(formattedDate,sequence.getDigit());
                sequence.setValue(startNum);
            }

            sequenceMapper.recoverValue(sequenceList);
        }
    }

    private long getStartNum(String startPrefix, int digit) {
        if (digit == 0) {
            // 设置默认值
            digit = 4;
        }
        long startNum = (long)Math.pow(10, digit);
        long longStartPrefix = Long.parseLong(startPrefix);
        return longStartPrefix * startNum;
    }
}
