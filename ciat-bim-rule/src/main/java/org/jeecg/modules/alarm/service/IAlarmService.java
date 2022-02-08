package org.jeecg.modules.alarm.service;

import com.ciat.bim.data.id.EntityId;
import com.google.common.util.concurrent.ListenableFuture;
import org.jeecg.modules.alarm.entity.Alarm;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @Description: 报警信息
 * @Author: jeecg-boot
 * @Date:   2022-01-25
 * @Version: V1.0
 */
public interface IAlarmService extends IService<Alarm> {

    ListenableFuture<Alarm> findLatestByOriginatorAndType(String tenantId, EntityId originator, String alarmType);

    Alarm saveOrUpdateL(Alarm alarm);
}
