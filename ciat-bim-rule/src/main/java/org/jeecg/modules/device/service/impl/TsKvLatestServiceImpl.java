package org.jeecg.modules.device.service.impl;

import com.ciat.bim.server.dao.ToData;
import com.github.jeffreyning.mybatisplus.service.MppServiceImpl;
import org.apache.poi.ss.formula.functions.T;
import org.jeecg.modules.device.entity.TsKvLatest;
import org.jeecg.modules.device.mapper.TsKvLatestMapper;
import org.jeecg.modules.device.service.ITsKvLatestService;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.util.List;

/**
 * @Description: 最新遥测数据
 * @Author: jeecg-boot
 * @Date:   2022-02-16
 * @Version: V1.0
 */
@Service
public class TsKvLatestServiceImpl extends MppServiceImpl<TsKvLatestMapper, TsKvLatest> implements ITsKvLatestService {

    @Override
    public List<String> findAllKeysByEntityIds(List<String> collect) {
        return null;
    }

    @Override
    public List<String> getKeysByTenantId(String id) {
        return null;
    }

    @Override
    public List<String> getKeysByDeviceProfileId(String id, String id1) {
        return null;
    }

    @Override
    public List<TsKvLatest> findAllByEntityId(String id) {
        return null;
    }
}
