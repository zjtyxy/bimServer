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
package com.ciat.bim.server.edge;

import com.ciat.bim.server.edge.rpc.EdgeEventStorageSettings;
import com.ciat.bim.server.edge.rpc.processor.*;
import com.ciat.bim.server.edge.rpc.sync.EdgeRequestsService;
import com.ciat.bim.server.queue.util.TbCoreComponent;
import freemarker.template.Configuration;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;


@Component
@TbCoreComponent
@Data
@Lazy
public class EdgeContextComponent {

//    @Autowired
//    private EdgeService edgeService;
//
//    @Autowired
//    private EdgeEventService edgeEventService;
//
//    @Autowired
//    private AdminSettingsService adminSettingsService;
//
//    @Autowired
//    private Configuration freemarkerConfig;
//
//    @Autowired
//    private AssetService assetService;
//
//    @Autowired
//    private DeviceProfileService deviceProfileService;
//
//    @Autowired
//    private AttributesService attributesService;
//
//    @Autowired
//    private DashboardService dashboardService;
//
//    @Autowired
//    private RuleChainService ruleChainService;
//
//    @Autowired
//    private UserService userService;
//
//    @Autowired
//    private WidgetsBundleService widgetsBundleService;
//
//    @Autowired
//    private EdgeRequestsService edgeRequestsService;
//
//    @Autowired
//    private AlarmEdgeProcessor alarmProcessor;
//
//    @Autowired
//    private DeviceProfileEdgeProcessor deviceProfileProcessor;
//
//    @Autowired
//    private DeviceEdgeProcessor deviceProcessor;
//
//    @Autowired
//    private EntityEdgeProcessor entityProcessor;
//
//    @Autowired
//    private AssetEdgeProcessor assetProcessor;
//
//    @Autowired
//    private EntityViewEdgeProcessor entityViewProcessor;
//
//    @Autowired
//    private UserEdgeProcessor userProcessor;
//
//    @Autowired
//    private RelationEdgeProcessor relationProcessor;
//
//    @Autowired
//    private TelemetryEdgeProcessor telemetryProcessor;
//
//    @Autowired
//    private DashboardEdgeProcessor dashboardProcessor;
//
//    @Autowired
//    private RuleChainEdgeProcessor ruleChainProcessor;
//
//    @Autowired
//    private CustomerEdgeProcessor customerProcessor;
//
//    @Autowired
//    private WidgetBundleEdgeProcessor widgetBundleProcessor;
//
//    @Autowired
//    private WidgetTypeEdgeProcessor widgetTypeProcessor;
//
//    @Autowired
//    private AdminSettingsEdgeProcessor adminSettingsProcessor;
//
//    @Autowired
//    private EdgeEventStorageSettings edgeEventStorageSettings;
//
//    @Autowired
//    private DbCallbackExecutorService dbCallbackExecutor;
//
//    @Autowired
//    private GrpcCallbackExecutorService grpcCallbackExecutorService;
}
