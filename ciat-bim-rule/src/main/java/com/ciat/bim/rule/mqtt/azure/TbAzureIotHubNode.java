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
package com.ciat.bim.rule.mqtt.azure;

import com.bim.mqtt.MqttClientConfig;
import com.ciat.bim.common.data.rule.ComponentType;
import com.ciat.bim.rule.RuleNode;
import com.ciat.bim.rule.TbContext;
import com.ciat.bim.rule.TbNodeConfiguration;
import com.ciat.bim.rule.TbNodeException;
import com.ciat.bim.rule.credentials.CertPemCredentials;
import com.ciat.bim.rule.credentials.ClientCredentials;
import com.ciat.bim.rule.credentials.CredentialsType;
import com.ciat.bim.rule.mqtt.TbMqttNode;
import com.ciat.bim.rule.mqtt.TbMqttNodeConfiguration;
import com.ciat.bim.rule.util.AzureIotHubUtil;
import com.ciat.bim.rule.util.TbNodeUtils;
import io.netty.handler.codec.mqtt.MqttVersion;
import lombok.extern.slf4j.Slf4j;


import javax.net.ssl.SSLException;

@Slf4j
@RuleNode(
        type = ComponentType.EXTERNAL,
        name = "azure iot hub",
        configClazz = TbAzureIotHubNodeConfiguration.class,
        nodeDescription = "Publish messages to the Azure IoT Hub",
        nodeDetails = "Will publish message payload to the Azure IoT Hub with QoS <b>AT_LEAST_ONCE</b>.",
        uiResources = {"static/rulenode/rulenode-core-config.js"},
        configDirective = "tbActionNodeAzureIotHubConfig"
)
public class TbAzureIotHubNode extends TbMqttNode {
    @Override
    public void init(TbContext ctx, TbNodeConfiguration configuration) throws TbNodeException {
//        try {
//            this.mqttNodeConfiguration = TbNodeUtils.convert(configuration, TbMqttNodeConfiguration.class);
//            mqttNodeConfiguration.setPort(8883);
//            mqttNodeConfiguration.setCleanSession(true);
//            ClientCredentials credentials = mqttNodeConfiguration.getCredentials();
//            if (CredentialsType.CERT_PEM == credentials.getType()) {
//                CertPemCredentials pemCredentials = (CertPemCredentials) credentials;
//                if (pemCredentials.getCaCert() == null || pemCredentials.getCaCert().isEmpty()) {
//                    pemCredentials.setCaCert(AzureIotHubUtil.getDefaultCaCert());
//                }
//            }
//            this.mqttClient = initClient(ctx);
//        } catch (Exception e) {
//            throw new TbNodeException(e);
//        }
    }

    protected void prepareMqttClientConfig(MqttClientConfig config) throws SSLException {
        config.setProtocolVersion(MqttVersion.MQTT_3_1_1);
        config.setUsername(AzureIotHubUtil.buildUsername(mqttNodeConfiguration.getHost(), config.getClientId()));
        ClientCredentials credentials = mqttNodeConfiguration.getCredentials();
        if (CredentialsType.SAS == credentials.getType()) {
            config.setPassword(AzureIotHubUtil.buildSasToken(mqttNodeConfiguration.getHost(), ((AzureIotHubSasCredentials) credentials).getSasKey()));
        }
    }
}
