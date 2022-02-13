package org.jeecg.modules.device.service;

import com.ciat.bim.data.id.TenantId;
import org.jeecg.modules.device.entity.DeviceProfile;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @Description: 设备配置
 * @Author: jeecg-boot
 * @Date:   2022-01-28
 * @Version: V1.0
 */
public interface IDeviceProfileService extends IService<DeviceProfile> {

    DeviceProfile findOrCreateDeviceProfile(TenantId tenantId, String profileName);
    DeviceProfile findOrCreateDeviceProfile(String id);
}
