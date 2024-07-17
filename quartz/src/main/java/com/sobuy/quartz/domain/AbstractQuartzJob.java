package com.sobuy.quartz.domain;

import com.sen.common.constant.TimerConstants;
import com.sen.common.utils.BeanCopyUtils;
import com.sobuy.quartz.domain.entity.TimerTask;
import lombok.extern.slf4j.Slf4j;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.util.Date;

/**
 * 抽象quartz调用
 *
 * @author SEN
 * @version 1.0.0
 * @date 2024-07-10
 */
@Slf4j
public abstract class AbstractQuartzJob implements Job {

    /**
     * 线程本地变量
     */
    private static ThreadLocal<Date> threadLocal = new ThreadLocal<>();

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        TimerTask task = new TimerTask();
        BeanCopyUtils.copyBeanProp(task, context.getMergedJobDataMap().get(TimerConstants.TASK_PROPERTIES));
        try {

            before(context, task);

            if (task != null) {

                doExecute(context, task);

            }

            after(context, task, null);

        } catch (Exception e) {
            log.error("任务执行异常  - ：", e);
            after(context, task, e);
        }
    }

    /**
     * 执行前
     *
     * @param context 工作执行上下文对象
     * @param task    系统计划任务
     */
    protected void before(JobExecutionContext context, TimerTask task) {
        threadLocal.set(new Date());
    }

    /**
     * 执行后
     *
     * @param context 工作执行上下文对象
     * @param task    系统计划任务
     */
    protected void after(JobExecutionContext context, TimerTask task, Exception e) {
        Date startTime = threadLocal.get();
        threadLocal.remove();
        long time = new Date().getTime();
        log.info("定时任务本次执行总耗时： " + (time - startTime.getTime()) + "毫秒");

        // 写入数据库当中
//        SpringUtils.getBean(ITimerTaskLogService.class).addJobLog(taskLog);
    }

    /**
     * 执行方法，由子类重载
     *
     * @param context 工作执行上下文对象
     * @param task    系统计划任务
     * @throws Exception 执行过程中的异常
     */
    protected abstract void doExecute(JobExecutionContext context, TimerTask task) throws Exception;
}
