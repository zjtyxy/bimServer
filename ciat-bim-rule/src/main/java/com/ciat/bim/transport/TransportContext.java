/**
 * Copyright © 2016-2021 The Thingsboard Authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.ciat.bim.transport;

import com.ciat.bim.server.queue.discovery.TbServiceInfoProvider;
import com.ciat.bim.server.queue.scheduler.SchedulerComponent;
import com.ciat.bim.server.utils.ThingsBoardExecutors;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.jdi.connect.spi.TransportService;
import lombok.Data;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.concurrent.ExecutorService;

/**
 * Created by ashvayka on 15.10.18.
 */
@Slf4j
@Data
public abstract class TransportContext {

    protected final ObjectMapper mapper = new ObjectMapper();

    @Autowired
    protected TransportService transportService;

    @Autowired
    private TbServiceInfoProvider serviceInfoProvider;

    @Autowired
    private SchedulerComponent scheduler;

    @Getter
    private ExecutorService executor;

//    @Getter
//    @Autowired
//    private OtaPackageDataCache otaPackageDataCache;
//
//    @Autowired
//    private TransportResourceCache transportResourceCache;

    @PostConstruct
    public void init() {
        executor = ThingsBoardExecutors.newWorkStealingPool(50, getClass());
    }

    @PreDestroy
    public void stop() {
        if (executor != null) {
            executor.shutdownNow();
        }
    }

    public String getNodeId() {
        return serviceInfoProvider.getServiceId();
    }

}
