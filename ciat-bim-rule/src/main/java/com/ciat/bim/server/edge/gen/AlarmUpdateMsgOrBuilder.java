// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: edge.proto

package com.ciat.bim.server.edge.gen;

public interface AlarmUpdateMsgOrBuilder extends
    // @@protoc_insertion_point(interface_extends:edge.AlarmUpdateMsg)
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
   * <code>string type = 5;</code>
   * @return The type.
   */
  String getType();
  /**
   * <code>string type = 5;</code>
   * @return The bytes for type.
   */
  com.google.protobuf.ByteString
      getTypeBytes();

  /**
   * <code>string originatorType = 6;</code>
   * @return The originatorType.
   */
  String getOriginatorType();
  /**
   * <code>string originatorType = 6;</code>
   * @return The bytes for originatorType.
   */
  com.google.protobuf.ByteString
      getOriginatorTypeBytes();

  /**
   * <code>string originatorName = 7;</code>
   * @return The originatorName.
   */
  String getOriginatorName();
  /**
   * <code>string originatorName = 7;</code>
   * @return The bytes for originatorName.
   */
  com.google.protobuf.ByteString
      getOriginatorNameBytes();

  /**
   * <code>string severity = 8;</code>
   * @return The severity.
   */
  String getSeverity();
  /**
   * <code>string severity = 8;</code>
   * @return The bytes for severity.
   */
  com.google.protobuf.ByteString
      getSeverityBytes();

  /**
   * <code>string status = 9;</code>
   * @return The status.
   */
  String getStatus();
  /**
   * <code>string status = 9;</code>
   * @return The bytes for status.
   */
  com.google.protobuf.ByteString
      getStatusBytes();

  /**
   * <code>int64 startTs = 10;</code>
   * @return The startTs.
   */
  long getStartTs();

  /**
   * <code>int64 endTs = 11;</code>
   * @return The endTs.
   */
  long getEndTs();

  /**
   * <code>int64 ackTs = 12;</code>
   * @return The ackTs.
   */
  long getAckTs();

  /**
   * <code>int64 clearTs = 13;</code>
   * @return The clearTs.
   */
  long getClearTs();

  /**
   * <code>string details = 14;</code>
   * @return The details.
   */
  String getDetails();
  /**
   * <code>string details = 14;</code>
   * @return The bytes for details.
   */
  com.google.protobuf.ByteString
      getDetailsBytes();

  /**
   * <code>bool propagate = 15;</code>
   * @return The propagate.
   */
  boolean getPropagate();
}
