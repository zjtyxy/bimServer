// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: edge.proto

package com.ciat.bim.server.edge.gen;

public interface ResponseMsgOrBuilder extends
    // @@protoc_insertion_point(interface_extends:edge.ResponseMsg)
    com.google.protobuf.MessageOrBuilder {

  /**
   * <code>.edge.ConnectResponseMsg connectResponseMsg = 1;</code>
   * @return Whether the connectResponseMsg field is set.
   */
  boolean hasConnectResponseMsg();
  /**
   * <code>.edge.ConnectResponseMsg connectResponseMsg = 1;</code>
   * @return The connectResponseMsg.
   */
  ConnectResponseMsg getConnectResponseMsg();
  /**
   * <code>.edge.ConnectResponseMsg connectResponseMsg = 1;</code>
   */
  ConnectResponseMsgOrBuilder getConnectResponseMsgOrBuilder();

  /**
   * <code>.edge.UplinkResponseMsg uplinkResponseMsg = 2;</code>
   * @return Whether the uplinkResponseMsg field is set.
   */
  boolean hasUplinkResponseMsg();
  /**
   * <code>.edge.UplinkResponseMsg uplinkResponseMsg = 2;</code>
   * @return The uplinkResponseMsg.
   */
  UplinkResponseMsg getUplinkResponseMsg();
  /**
   * <code>.edge.UplinkResponseMsg uplinkResponseMsg = 2;</code>
   */
  UplinkResponseMsgOrBuilder getUplinkResponseMsgOrBuilder();

  /**
   * <code>.edge.DownlinkMsg downlinkMsg = 3;</code>
   * @return Whether the downlinkMsg field is set.
   */
  boolean hasDownlinkMsg();
  /**
   * <code>.edge.DownlinkMsg downlinkMsg = 3;</code>
   * @return The downlinkMsg.
   */
  DownlinkMsg getDownlinkMsg();
  /**
   * <code>.edge.DownlinkMsg downlinkMsg = 3;</code>
   */
  DownlinkMsgOrBuilder getDownlinkMsgOrBuilder();

  /**
   * <code>.edge.EdgeUpdateMsg edgeUpdateMsg = 4;</code>
   * @return Whether the edgeUpdateMsg field is set.
   */
  boolean hasEdgeUpdateMsg();
  /**
   * <code>.edge.EdgeUpdateMsg edgeUpdateMsg = 4;</code>
   * @return The edgeUpdateMsg.
   */
  EdgeUpdateMsg getEdgeUpdateMsg();
  /**
   * <code>.edge.EdgeUpdateMsg edgeUpdateMsg = 4;</code>
   */
  EdgeUpdateMsgOrBuilder getEdgeUpdateMsgOrBuilder();
}