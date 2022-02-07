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


import com.ciat.bim.common.data.rule.ComponentScope;
import com.ciat.bim.common.data.rule.ComponentType;
import com.ciat.bim.common.data.rule.RuleChainType;
import com.ciat.bim.rule.engine.api.NodeConfiguration;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface RuleNode {

    ComponentType type();

    String name();

    String nodeDescription();

    String nodeDetails();

    Class<? extends NodeConfiguration> configClazz();

    boolean inEnabled() default true;

    boolean outEnabled() default true;

    ComponentScope scope() default ComponentScope.TENANT;

    String[] relationTypes() default {"Success", "Failure"};

    String[] uiResources() default {};

    String configDirective() default "";

    String icon() default "";

    String iconUrl() default "";

    String docUrl() default "";

    boolean customRelations() default false;

    RuleChainType[] ruleChainTypes() default {RuleChainType.CORE, RuleChainType.EDGE};

}
