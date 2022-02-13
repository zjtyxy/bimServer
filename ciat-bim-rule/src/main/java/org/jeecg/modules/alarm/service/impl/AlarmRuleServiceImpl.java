package org.jeecg.modules.alarm.service.impl;

import org.jeecg.modules.alarm.entity.BimAlarmRule;
import org.jeecg.modules.alarm.mapper.AlarmRuleMapper;
import org.jeecg.modules.alarm.service.IAlarmRuleService;
import org.springframework.stereotype.Service;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Description: 报警规则
 * @Author: jeecg-boot
 * @Date:   2022-02-13
 * @Version: V1.0
 */
@Service
public class AlarmRuleServiceImpl extends ServiceImpl<AlarmRuleMapper, BimAlarmRule> implements IAlarmRuleService {

	@Autowired
	private AlarmRuleMapper alarmRuleMapper;

	@Override
	public List<BimAlarmRule> selectByMainId(String mainId) {
		return alarmRuleMapper.selectByMainId(mainId);
	}
}
