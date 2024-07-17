package com.sobuy.quartz.domain;

import com.sobuy.common.constant.TimerConstants;
import com.sobuy.common.exception.TaskException;
import com.sobuy.common.utils.SpringUtils;
import com.sobuy.quartz.domain.entity.TimerTask;
import com.sobuy.quartz.mapper.TimerTaskMapper;
import com.sobuy.system.domain.SysConfig;
import com.sobuy.system.mapper.SysConfigMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * 定时任务初始化
 *
 * @author SEN
 * @version 1.0.0
 * @date 2024-07-11
 */
@Slf4j
@Component
public class TimerComponent {

    @Autowired
    private Scheduler scheduler;

    @Autowired
    private TimerTaskMapper jobMapper;

    @Autowired
    private SysConfigMapper configMapper;

    private final ScheduledExecutorService executor = SpringUtils.getBean("timerTaskScheduledExecutor");

    /**
     * 项目启动时，初始化定时器 主要是防止手动修改数据库导致未同步到定时任务处理（注：不能手动修改数据库ID和任务组名，否则会导致脏数据）
     */
    @PostConstruct
    public void init() throws SchedulerException, TaskException {
        // 初始化
        scheduler.clear();
        List<TimerTask> taskList = jobMapper.selectJobAll();
        for (TimerTask task : taskList) {
            createScheduleJob(scheduler, task);
        }

        // 5s后执行，之后没间隔30s查询一次定时任务数据库，刷新最新的定时任务cron表达式
        executor.scheduleAtFixedRate(this::regularUpdateScheduler, 5,30, TimeUnit.SECONDS);
    }

    public void regularUpdateScheduler() {

        // 先查询系统配置参数，是否允许定时查询定时任务配置，实时刷新
        SysConfig config = configMapper.selectConfigByKey("sys_regular_update_timer");

        if (Boolean.parseBoolean(config.getConfigValue())) {
            List<TimerTask> taskList = jobMapper.selectJobAll();

            try {
                for (TimerTask task : taskList) {
                    String runStatus = task.getRunStatus();

                    TriggerKey triggerKey = getTriggerKey(task.getId(), task.getTaskGroup());
                    CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);


                    if (StringUtils.equals(runStatus, "0")) {
                        scheduler.pauseJob(getJobKey(task.getId(), task.getTaskGroup()));
                        log.warn("暂停定时任务【{}】", StringUtils.substringAfterLast(task.getInvokeTarget(), "."));
                        continue;
                    }
                    Trigger.TriggerState triggerState = scheduler.getTriggerState(triggerKey);

                    if ((StringUtils.equals(runStatus, "1") && !StringUtils.equals(trigger.getCronExpression(),task.getCronExpression())) ||
                            (triggerState.equals(Trigger.TriggerState.PAUSED) && StringUtils.equals(runStatus, "1"))) {

                        // 表达式调度构建器
                        CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder
                                .cronSchedule(task.getCronExpression())
                                .withMisfireHandlingInstructionDoNothing();

                        trigger = trigger.getTriggerBuilder().withIdentity(triggerKey)
                                .withSchedule(cronScheduleBuilder).build();
                        // 重启触发器
                        scheduler.rescheduleJob(triggerKey, trigger);

                        log.warn("刷新定时任务【{}】", StringUtils.substringAfterLast(task.getInvokeTarget(), "."));
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    public static void createScheduleJob(Scheduler scheduler, TimerTask task) throws SchedulerException, TaskException {
        Class<? extends Job> jobClass = getQuartzJobClass();
        // 构建job信息
        Long taskId = task.getId();
        String taskGroup = task.getTaskGroup();
        JobDetail jobDetail = JobBuilder.newJob(jobClass).withIdentity(getJobKey(taskId, taskGroup)).build();

        // 表达式调度构建器
        CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder.cronSchedule(task.getCronExpression());
        cronScheduleBuilder = handleCronScheduleMisfirePolicy(task, cronScheduleBuilder);

        // 按新的cronExpression表达式构建一个新的trigger
        CronTrigger trigger = TriggerBuilder.newTrigger().withIdentity(getTriggerKey(taskId, taskGroup))
                .withSchedule(cronScheduleBuilder).build();

        // 放入参数，运行时的方法可以获取
        jobDetail.getJobDataMap().put(TimerConstants.TASK_PROPERTIES, task);

        // 判断是否存在
        if (scheduler.checkExists(getJobKey(taskId, taskGroup))) {
            // 防止创建时存在数据问题 先移除，然后在执行创建操作
            scheduler.deleteJob(getJobKey(taskId, taskGroup));
        }

        scheduler.scheduleJob(jobDetail, trigger);

        // 暂停任务
        if (task.getRunStatus().equals(TimerConstants.Status.PAUSE.getValue())) {
            scheduler.pauseJob(getJobKey(taskId, taskGroup));
        }
    }


    /**
     * 得到quartz任务类
     *
     * @param sysJob 执行计划
     * @return 具体执行任务类
     */
    private static Class<? extends Job> getQuartzJobClass() {
        // TODO 定时任务暂定不允许并发执行
        return QuartzDisallowConcurrentExecution.class;
    }


    /**
     * 构建任务键对象
     */
    public static JobKey getJobKey(Long jobId, String jobGroup) {
        return JobKey.jobKey(TimerConstants.TASK_CLASS_NAME + jobId, jobGroup);
    }


    /**
     * 设置定时任务策略
     */
    public static CronScheduleBuilder handleCronScheduleMisfirePolicy(TimerTask task, CronScheduleBuilder cb)
            throws TaskException {
        switch (task.getMisfirePolicy()) {
            case TimerConstants.MISFIRE_DEFAULT:
                return cb;
            case TimerConstants.MISFIRE_IGNORE_MISFIRES:
                return cb.withMisfireHandlingInstructionIgnoreMisfires();
            case TimerConstants.MISFIRE_FIRE_AND_PROCEED:
                return cb.withMisfireHandlingInstructionFireAndProceed();
            case TimerConstants.MISFIRE_DO_NOTHING:
                return cb.withMisfireHandlingInstructionDoNothing();
            default:
                throw new TaskException("The task misfire policy '" + task.getMisfirePolicy()
                        + "' cannot be used in cron schedule tasks", TaskException.Code.CONFIG_ERROR);
        }
    }


    /**
     * 构建任务触发对象
     */
    public static TriggerKey getTriggerKey(Long jobId, String jobGroup) {
        return TriggerKey.triggerKey(TimerConstants.TASK_CLASS_NAME + jobId, jobGroup);
    }


    /**
     * 立即运行任务
     *
     * @param job 调度信息
     */
//    @Transactional(rollbackFor = Exception.class)
//    public void run(TimerTask job) throws SchedulerException {
//        Long jobId = job.getJobId();
//        String jobGroup = job.getJobGroup();
//        TimerTask properties = selectJobById(job.getJobId());
//        // 参数
//        JobDataMap dataMap = new JobDataMap();
//        dataMap.put(ScheduleConstants.TASK_PROPERTIES, properties);
//        scheduler.triggerJob(ScheduleUtils.getJobKey(jobId, jobGroup), dataMap);
//    }

}
