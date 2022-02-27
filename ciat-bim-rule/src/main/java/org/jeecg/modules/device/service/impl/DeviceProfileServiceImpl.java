package org.jeecg.modules.device.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ciat.bim.data.device.AlarmRule;
import com.ciat.bim.data.device.profile.DeviceProfileTransportConfiguration;
import com.ciat.bim.data.id.TenantId;
import com.ciat.bim.server.common.data.device.profile.MqttDeviceProfileTransportConfiguration;
import org.jeecg.modules.alarm.entity.BimAlarmRule;
import org.jeecg.modules.alarm.entity.DeviceProfileAlarm;
import org.jeecg.modules.alarm.entity.DeviceProfileData;
import org.jeecg.modules.alarm.service.IAlarmRuleService;
import org.jeecg.modules.alarm.service.IDeviceProfileAlarmService;
import org.jeecg.modules.device.entity.DeviceProfile;
import org.jeecg.modules.device.mapper.DeviceProfileMapper;
import org.jeecg.modules.device.service.IDeviceProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.util.List;
import java.util.TreeMap;

/**
 * @Description: 设备配置
 * @Author: jeecg-boot
 * @Date: 2022-01-28
 * @Version: V1.0
 */
@Service
public class DeviceProfileServiceImpl extends ServiceImpl<DeviceProfileMapper, DeviceProfile> implements IDeviceProfileService {

    @Autowired
    private IDeviceProfileAlarmService deviceProfileAlarmService;
    @Autowired
    private IAlarmRuleService alarmRuleService;
    @Override
    public DeviceProfile findOrCreateDeviceProfile(TenantId tenantId, String profileName) {
        LambdaQueryWrapper<DeviceProfile> lqw = new LambdaQueryWrapper();
        lqw.eq(DeviceProfile::getTenantId,tenantId.getId());
        lqw.eq(DeviceProfile::getName,profileName);
        DeviceProfile rst = this.getOne(lqw);
        if(rst == null)
        {
            rst = new DeviceProfile();
            rst.setTenantId(tenantId.getId());
            rst.setName(profileName);
            this.save(rst);
        }

        return rst;
    }

    @Override
    public DeviceProfile findOrCreateDeviceProfile(String id) {
        DeviceProfile dp = this.getById(id);
        if (dp != null) {
            DeviceProfileData dpd = new DeviceProfileData();

            LambdaQueryWrapper<DeviceProfileAlarm> lqw = new LambdaQueryWrapper();
            lqw.eq(DeviceProfileAlarm::getMainId,id);
            List<DeviceProfileAlarm> dpas = deviceProfileAlarmService.list(lqw);
            for(DeviceProfileAlarm dpa : dpas )
            {
                LambdaQueryWrapper<BimAlarmRule> lqw1 = new LambdaQueryWrapper();
                lqw1.eq(BimAlarmRule::getMainId,dpa.getId());
                List<BimAlarmRule> alarms = alarmRuleService.list(lqw1);
                dpa.setCreateRules(new TreeMap<>());
                for(BimAlarmRule alarm : alarms)
                {

                    if(alarm.getIsCreate().equals("Y"))
                    {
                        dpa.getCreateRules().put(alarm.getAlarmSeverity(), new AlarmRule(alarm));
                    }
                    else
                    {
                        dpa.setClearRule(new AlarmRule(alarm));
                    }
                }

            }

            DeviceProfileTransportConfiguration dptc = new MqttDeviceProfileTransportConfiguration();
            dpd.setTransportConfiguration(dptc);
            dpd.setAlarms(dpas);// 设置报警条件

            dp.setProfileData(dpd);

        }
        return dp;
    }
}
