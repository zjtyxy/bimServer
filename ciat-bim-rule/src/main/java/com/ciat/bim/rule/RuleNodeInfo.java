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
package com.ciat.bim.rule;

import lombok.Getter;

public class RuleNodeInfo {
    private final String label;
    @Getter
    private final String ruleNodeId;

    public RuleNodeInfo(String id, String ruleChainName, String ruleNodeName) {
        this.ruleNodeId = id;
        this.label = "[RuleChain: " + ruleChainName + "|RuleNode: " + ruleNodeName + "(" + id + ")]";
    }

    @Override
    public String toString() {
        return label;
    }
}
