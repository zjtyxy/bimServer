// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: edge.proto

package com.ciat.bim.server.edge.gen;

public interface DeviceRpcCallMsgOrBuilder extends
    // @@protoc_insertion_point(interface_extends:edge.DeviceRpcCallMsg)
    com.google.protobuf.MessageOrBuilder {

  /**
   * <code>int64 deviceIdMSB = 1;</code>
   * @return The deviceIdMSB.
   */
  long getDeviceIdMSB();

  /**
   * <code>int64 deviceIdLSB = 2;</code>
   * @return The deviceIdLSB.
   */
  long getDeviceIdLSB();

  /**
   * <code>int64 requestUuidMSB = 3;</code>
   * @return The requestUuidMSB.
   */
  long getRequestUuidMSB();

  /**
   * <code>int64 requestUuidLSB = 4;</code>
   * @return The requestUuidLSB.
   */
  long getRequestUuidLSB();

  /**
   * <code>int32 requestId = 5;</code>
   * @return The requestId.
   */
  int getRequestId();

  /**
   * <code>int64 expirationTime = 6;</code>
   * @return The expirationTime.
   */
  long getExpirationTime();

  /**
   * <code>bool oneway = 7;</code>
   * @return The oneway.
   */
  boolean getOneway();

  /**
   * <code>.edge.RpcRequestMsg requestMsg = 8;</code>
   * @return Whether the requestMsg field is set.
   */
  boolean hasRequestMsg();
  /**
   * <code>.edge.RpcRequestMsg requestMsg = 8;</code>
   * @return The requestMsg.
   */
  RpcRequestMsg getRequestMsg();
  /**
   * <code>.edge.RpcRequestMsg requestMsg = 8;</code>
   */
  RpcRequestMsgOrBuilder getRequestMsgOrBuilder();

  /**
   * <code>.edge.RpcResponseMsg responseMsg = 9;</code>
   * @return Whether the responseMsg field is set.
   */
  boolean hasResponseMsg();
  /**
   * <code>.edge.RpcResponseMsg responseMsg = 9;</code>
   * @return The responseMsg.
   */
  RpcResponseMsg getResponseMsg();
  /**
   * <code>.edge.RpcResponseMsg responseMsg = 9;</code>
   */
  RpcResponseMsgOrBuilder getResponseMsgOrBuilder();
}
