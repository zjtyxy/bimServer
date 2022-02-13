package org.jeecg.modules.alarm.service.impl;

import org.jeecg.modules.alarm.entity.DeviceProfileAlarm;
import org.jeecg.modules.alarm.entity.BimAlarmRule;
import org.jeecg.modules.alarm.mapper.AlarmRuleMapper;
import org.jeecg.modules.alarm.mapper.DeviceProfileAlarmMapper;
import org.jeecg.modules.alarm.service.IDeviceProfileAlarmService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import java.io.Serializable;
import java.util.List;
import java.util.Collection;

/**
 * @Description: 设备报警规则
 * @Author: jeecg-boot
 * @Date:   2022-02-13
 * @Version: V1.0
 */
@Service
public class DeviceProfileAlarmServiceImpl extends ServiceImpl<DeviceProfileAlarmMapper, DeviceProfileAlarm> implements IDeviceProfileAlarmService {

	@Autowired
	private DeviceProfileAlarmMapper deviceProfileAlarmMapper;
	@Autowired
	private AlarmRuleMapper alarmRuleMapper;

	@Override
	@Transactional
	public void saveMain(DeviceProfileAlarm deviceProfileAlarm, List<BimAlarmRule> alarmRuleList) {
		deviceProfileAlarmMapper.insert(deviceProfileAlarm);
		if(alarmRuleList!=null && alarmRuleList.size()>0) {
			for(BimAlarmRule entity:alarmRuleList) {
				//外键设置
				entity.setMainId(deviceProfileAlarm.getId());
				alarmRuleMapper.insert(entity);
			}
		}
	}

	@Override
	@Transactional
	public void updateMain(DeviceProfileAlarm deviceProfileAlarm,List<BimAlarmRule> alarmRuleList) {
		deviceProfileAlarmMapper.updateById(deviceProfileAlarm);

		//1.先删除子表数据
		alarmRuleMapper.deleteByMainId(deviceProfileAlarm.getId());

		//2.子表数据重新插入
		if(alarmRuleList!=null && alarmRuleList.size()>0) {
			for(BimAlarmRule entity:alarmRuleList) {
				//外键设置
				entity.setMainId(deviceProfileAlarm.getId());
				alarmRuleMapper.insert(entity);
			}
		}
	}

	@Override
	@Transactional
	public void delMain(String id) {
		alarmRuleMapper.deleteByMainId(id);
		deviceProfileAlarmMapper.deleteById(id);
	}

	@Override
	@Transactional
	public void delBatchMain(Collection<? extends Serializable> idList) {
		for(Serializable id:idList) {
			alarmRuleMapper.deleteByMainId(id.toString());
			deviceProfileAlarmMapper.deleteById(id);
		}
	}

}
