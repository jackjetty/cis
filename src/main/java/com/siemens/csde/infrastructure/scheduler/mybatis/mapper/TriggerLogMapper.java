package com.siemens.csde.infrastructure.scheduler.mybatis.mapper;

import com.siemens.csde.infrastructure.scheduler.mybatis.model.TriggerLogModel;

public interface TriggerLogMapper {
    int deleteByPrimaryKey(String id);

    int insert(TriggerLogModel record);

    int insertSelective(TriggerLogModel record);

    TriggerLogModel selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(TriggerLogModel record);

    int updateByPrimaryKey(TriggerLogModel record);
}