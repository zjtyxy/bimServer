package org.jeecg.modules.device.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.jeecg.modules.device.entity.AttributeKv;

import java.util.List;

/**
 * @Description: 属性表
 * @Author: jeecg-boot
 * @Date:   2022-01-19
 * @Version: V1.0
 */
public interface IAttributeKvService extends IService<AttributeKv> {

	public List<AttributeKv> selectByMainId(String mainId);
}
