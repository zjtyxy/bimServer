// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: edge.proto

package com.ciat.bim.server.edge.gen;

public interface RuleNodeProtoOrBuilder extends
    // @@protoc_insertion_point(interface_extends:edge.RuleNodeProto)
    com.google.protobuf.MessageOrBuilder {

  /**
   * <code>int64 idMSB = 1;</code>
   * @return The idMSB.
   */
  long getIdMSB();

  /**
   * <code>int64 idLSB = 2;</code>
   * @return The idLSB.
   */
  long getIdLSB();

  /**
   * <code>string type = 3;</code>
   * @return The type.
   */
  String getType();
  /**
   * <code>string type = 3;</code>
   * @return The bytes for type.
   */
  com.google.protobuf.ByteString
      getTypeBytes();

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
   * <code>bool debugMode = 5;</code>
   * @return The debugMode.
   */
  boolean getDebugMode();

  /**
   * <code>string configuration = 6;</code>
   * @return The configuration.
   */
  String getConfiguration();
  /**
   * <code>string configuration = 6;</code>
   * @return The bytes for configuration.
   */
  com.google.protobuf.ByteString
      getConfigurationBytes();

  /**
   * <code>string additionalInfo = 7;</code>
   * @return The additionalInfo.
   */
  String getAdditionalInfo();
  /**
   * <code>string additionalInfo = 7;</code>
   * @return The bytes for additionalInfo.
   */
  com.google.protobuf.ByteString
      getAdditionalInfoBytes();
}
