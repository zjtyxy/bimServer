package org.jeecg.modules.tenant.service.impl;

import org.jeecg.modules.tenant.entity.TenantProfile;
import org.jeecg.modules.tenant.mapper.TenantProfileMapper;
import org.jeecg.modules.tenant.service.ITenantProfileService;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * @Description: 住户配置
 * @Author: jeecg-boot
 * @Date:   2022-01-21
 * @Version: V1.0
 */
@Service
public class TenantProfileServiceImpl extends ServiceImpl<TenantProfileMapper, TenantProfile> implements ITenantProfileService {

}
