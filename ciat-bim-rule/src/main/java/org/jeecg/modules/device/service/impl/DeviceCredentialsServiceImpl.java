package org.jeecg.modules.device.service.impl;

import org.jeecg.modules.device.entity.DeviceCredentials;
import org.jeecg.modules.device.mapper.DeviceCredentialsMapper;
import org.jeecg.modules.device.service.IDeviceCredentialsService;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * @Description: 认证信息
 * @Author: jeecg-boot
 * @Date:   2022-02-04
 * @Version: V1.0
 */
@Service
public class DeviceCredentialsServiceImpl extends ServiceImpl<DeviceCredentialsMapper, DeviceCredentials> implements IDeviceCredentialsService {

}
