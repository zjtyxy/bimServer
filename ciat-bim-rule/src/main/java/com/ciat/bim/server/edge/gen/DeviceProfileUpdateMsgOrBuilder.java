// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: edge.proto

package com.ciat.bim.server.edge.gen;

public interface DeviceProfileUpdateMsgOrBuilder extends
    // @@protoc_insertion_point(interface_extends:edge.DeviceProfileUpdateMsg)
    com.google.protobuf.MessageOrBuilder {

  /**
   * <code>.edge.UpdateMsgType msgType = 1;</code>
   * @return The enum numeric value on the wire for msgType.
   */
  int getMsgTypeValue();
  /**
   * <code>.edge.UpdateMsgType msgType = 1;</code>
   * @return The msgType.
   */
  UpdateMsgType getMsgType();

  /**
   * <code>int64 idMSB = 2;</code>
   * @return The idMSB.
   */
  long getIdMSB();

  /**
   * <code>int64 idLSB = 3;</code>
   * @return The idLSB.
   */
  long getIdLSB();

  /**
   * <code>string name = 4;</code>
   * @return The name.
   */
  String getName();
  /**
   * <code>string name = 4;</code>
   * @return The bytes for name.
   */
  com.google.protobuf.ByteString
      getNameBytes();

  /**
   * <code>optional string description = 5;</code>
   * @return Whether the description field is set.
   */
  boolean hasDescription();
  /**
   * <code>optional string description = 5;</code>
   * @return The description.
   */
  String getDescription();
  /**
   * <code>optional string description = 5;</code>
   * @return The bytes for description.
   */
  com.google.protobuf.ByteString
      getDescriptionBytes();

  /**
   * <code>bool default = 6;</code>
   * @return The default.
   */
  boolean getDefault();

  /**
   * <code>string type = 7;</code>
   * @return The type.
   */
  String getType();
  /**
   * <code>string type = 7;</code>
   * @return The bytes for type.
   */
  com.google.protobuf.ByteString
      getTypeBytes();

  /**
   * <code>optional string transportType = 8;</code>
   * @return Whether the transportType field is set.
   */
  boolean hasTransportType();
  /**
   * <code>optional string transportType = 8;</code>
   * @return The transportType.
   */
  String getTransportType();
  /**
   * <code>optional string transportType = 8;</code>
   * @return The bytes for transportType.
   */
  com.google.protobuf.ByteString
      getTransportTypeBytes();

  /**
   * <code>optional string provisionType = 9;</code>
   * @return Whether the provisionType field is set.
   */
  boolean hasProvisionType();
  /**
   * <code>optional string provisionType = 9;</code>
   * @return The provisionType.
   */
  String getProvisionType();
  /**
   * <code>optional string provisionType = 9;</code>
   * @return The bytes for provisionType.
   */
  com.google.protobuf.ByteString
      getProvisionTypeBytes();

  /**
   * <code>int64 defaultRuleChainIdMSB = 10;</code>
   * @return The defaultRuleChainIdMSB.
   */
  long getDefaultRuleChainIdMSB();

  /**
   * <code>int64 defaultRuleChainIdLSB = 11;</code>
   * @return The defaultRuleChainIdLSB.
   */
  long getDefaultRuleChainIdLSB();

  /**
   * <code>string defaultQueueName = 12;</code>
   * @return The defaultQueueName.
   */
  String getDefaultQueueName();
  /**
   * <code>string defaultQueueName = 12;</code>
   * @return The bytes for defaultQueueName.
   */
  com.google.protobuf.ByteString
      getDefaultQueueNameBytes();

  /**
   * <code>bytes profileDataBytes = 13;</code>
   * @return The profileDataBytes.
   */
  com.google.protobuf.ByteString getProfileDataBytes();

  /**
   * <code>optional string provisionDeviceKey = 14;</code>
   * @return Whether the provisionDeviceKey field is set.
   */
  boolean hasProvisionDeviceKey();
  /**
   * <code>optional string provisionDeviceKey = 14;</code>
   * @return The provisionDeviceKey.
   */
  String getProvisionDeviceKey();
  /**
   * <code>optional string provisionDeviceKey = 14;</code>
   * @return The bytes for provisionDeviceKey.
   */
  com.google.protobuf.ByteString
      getProvisionDeviceKeyBytes();

  /**
   * <code>optional bytes image = 15;</code>
   * @return Whether the image field is set.
   */
  boolean hasImage();
  /**
   * <code>optional bytes image = 15;</code>
   * @return The image.
   */
  com.google.protobuf.ByteString getImage();
}
