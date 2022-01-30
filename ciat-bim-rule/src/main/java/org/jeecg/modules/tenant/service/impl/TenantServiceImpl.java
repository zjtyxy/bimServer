package org.jeecg.modules.tenant.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ciat.bim.server.common.data.page.PageData;
import com.ciat.bim.server.common.data.page.PageLink;
import org.jeecg.modules.device.entity.DeviceProfile;
import org.jeecg.modules.tenant.entity.Tenant;
import org.jeecg.modules.tenant.mapper.TenantMapper;
import org.jeecg.modules.tenant.service.ITenantService;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * @Description: 租户
 * @Author: jeecg-boot
 * @Date:   2022-01-21
 * @Version: V1.0
 */
@Service
public class TenantServiceImpl extends ServiceImpl<TenantMapper, Tenant> implements ITenantService {


}
