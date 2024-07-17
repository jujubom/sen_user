package com.sobuy.quartz.mapper;

import com.sobuy.quartz.domain.entity.TimerTask;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author SEN
 * @version 1.0.0
 * @date 2024-07-11
 */
@Mapper
public interface TimerTaskMapper {
    /**
     * 查询调度任务日志集合
     *
     * @param job 调度信息
     * @return 操作日志集合
     */
    public List<TimerTask> selectJobList(TimerTask job);

    /**
     * 查询所有调度任务
     *
     * @return 调度任务列表
     */
    public List<TimerTask> selectJobAll();

    /**
     * 通过调度ID查询调度任务信息
     *
     * @param jobId 调度ID
     * @return 角色对象信息
     */
    public TimerTask selectJobById(Long jobId);

    /**
     * 通过调度ID删除调度任务信息
     *
     * @param jobId 调度ID
     * @return 结果
     */
    public int deleteJobById(Long jobId);

    /**
     * 批量删除调度任务信息
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteJobByIds(Long[] ids);

    /**
     * 修改调度任务信息
     *
     * @param job 调度任务信息
     * @return 结果
     */
    public int updateJob(TimerTask job);

    /**
     * 新增调度任务信息
     *
     * @param job 调度任务信息
     * @return 结果
     */
    public int insertJob(TimerTask job);
}
