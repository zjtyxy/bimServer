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

import com.ciat.bim.data.SearchTextBasedWithAdditionalInfo;
import com.ciat.bim.data.id.*;
import com.ciat.bim.rule.RuleChainId;
import com.ciat.bim.tenant.HasTenantId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.Setter;
import lombok.ToString;


@ApiModel
@EqualsAndHashCode(callSuper = true)
@ToString
@Setter
public class Edge extends SearchTextBasedWithAdditionalInfo<EdgeId> implements HasName, HasTenantId, HasCustomerId {

    private static final long serialVersionUID = 4934987555236873728L;

    private TenantId tenantId;
    private CustomerId customerId;
    private RuleChainId rootRuleChainId;

    private String name;

    private String type;

    private String label;

    private String routingKey;

    private String secret;

    private String edgeLicenseKey;

    private String cloudEndpoint;

    public Edge() {
        super();
    }

    public Edge(EdgeId id) {
        super(id);
    }

    public Edge(Edge edge) {
        super(edge);
        this.tenantId = edge.getTenantId();
        this.customerId = edge.getCustomerId();
        this.rootRuleChainId = edge.getRootRuleChainId();
        this.type = edge.getType();
        this.label = edge.getLabel();
        this.name = edge.getName();
        this.routingKey = edge.getRoutingKey();
        this.secret = edge.getSecret();
        this.edgeLicenseKey = edge.getEdgeLicenseKey();
        this.cloudEndpoint = edge.getCloudEndpoint();
    }

    public void update(Edge edge) {
        this.tenantId = edge.getTenantId();
        this.customerId = edge.getCustomerId();
        this.rootRuleChainId = edge.getRootRuleChainId();
        this.type = edge.getType();
        this.label = edge.getLabel();
        this.name = edge.getName();
        this.routingKey = edge.getRoutingKey();
        this.secret = edge.getSecret();
        this.edgeLicenseKey = edge.getEdgeLicenseKey();
        this.cloudEndpoint = edge.getCloudEndpoint();
    }

    @ApiModelProperty(position = 1, value = "JSON object with the Edge Id. " +
            "Specify this field to update the Edge. " +
            "Referencing non-existing Edge Id will cause error. " +
            "Omit this field to create new Edge." )
    @Override
    public EdgeId getId() {
        return super.getId();
    }

    @ApiModelProperty(position = 2, value = "Timestamp of the edge creation, in milliseconds", example = "1609459200000", readOnly = true)
    @Override
    public long getCreatedTime() {
        return super.getCreatedTime();
    }

    @ApiModelProperty(position = 3, value = "JSON object with Tenant Id. Use 'assignDeviceToTenant' to change the Tenant Id.", readOnly = true)
    @Override
    public TenantId getTenantId() {
        return this.tenantId;
    }

    @ApiModelProperty(position = 4, value = "JSON object with Customer Id. Use 'assignEdgeToCustomer' to change the Customer Id.", readOnly = true)
    @Override
    public CustomerId getCustomerId() {
        return this.customerId;
    }

    @ApiModelProperty(position = 5, value = "JSON object with Root Rule Chain Id. Use 'setEdgeRootRuleChain' to change the Root Rule Chain Id.", readOnly = true)
    public RuleChainId getRootRuleChainId() {
        return this.rootRuleChainId;
    }

    @ApiModelProperty(position = 6, required = true, value = "Unique Edge Name in scope of Tenant", example = "Silo_A_Edge")
    @Override
    public String getName() {
        return this.name;
    }

    @ApiModelProperty(position = 7, required = true, value = "Edge type", example = "Silos")
    public String getType() {
        return this.type;
    }

    @ApiModelProperty(position = 8, value = "Label that may be used in widgets", example = "Silo Edge on far field")
    public String getLabel() {
        return this.label;
    }

    @Override
    public String getSearchText() {
        return getName();
    }

    @ApiModelProperty(position = 9, required = true, value = "Edge routing key ('username') to authorize on cloud")
    public String getRoutingKey() {
        return this.routingKey;
    }

    @ApiModelProperty(position = 10, required = true, value = "Edge secret ('password') to authorize on cloud")
    public String getSecret() {
        return this.secret;
    }

    @ApiModelProperty(position = 11, required = true, value = "Edge license key obtained from license portal", example = "AgcnI24Z06XC&m6Sxsdgf")
    public String getEdgeLicenseKey() {
        return this.edgeLicenseKey;
    }

    @ApiModelProperty(position = 12, required = true, value = "Edge uses this cloud URL to activate and periodically check it's license", example = "https://thingsboard.cloud")
    public String getCloudEndpoint() {
        return this.cloudEndpoint;
    }

}
