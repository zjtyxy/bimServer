package org.jeecg.modules.device.service;


import com.baomidou.mybatisplus.extension.service.IService;
import org.jeecg.modules.device.entity.AttributeKv;
import org.jeecg.modules.device.entity.Device;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * @Description: 设备信息
 * @Author: jeecg-boot
 * @Date:   2022-01-19
 * @Version: V1.0
 */
public interface IDeviceService extends IService<Device> {

	/**
	 * 添加一对多
	 *
	 */
	public void saveMain(Device device,List<AttributeKv> attributeKvList) ;

	/**
	 * 修改一对多
	 *
	 */
	public void updateMain(Device device, List<AttributeKv> attributeKvList);

	/**
	 * 删除一对多
	 */
	public void delMain (String id);

	/**
	 * 批量删除一对多
	 */
	public void delBatchMain (Collection<? extends Serializable> idList);

}
