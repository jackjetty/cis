package com.siemens.csde.infrastructure.scheduler.mybatis.mapper;

import com.siemens.csde.infrastructure.scheduler.mybatis.model.TaskModel;

public interface TaskMapper {
    int deleteByPrimaryKey(String id);

    int insert(TaskModel record);

    int insertSelective(TaskModel record);

    TaskModel selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(TaskModel record);

    int updateByPrimaryKey(TaskModel record);
}