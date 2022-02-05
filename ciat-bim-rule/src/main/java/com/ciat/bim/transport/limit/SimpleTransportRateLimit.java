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
package com.ciat.bim.transport.limit;

import com.ciat.bim.server.common.msg.TbRateLimits;
import lombok.Getter;
import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
public class SimpleTransportRateLimit implements TransportRateLimit {

    private final TbRateLimits rateLimit;
    @Getter
    private final String configuration;

    public SimpleTransportRateLimit(String configuration) {
        this.configuration = configuration;
        this.rateLimit = new TbRateLimits(configuration);
    }

    @Override
    public boolean tryConsume() {
        return rateLimit.tryConsume();
    }

    @Override
    public boolean tryConsume(long number) {
        return number <= 0 || rateLimit.tryConsume(number);
    }
}
