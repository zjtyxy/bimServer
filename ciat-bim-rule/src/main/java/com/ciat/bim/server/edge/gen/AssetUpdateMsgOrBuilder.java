// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: edge.proto

package com.ciat.bim.server.edge.gen;

public interface AssetUpdateMsgOrBuilder extends
    // @@protoc_insertion_point(interface_extends:edge.AssetUpdateMsg)
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
   * <code>optional int64 customerIdMSB = 4;</code>
   * @return Whether the customerIdMSB field is set.
   */
  boolean hasCustomerIdMSB();
  /**
   * <code>optional int64 customerIdMSB = 4;</code>
   * @return The customerIdMSB.
   */
  long getCustomerIdMSB();

  /**
   * <code>optional int64 customerIdLSB = 5;</code>
   * @return Whether the customerIdLSB field is set.
   */
  boolean hasCustomerIdLSB();
  /**
   * <code>optional int64 customerIdLSB = 5;</code>
   * @return The customerIdLSB.
   */
  long getCustomerIdLSB();

  /**
   * <code>string name = 6;</code>
   * @return The name.
   */
  String getName();
  /**
   * <code>string name = 6;</code>
   * @return The bytes for name.
   */
  com.google.protobuf.ByteString
      getNameBytes();

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
   * <code>optional string label = 8;</code>
   * @return Whether the label field is set.
   */
  boolean hasLabel();
  /**
   * <code>optional string label = 8;</code>
   * @return The label.
   */
  String getLabel();
  /**
   * <code>optional string label = 8;</code>
   * @return The bytes for label.
   */
  com.google.protobuf.ByteString
      getLabelBytes();

  /**
   * <code>optional string additionalInfo = 9;</code>
   * @return Whether the additionalInfo field is set.
   */
  boolean hasAdditionalInfo();
  /**
   * <code>optional string additionalInfo = 9;</code>
   * @return The additionalInfo.
   */
  String getAdditionalInfo();
  /**
   * <code>optional string additionalInfo = 9;</code>
   * @return The bytes for additionalInfo.
   */
  com.google.protobuf.ByteString
      getAdditionalInfoBytes();
}
