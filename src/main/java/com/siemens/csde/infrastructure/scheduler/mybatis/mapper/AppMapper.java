package com.siemens.csde.infrastructure.scheduler.mybatis.mapper;

import com.siemens.csde.infrastructure.scheduler.mybatis.model.AppModel;

public interface AppMapper {
    int deleteByPrimaryKey(String id);

    int insert(AppModel record);

    int insertSelective(AppModel record);

    AppModel selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(AppModel record);

    int updateByPrimaryKey(AppModel record);
}