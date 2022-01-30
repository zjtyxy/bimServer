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
package com.ciat.bim.server.common.data.device.profile;

import com.ciat.bim.server.common.data.TransportPayloadType;
import com.github.os72.protobuf.dynamic.DynamicSchema;
import com.github.os72.protobuf.dynamic.EnumDefinition;
import com.github.os72.protobuf.dynamic.MessageDefinition;
import com.google.protobuf.Descriptors;
import com.google.protobuf.DynamicMessage;
import com.squareup.wire.schema.Location;
import com.squareup.wire.schema.internal.parser.*;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Data
public class ProtoTransportPayloadConfiguration implements TransportPayloadTypeConfiguration {

    public static final Location LOCATION = new Location("", "", -1, -1);
    public static final String ATTRIBUTES_PROTO_SCHEMA = "attributes proto schema";
    public static final String TELEMETRY_PROTO_SCHEMA = "telemetry proto schema";
    public static final String RPC_RESPONSE_PROTO_SCHEMA = "rpc response proto schema";
    public static final String RPC_REQUEST_PROTO_SCHEMA = "rpc request proto schema";
    private static final String PROTO_3_SYNTAX = "proto3";

    private String deviceTelemetryProtoSchema;
    private String deviceAttributesProtoSchema;
    private String deviceRpcRequestProtoSchema;
    private String deviceRpcResponseProtoSchema;

    private boolean enableCompatibilityWithJsonPayloadFormat;
    private boolean useJsonPayloadFormatForDefaultDownlinkTopics;

    @Override
    public TransportPayloadType getTransportPayloadType() {
        return TransportPayloadType.PROTOBUF;
    }

    public Descriptors.Descriptor getTelemetryDynamicMessageDescriptor(String deviceTelemetryProtoSchema) {
        return getDescriptor(deviceTelemetryProtoSchema, TELEMETRY_PROTO_SCHEMA);
    }

    public Descriptors.Descriptor getAttributesDynamicMessageDescriptor(String deviceAttributesProtoSchema) {
        return getDescriptor(deviceAttributesProtoSchema, ATTRIBUTES_PROTO_SCHEMA);
    }

    public Descriptors.Descriptor getRpcResponseDynamicMessageDescriptor(String deviceRpcResponseProtoSchema) {
        return getDescriptor(deviceRpcResponseProtoSchema, RPC_RESPONSE_PROTO_SCHEMA);
    }

    public DynamicMessage.Builder getRpcRequestDynamicMessageBuilder(String deviceRpcRequestProtoSchema) {
        return getDynamicMessageBuilder(deviceRpcRequestProtoSchema, RPC_REQUEST_PROTO_SCHEMA);
    }

    public String getDeviceRpcResponseProtoSchema() {
        if (!isEmptyStr(deviceRpcResponseProtoSchema)) {
            return deviceRpcResponseProtoSchema;
        } else {
            return "syntax =\"proto3\";\n" +
                    "package rpc;\n" +
                    "\n" +
                    "message RpcResponseMsg {\n" +
                    "  optional string payload = 1;\n" +
                    "}";
        }
    }

    public String getDeviceRpcRequestProtoSchema() {
        if (!isEmptyStr(deviceRpcRequestProtoSchema)) {
            return deviceRpcRequestProtoSchema;
        } else {
            return "syntax =\"proto3\";\n" +
                    "package rpc;\n" +
                    "\n" +
                    "message RpcRequestMsg {\n" +
                    "  optional string method = 1;\n" +
                    "  optional int32 requestId = 2;\n" +
                    "  optional string params = 3;\n" +
                    "}";
        }
    }

    private Descriptors.Descriptor getDescriptor(String protoSchema, String schemaName) {
        try {
            DynamicMessage.Builder builder = getDynamicMessageBuilder(protoSchema, schemaName);
            return builder.getDescriptorForType();
        } catch (Exception e) {
            log.warn("Failed to get Message Descriptor due to {}", e.getMessage());
            return null;
        }
    }

    public DynamicMessage.Builder getDynamicMessageBuilder(String protoSchema, String schemaName) {
        ProtoFileElement protoFileElement = getTransportProtoSchema(protoSchema);
        DynamicSchema dynamicSchema = getDynamicSchema(protoFileElement, schemaName);
        String lastMsgName = getMessageTypes(protoFileElement.getTypes()).stream()
                .map(MessageElement::getName).reduce((previous, last) -> last).get();
        return dynamicSchema.newMessageBuilder(lastMsgName);
    }

