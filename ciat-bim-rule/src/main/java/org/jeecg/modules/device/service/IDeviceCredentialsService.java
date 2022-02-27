package org.jeecg.modules.device.service;

import org.jeecg.modules.device.entity.DeviceCredentials;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @Description: 认证信息
 * @Author: jeecg-boot
 * @Date:   2022-02-04
 * @Version: V1.0
 */
public interface IDeviceCredentialsService extends IService<DeviceCredentials> {

    DeviceCredentials findDeviceCredentialsByDeviceId(String deviceId);


    DeviceCredentials getByUserName(String userName);
}
