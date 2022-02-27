package org.jeecg.modules.device.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.apache.commons.lang3.StringUtils;
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

    @Override
    public DeviceCredentials findDeviceCredentialsByDeviceId(String deviceId) {
        QueryWrapper qw = new QueryWrapper();
        qw.eq("device_id",deviceId);
        return  this.getOne(qw);
    }

    @Override
    public DeviceCredentials getByUserName(String userName) {
        QueryWrapper qw = new QueryWrapper();
        qw.eq("credentials_value",userName);
        return  this.getOne(qw);
    }
}
