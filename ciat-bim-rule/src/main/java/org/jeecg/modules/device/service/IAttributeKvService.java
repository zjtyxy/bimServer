package org.jeecg.modules.device.service;


import com.ciat.bim.data.id.DeviceId;
import com.ciat.bim.data.id.EntityId;
import com.ciat.bim.data.id.TenantId;
import com.ciat.bim.msg.EntityType;
import com.google.common.util.concurrent.ListenableFuture;
import org.jeecg.modules.device.entity.AttributeKv;

import com.github.jeffreyning.mybatisplus.service.IMppService;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * @Description: 属性表
 * @Author: jeecg-boot
 * @Date:   2022-01-19
 * @Version: V1.0
 */
public interface IAttributeKvService extends IMppService<AttributeKv> {

	public List<AttributeKv> selectByMainId(String mainId);
    ListenableFuture<Optional<AttributeKv>> find(TenantId tenantId, EntityId entityId, String scope, String attributeKey);
    ListenableFuture<List<AttributeKv>> find(String tenantId, EntityId entityId, String clientScope, Collection<String> keySet);

    ListenableFuture<List<Void>> removeAll(TenantId tenantId, EntityId entityId, String scope, List<String> keys);

    ListenableFuture<List<Void>> save(TenantId tenantId, EntityId entityId, String scope, List<AttributeKv> attributes);

    ListenableFuture<List<AttributeKv>> findAll(TenantId tenantId, DeviceId deviceId, String scope);

    List<String> findAllKeysByEntityIds(String name, List<String> collect);

    List<String> findAllKeysByDeviceProfileId(String id, String id1);

    List<String> findAllKeysByTenantId(String id);

    List<AttributeKv> findAllByEntityTypeAndEntityIdAndAttributeType(EntityType entityType, String id, String attributeType);
}
