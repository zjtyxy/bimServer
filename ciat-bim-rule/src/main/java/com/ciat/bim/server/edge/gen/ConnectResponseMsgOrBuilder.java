// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: edge.proto

package com.ciat.bim.server.edge.gen;

public interface ConnectResponseMsgOrBuilder extends
    // @@protoc_insertion_point(interface_extends:edge.ConnectResponseMsg)
    com.google.protobuf.MessageOrBuilder {

  /**
   * <code>.edge.ConnectResponseCode responseCode = 1;</code>
   * @return The enum numeric value on the wire for responseCode.
   */
  int getResponseCodeValue();
  /**
   * <code>.edge.ConnectResponseCode responseCode = 1;</code>
   * @return The responseCode.
   */
  ConnectResponseCode getResponseCode();

  /**
   * <code>string errorMsg = 2;</code>
   * @return The errorMsg.
   */
  String getErrorMsg();
  /**
   * <code>string errorMsg = 2;</code>
   * @return The bytes for errorMsg.
   */
  com.google.protobuf.ByteString
      getErrorMsgBytes();

  /**
   * <code>.edge.EdgeConfiguration configuration = 3;</code>
   * @return Whether the configuration field is set.
   */
  boolean hasConfiguration();
  /**
   * <code>.edge.EdgeConfiguration configuration = 3;</code>
   * @return The configuration.
   */
  EdgeConfiguration getConfiguration();
  /**
   * <code>.edge.EdgeConfiguration configuration = 3;</code>
   */
  EdgeConfigurationOrBuilder getConfigurationOrBuilder();
}