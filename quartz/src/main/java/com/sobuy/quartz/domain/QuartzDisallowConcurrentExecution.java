package com.sobuy.quartz.domain;

import com.sobuy.quartz.domain.entity.TimerTask;
import com.sobuy.quartz.util.JobInvokeUtil;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.JobExecutionContext;

/**
 *
 * 定时任务处理（禁止并发执行）
 *
 * @author SEN
 * @version 1.0.0
 * @date 2024-07-11
 */
@DisallowConcurrentExecution
public class QuartzDisallowConcurrentExecution  extends AbstractQuartzJob{

    @Override
    protected void doExecute(JobExecutionContext context, TimerTask task) throws Exception {
        JobInvokeUtil.invokeMethod(task);
    }
}
