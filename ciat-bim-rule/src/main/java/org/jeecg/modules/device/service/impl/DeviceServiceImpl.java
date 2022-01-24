package org.jeecg.modules.device.service.impl;

import org.jeecg.modules.device.entity.AttributeKv;
import org.jeecg.modules.device.entity.Device;
import org.jeecg.modules.device.mapper.AttributeKvMapper;
import org.jeecg.modules.device.mapper.DeviceMapper;
import org.jeecg.modules.device.service.IDeviceService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import java.io.Serializable;
import java.util.List;
import java.util.Collection;

/**
 * @Description: 设备信息
 * @Author: jeecg-boot
 * @Date:   2022-01-19
 * @Version: V1.0
 */
@Service
public class DeviceServiceImpl extends ServiceImpl<DeviceMapper, Device> implements IDeviceService {

	@Autowired
	private DeviceMapper deviceMapper;
	@Autowired
	private AttributeKvMapper attributeKvMapper;

	@Override
	@Transactional
	public void saveMain(Device device, List<AttributeKv> attributeKvList) {
		deviceMapper.insert(device);
		if(attributeKvList!=null && attributeKvList.size()>0) {
			for(AttributeKv entity:attributeKvList) {
				//外键设置
				entity.setEntityId(device.getId());
				attributeKvMapper.insert(entity);
			}
		}
	}

	@Override
	@Transactional
	public void updateMain(Device device,List<AttributeKv> attributeKvList) {
		deviceMapper.updateById(device);

		//1.先删除子表数据
		attributeKvMapper.deleteByMainId(device.getId());

		//2.子表数据重新插入
		if(attributeKvList!=null && attributeKvList.size()>0) {
			for(AttributeKv entity:attributeKvList) {
				//外键设置
				entity.setEntityId(device.getId());
				attributeKvMapper.insert(entity);
			}
		}
	}

	@Override
	@Transactional
	public void delMain(String id) {
		attributeKvMapper.deleteByMainId(id);
		deviceMapper.deleteById(id);
	}

	@Override
	@Transactional
	public void delBatchMain(Collection<? extends Serializable> idList) {
		for(Serializable id:idList) {
			attributeKvMapper.deleteByMainId(id.toString());
			deviceMapper.deleteById(id);
		}
	}

}
