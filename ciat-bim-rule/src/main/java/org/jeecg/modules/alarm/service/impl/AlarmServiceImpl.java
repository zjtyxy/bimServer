package org.jeecg.modules.alarm.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ciat.bim.data.id.EntityId;
import com.fasterxml.jackson.databind.JsonNode;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.SettableFuture;
import org.jeecg.modules.alarm.entity.Alarm;
import org.jeecg.modules.alarm.entity.AlarmOperationResult;
import org.jeecg.modules.alarm.mapper.AlarmMapper;
import org.jeecg.modules.alarm.service.IAlarmService;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * @Description: 报警信息
 * @Author: jeecg-boot
 * @Date:   2022-01-25
 * @Version: V1.0
 */
@Service
public class AlarmServiceImpl extends ServiceImpl<AlarmMapper, Alarm> implements IAlarmService {

    @Override
    public ListenableFuture<Alarm> findLatestByOriginatorAndType(String tenantId, EntityId originator, String alarmType) {
    //    ListenableFuture<Alarm> rst = SettableFuture.create();
        LambdaQueryWrapper<Alarm> lqw = new LambdaQueryWrapper();
        lqw.eq(Alarm::getOriginatorId,originator.getId());
        lqw.eq(Alarm::getType,alarmType);
        Alarm alarm = this.getOne(lqw);
        if(alarm != null)
        {
            return  Futures.immediateFuture(alarm);
        }
        return  Futures.immediateFuture(null);
    }

    @Override
    public Alarm saveOrUpdateL(Alarm alarm) {
        this.saveOrUpdate(alarm);
        return alarm;
    }

    @Override
    public ListenableFuture<AlarmOperationResult> clearAlarmForResult(String tenantId, String id, JsonNode details, long currentTimeMillis) {
        return null;
    }
}
