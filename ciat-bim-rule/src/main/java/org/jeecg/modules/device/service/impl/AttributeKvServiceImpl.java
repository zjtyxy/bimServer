package org.jeecg.modules.device.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
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

import java.util.*;
import java.util.stream.Collectors;


import org.springframework.beans.factory.annotation.Autowired;
import com.github.jeffreyning.mybatisplus.service.MppServiceImpl;
/**
 * @Description: 属性表
 * @Author: jeecg-boot
 * @Date:   2022-01-19
 * @Version: V1.0
 */
@Service
public class AttributeKvServiceImpl extends MppServiceImpl<AttributeKvMapper, AttributeKv> implements IAttributeKvService {
	@Override
	public ListenableFuture<Optional<AttributeKv>> find(TenantId tenantId, EntityId entityId, String scope, String attributeKey) {
		return null;
	}

	@Autowired
	private AttributeKvMapper attributeKvMapper;
	@Autowired
	private  AttributesDao attributesDao;
	@Override
	public List<AttributeKv> selectByMainId(String mainId) {
		return attributeKvMapper.selectByMainId(mainId);
	}

	@Override
	public ListenableFuture<List<AttributeKv>> find(String tenantId, EntityId entityId, String clientScope, Collection<String> keySet) {
		//List<ListenableFuture<AttributeKv>> saveFutures = keySet.stream().map(attribute ->attributesDao.save(tenantId, entityId, scope, attribute)).collect(Collectors.toList());
		LambdaQueryWrapper<AttributeKv> lqw = new LambdaQueryWrapper<>();
		lqw.eq(AttributeKv::getEntityId,entityId);
		lqw.eq(AttributeKv::getEntityType,clientScope);//属性范围字段应该有问题
		List<AttributeKv> attrs = this.list(lqw);
		List<AttributeKv> rest = new ArrayList<>();
		for(AttributeKv attr :attrs)
		{
			if(keySet.contains(attr.getAttributeKey()))
			{
				rest.add(attr);
			}
		}
		SettableFuture<List<AttributeKv>> future = SettableFuture.create();
		future.set(rest);
		return future;
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

	@Override
	public boolean updateByMultiId(AttributeKv entity) {
		return super.updateByMultiId(entity);
	}

	@Override
	public boolean deleteByMultiId(AttributeKv entity) {
		return super.deleteByMultiId(entity);
	}

	@Override
	public AttributeKv selectByMultiId(AttributeKv entity) {
		return super.selectByMultiId(entity);
	}

	@Override
	public boolean saveOrUpdateBatchByMultiId(Collection<AttributeKv> entityList) {
		return super.saveOrUpdateBatchByMultiId(entityList);
	}

	@Override
	public boolean updateBatchByMultiId(Collection<AttributeKv> entityList) {
		return super.updateBatchByMultiId(entityList);
	}
}
