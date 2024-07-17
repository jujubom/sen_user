package com.sobuy.quartz.domain;

import com.sobuy.quartz.domain.entity.TimerTask;
import com.sobuy.quartz.util.JobInvokeUtil;
import org.quartz.JobExecutionContext;

/**
 * @author SEN
 * @version 1.0.0
 * @date 2024-07-11
 */
public class QuartzJobExecution extends AbstractQuartzJob {

    @Override
    protected void doExecute(JobExecutionContext context, TimerTask task) throws Exception {
        JobInvokeUtil.invokeMethod(task);
    }
}
