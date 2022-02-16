package org.jeecg.modules.device.service.impl;

import org.jeecg.modules.device.entity.TsKv;
import org.jeecg.modules.device.mapper.TsKvMapper;
import org.jeecg.modules.device.service.ITsKvService;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * @Description: 设备遥测数据
 * @Author: jeecg-boot
 * @Date:   2022-02-16
 * @Version: V1.0
 */
@Service
public class TsKvServiceImpl extends ServiceImpl<TsKvMapper, TsKv> implements ITsKvService {

}
