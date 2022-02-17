package org.jeecg.modules.device.service;

import com.ciat.bim.server.dao.ToData;
import com.github.jeffreyning.mybatisplus.service.IMppService;
import org.apache.poi.ss.formula.functions.T;
import org.jeecg.modules.device.entity.TsKvLatest;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @Description: 最新遥测数据
 * @Author: jeecg-boot
 * @Date:   2022-02-16
 * @Version: V1.0
 */
public interface ITsKvLatestService extends IMppService<TsKvLatest> {

    List<String> findAllKeysByEntityIds(List<String> collect);

    List<String> getKeysByTenantId(String id);

    List<String> getKeysByDeviceProfileId(String id, String id1);

    List<TsKvLatest> findAllByEntityId(String id);
}
