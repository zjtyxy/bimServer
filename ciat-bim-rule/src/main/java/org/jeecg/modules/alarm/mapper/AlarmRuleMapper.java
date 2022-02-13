package org.jeecg.modules.alarm.mapper;

import java.util.List;
import org.jeecg.modules.alarm.entity.BimAlarmRule;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * @Description: 报警规则
 * @Author: jeecg-boot
 * @Date:   2022-02-13
 * @Version: V1.0
 */
public interface AlarmRuleMapper extends BaseMapper<BimAlarmRule> {

	public boolean deleteByMainId(@Param("mainId") String mainId);

	public List<BimAlarmRule> selectByMainId(@Param("mainId") String mainId);
}
