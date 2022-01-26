package org.jeecg.modules.alarm.service.impl;

import org.jeecg.modules.alarm.entity.Alarm;
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

}
