package com.siemens.csde.infrastructure.scheduler.service;

import com.siemens.csde.infrastructure.scheduler.mybatis.model.TriggerLogModel;
import java.util.List;

public interface TriggerLogService{

    void addTriggerLogs(List<TriggerLogModel> triggerLogModels);

}