    public DynamicSchema getDynamicSchema(ProtoFileElement protoFileElement, String schemaName) {
        DynamicSchema.Builder schemaBuilder = DynamicSchema.newBuilder();
        schemaBuilder.setName(schemaName);
        schemaBuilder.setSyntax(PROTO_3_SYNTAX);
        schemaBuilder.setPackage(!isEmptyStr(protoFileElement.getPackageName()) ?
                protoFileElement.getPackageName() : schemaName.toLowerCase());
        List<TypeElement> types = protoFileElement.getTypes();
        List<MessageElement> messageTypes = getMessageTypes(types);

        if (!messageTypes.isEmpty()) {
            List<EnumElement> enumTypes = getEnumElements(types);
            if (!enumTypes.isEmpty()) {
                enumTypes.forEach(enumElement -> {
                    EnumDefinition enumDefinition = getEnumDefinition(enumElement);
                    schemaBuilder.addEnumDefinition(enumDefinition);
                });
            }
            List<MessageDefinition> messageDefinitions = getMessageDefinitions(messageTypes);
            messageDefinitions.forEach(schemaBuilder::addMessageDefinition);
            try {
                return schemaBuilder.build();
            } catch (Descriptors.DescriptorValidationException e) {
                throw new RuntimeException("Failed to create dynamic schema due to: " + e.getMessage());
            }
        } else {
            throw new RuntimeException("Failed to get Dynamic Schema! Message types is empty for schema:" + schemaName);
        }
    }

    public ProtoFileElement getTransportProtoSchema(String protoSchema) {
        return new ProtoParser(LOCATION, protoSchema.toCharArray()).readProtoFile();
    }

    private List<MessageElement> getMessageTypes(List<TypeElement> types) {
        return types.stream()
                .filter(typeElement -> typeElement instanceof MessageElement)
                .map(typeElement -> (MessageElement) typeElement)
                .collect(Collectors.toList());
    }

    private List<EnumElement> getEnumElements(List<TypeElement> types) {
        return types.stream()
                .filter(typeElement -> typeElement instanceof EnumElement)
                .map(typeElement -> (EnumElement) typeElement)
                .collect(Collectors.toList());
    }

    private List<MessageDefinition> getMessageDefinitions(List<MessageElement> messageElementsList) {
        if (!messageElementsList.isEmpty()) {
            List<MessageDefinition> messageDefinitions = new ArrayList<>();
            messageElementsList.forEach(messageElement -> {
                MessageDefinition.Builder messageDefinitionBuilder = MessageDefinition.newBuilder(messageElement.getName());

                List<TypeElement> nestedTypes = messageElement.getNestedTypes();
                if (!nestedTypes.isEmpty()) {
                    List<EnumElement> nestedEnumTypes = getEnumElements(nestedTypes);
                    if (!nestedEnumTypes.isEmpty()) {
                        nestedEnumTypes.forEach(enumElement -> {
                            EnumDefinition nestedEnumDefinition = getEnumDefinition(enumElement);
                            messageDefinitionBuilder.addEnumDefinition(nestedEnumDefinition);
                        });
                    }
                    List<MessageElement> nestedMessageTypes = getMessageTypes(nestedTypes);
                    List<MessageDefinition> nestedMessageDefinitions = getMessageDefinitions(nestedMessageTypes);
                    nestedMessageDefinitions.forEach(messageDefinitionBuilder::addMessageDefinition);
                }
                List<FieldElement> messageElementFields = messageElement.getFields();
                List<OneOfElement> oneOfs = messageElement.getOneOfs();
                if (!oneOfs.isEmpty()) {
                    for (OneOfElement oneOfelement : oneOfs) {
                        MessageDefinition.OneofBuilder oneofBuilder = messageDefinitionBuilder.addOneof(oneOfelement.getName());
                        addMessageFieldsToTheOneOfDefinition(oneOfelement.getFields(), oneofBuilder);
                    }
                }
                if (!messageElementFields.isEmpty()) {
                    addMessageFieldsToTheMessageDefinition(messageElementFields, messageDefinitionBuilder);
                }
                messageDefinitions.add(messageDefinitionBuilder.build());
            });
            return messageDefinitions;
        } else {
            return Collections.emptyList();
        }
    }

    private EnumDefinition getEnumDefinition(EnumElement enumElement) {
        List<EnumConstantElement> enumElementTypeConstants = enumElement.getConstants();
        EnumDefinition.Builder enumDefinitionBuilder = EnumDefinition.newBuilder(enumElement.getName());
        if (!enumElementTypeConstants.isEmpty()) {
            enumElementTypeConstants.forEach(constantElement -> enumDefinitionBuilder.addValue(constantElement.getName(), constantElement.getTag()));
        }
        return enumDefinitionBuilder.build();
    }


    private void addMessageFieldsToTheMessageDefinition(List<FieldElement> messageElementFields, MessageDefinition.Builder messageDefinitionBuilder) {
        messageElementFields.forEach(fieldElement -> {
            String labelStr = null;
            if (fieldElement.getLabel() != null) {
                labelStr = fieldElement.getLabel().name().toLowerCase();
            }
            messageDefinitionBuilder.addField(
                    labelStr,
                    fieldElement.getType(),
                    fieldElement.getName(),
                    fieldElement.getTag());
        });
    }

    private void addMessageFieldsToTheOneOfDefinition(List<FieldElement> oneOfsElementFields, MessageDefinition.OneofBuilder oneofBuilder) {
        oneOfsElementFields.forEach(fieldElement -> oneofBuilder.addField(
                fieldElement.getType(),
                fieldElement.getName(),
                fieldElement.getTag()));
        oneofBuilder.msgDefBuilder();
    }

    private boolean isEmptyStr(String str) {
        return str == null || "".equals(str);
    }

}
