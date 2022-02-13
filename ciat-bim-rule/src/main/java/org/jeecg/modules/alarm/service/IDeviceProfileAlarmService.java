package org.jeecg.modules.alarm.service;

import org.jeecg.modules.alarm.entity.BimAlarmRule;
import org.jeecg.modules.alarm.entity.DeviceProfileAlarm;
import com.baomidou.mybatisplus.extension.service.IService;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * @Description: 设备报警规则
 * @Author: jeecg-boot
 * @Date:   2022-02-13
 * @Version: V1.0
 */
public interface IDeviceProfileAlarmService extends IService<DeviceProfileAlarm> {

	/**
	 * 添加一对多
	 *
	 */
	public void saveMain(DeviceProfileAlarm deviceProfileAlarm,List<BimAlarmRule> alarmRuleList) ;

	/**
	 * 修改一对多
	 *
	 */
	public void updateMain(DeviceProfileAlarm deviceProfileAlarm,List<BimAlarmRule> alarmRuleList);

	/**
	 * 删除一对多
	 */
	public void delMain (String id);

	/**
	 * 批量删除一对多
	 */
	public void delBatchMain (Collection<? extends Serializable> idList);

}
