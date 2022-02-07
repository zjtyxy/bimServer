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
package com.ciat.bim.rule.mqtt;


import com.ciat.bim.rule.credentials.AnonymousCredentials;
import com.ciat.bim.rule.credentials.ClientCredentials;
import com.ciat.bim.rule.engine.api.NodeConfiguration;
import lombok.Data;


@Data
public class TbMqttNodeConfiguration implements NodeConfiguration<TbMqttNodeConfiguration> {

    private String topicPattern;
    private String host;
    private int port;
    private int connectTimeoutSec;
    private String clientId;
    private boolean cleanSession;
    private boolean ssl;
    private ClientCredentials credentials;

    @Override
    public TbMqttNodeConfiguration defaultConfiguration() {
        TbMqttNodeConfiguration configuration = new TbMqttNodeConfiguration();
        configuration.setTopicPattern("my-topic");
        configuration.setPort(1883);
        configuration.setConnectTimeoutSec(10);
        configuration.setCleanSession(true);
        configuration.setSsl(false);
        configuration.setCredentials(new AnonymousCredentials());
        return configuration;
    }

}
