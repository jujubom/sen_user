package com.sen.system.mapper;

import com.sen.common.core.domain.entity.Sequence;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.sql.Timestamp;
import java.util.List;

/**
 * @author SEN
 * @version 1.0.0
 * @date 2024-07-02
 */
@Mapper
public interface SequenceMapper {

    long getTopSeqByName(@Param("name") String name);

    int updateTopSeq(@Param("newValue") Long newValue, @Param("updateTime") Timestamp time,
                            @Param("name") String name, @Param("oldValue") Long oldValue);

    List<Sequence> selectAll();

    void recoverValue(@Param("sequenceList")List<Sequence> sequenceList);
}
