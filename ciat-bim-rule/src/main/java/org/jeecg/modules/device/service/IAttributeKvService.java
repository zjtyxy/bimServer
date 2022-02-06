package org.jeecg.modules.device.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ciat.bim.data.id.DeviceId;
import com.ciat.bim.data.id.EntityId;
import com.ciat.bim.data.id.TenantId;
import com.google.common.util.concurrent.ListenableFuture;
import org.jeecg.modules.device.entity.AttributeKv;

import java.util.List;
import java.util.Set;

/**
 * @Description: 属性表
 * @Author: jeecg-boot
 * @Date:   2022-01-19
 * @Version: V1.0
 */
public interface IAttributeKvService extends IService<AttributeKv> {

	public List<AttributeKv> selectByMainId(String mainId);

    ListenableFuture<List<AttributeKv>> find(String tenantId, EntityId entityId, String clientScope, Set<String> keySet);

    ListenableFuture<List<Void>> removeAll(TenantId tenantId, EntityId entityId, String scope, List<String> keys);

    ListenableFuture<List<Void>> save(TenantId tenantId, EntityId entityId, String scope, List<AttributeKv> attributes);

    ListenableFuture<List<AttributeKv>> findAll(TenantId tenantId, DeviceId deviceId, String scope);
}
