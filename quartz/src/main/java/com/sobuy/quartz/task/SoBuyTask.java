package com.sobuy.quartz.task;

/**
 * 自定义的定时任务都实现这个接口，
 * 在execute方法中编写定时任务逻辑
 *
 * @author SEN
 * @version 1.0.0
 * @date 2024-07-12
 */
public interface SoBuyTask {

    void execute();
}
