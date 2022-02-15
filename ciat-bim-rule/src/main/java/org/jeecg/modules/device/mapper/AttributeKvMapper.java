package org.jeecg.modules.device.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.jeecg.modules.device.entity.AttributeKv;

import java.util.List;

/**
 * @Description: 属性表
 * @Author: jeecg-boot
 * @Date:   2022-01-19
 * @Version: V1.0
 */
import com.github.jeffreyning.mybatisplus.base.MppBaseMapper;
public interface AttributeKvMapper extends MppBaseMapper<AttributeKv> {

	public boolean deleteByMainId(@Param("mainId") String mainId);

	public List<AttributeKv> selectByMainId(@Param("mainId") String mainId);
}
