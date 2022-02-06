package org.jeecg.modules.device.service.impl;

import com.ciat.bim.data.id.EntityId;
import com.ciat.bim.data.id.TenantId;
import com.google.common.util.concurrent.ListenableFuture;
import org.jeecg.modules.device.entity.AttributeKv;
import org.jeecg.modules.device.mapper.AttributeKvMapper;
import org.jeecg.modules.device.service.IAttributeKvService;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Set;

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
		return null;
	}
}
