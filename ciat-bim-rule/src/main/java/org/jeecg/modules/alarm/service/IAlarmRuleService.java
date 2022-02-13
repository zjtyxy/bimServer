package org.jeecg.modules.alarm.service;

import org.jeecg.modules.alarm.entity.BimAlarmRule;
import com.baomidou.mybatisplus.extension.service.IService;
import java.util.List;

/**
 * @Description: 报警规则
 * @Author: jeecg-boot
 * @Date:   2022-02-13
 * @Version: V1.0
 */
public interface IAlarmRuleService extends IService<BimAlarmRule> {

	public List<BimAlarmRule> selectByMainId(String mainId);
}
