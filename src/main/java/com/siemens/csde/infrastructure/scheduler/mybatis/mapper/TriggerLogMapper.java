package com.siemens.csde.infrastructure.scheduler.mybatis.mapper;

import com.siemens.csde.infrastructure.scheduler.mybatis.model.TriggerLogModel;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TriggerLogMapper {
    int deleteByPrimaryKey(String id);

    int insert(TriggerLogModel record);

    int insertSelective(TriggerLogModel record);

    TriggerLogModel selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(TriggerLogModel record);

    int updateByPrimaryKey(TriggerLogModel record);

    //批量保存
    int insertTriggerLogs(@Param("triggerLogModels") List<TriggerLogModel> triggerLogModels);
}