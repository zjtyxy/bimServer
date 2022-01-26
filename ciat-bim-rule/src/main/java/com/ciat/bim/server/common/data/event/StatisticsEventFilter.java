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
package com.ciat.bim.server.common.data.event;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;


@Data
@ApiModel
public class StatisticsEventFilter implements EventFilter {

    @ApiModelProperty(position = 1, value = "String value representing the server name, identifier or ip address where the platform is running", example = "ip-172-31-24-152")
    protected String server;
    @ApiModelProperty(position = 2, value = "The minimum number of successfully processed messages", example = "25")
    protected Integer messagesProcessed;
    @ApiModelProperty(position = 3, value = "The minimum number of errors occurred during messages processing", example = "30")
    protected Integer errorsOccurred;

    @Override
    public EventType getEventType() {
        return EventType.STATS;
    }

    @Override
    public boolean hasFilterForJsonBody() {
        return !StringUtils.isEmpty(server) || (messagesProcessed != null && messagesProcessed > 0) || (errorsOccurred != null && errorsOccurred > 0);
    }
}
