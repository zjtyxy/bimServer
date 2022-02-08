package org.jeecg.modules.device.service.impl;

import com.ciat.bim.data.id.DeviceId;
import com.ciat.bim.data.id.EntityId;
import com.ciat.bim.data.id.TenantId;
import com.ciat.bim.msg.EntityType;
import com.ciat.bim.server.dao.attributes.AttributesDao;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.SettableFuture;
import org.jeecg.modules.device.entity.AttributeKv;
import org.jeecg.modules.device.mapper.AttributeKvMapper;
import org.jeecg.modules.device.service.IAttributeKvService;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Description: 属性表
 * @Author: jeecg-boot
 * @Date:   2022-01-19
 * @Version: V1.0
 */
@Service
public class AttributeKvServiceImpl extends ServiceImpl<AttributeKvMapper, AttributeKv> implements IAttributeKvService {

	@Autowired
	private AttributeKvMapper attributeKvMapper;
	@Autowired
	private  AttributesDao attributesDao;
	@Override
	public List<AttributeKv> selectByMainId(String mainId) {
		return attributeKvMapper.selectByMainId(mainId);
	}

	@Override
	public ListenableFuture<List<AttributeKv>> find(String tenantId, EntityId entityId, String clientScope, Set<String> keySet) {
		return null;
	}

	@Override
	public ListenableFuture<List<Void>> removeAll(TenantId tenantId, EntityId entityId, String scope, List<String> keys) {
		return null;
	}

	@Override
	public ListenableFuture<List<Void>> save(TenantId tenantId, EntityId entityId, String scope, List<AttributeKv> attributes) {

		SettableFuture<List<AttributeKv>> future = SettableFuture.create();
		List<ListenableFuture<Void>> saveFutures = attributes.stream().map(attribute ->attributesDao.save(tenantId, entityId, scope, attribute)).collect(Collectors.toList());

		return Futures.allAsList(saveFutures);
	}
	public ListenableFuture<Void> save(TenantId tenantId, EntityId entityId, String attributeType, AttributeKv attribute) {
		SettableFuture<Void> future = SettableFuture.create();
		return future;
	}
	@Override
	public ListenableFuture<List<AttributeKv>> findAll(TenantId tenantId, DeviceId deviceId, String scope) {
		return null;
	}

	@Override
	public List<String> findAllKeysByEntityIds(String name, List<String> collect) {
		return null;
	}

	@Override
	public List<String> findAllKeysByDeviceProfileId(String id, String id1) {
		return null;
	}

	@Override
	public List<String> findAllKeysByTenantId(String id) {
		return null;
	}

	@Override
	public List<AttributeKv> findAllByEntityTypeAndEntityIdAndAttributeType(EntityType entityType, String id, String attributeType) {
		return null;
	}
}
