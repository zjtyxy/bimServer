package org.jeecg.modules.device.service.impl;

import com.ciat.bim.data.id.TenantId;
import org.jeecg.modules.device.entity.DeviceProfile;
import org.jeecg.modules.device.mapper.DeviceProfileMapper;
import org.jeecg.modules.device.service.IDeviceProfileService;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * @Description: 设备配置
 * @Author: jeecg-boot
 * @Date:   2022-01-28
 * @Version: V1.0
 */
@Service
public class DeviceProfileServiceImpl extends ServiceImpl<DeviceProfileMapper, DeviceProfile> implements IDeviceProfileService {

    @Override
    public DeviceProfile findOrCreateDeviceProfile(TenantId tenantId, String profileName) {
        return null;
    }
}
