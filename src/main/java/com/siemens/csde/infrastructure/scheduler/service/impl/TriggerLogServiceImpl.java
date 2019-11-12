package com.siemens.csde.infrastructure.scheduler.service.impl;
import com.github.pagehelper.PageHelper;
import com.siemens.csde.infrastructure.scheduler.mybatis.mapper.TriggerLogMapper;
import com.siemens.csde.infrastructure.scheduler.mybatis.model.TriggerLogModel;
import com.siemens.csde.infrastructure.scheduler.service.TriggerLogService;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.util.Properties;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Slf4j
public class TriggerLogServiceImpl  implements TriggerLogService {

    @Autowired
    private TriggerLogMapper triggerLogMapper;

    @Override
    @Transactional
    public void addTriggerLogs(List<TriggerLogModel> triggerLogModels) {
        if(CollectionUtils.isEmpty(triggerLogModels)){
          return;
        }
        triggerLogMapper.insertTriggerLogs(triggerLogModels);
    }
}