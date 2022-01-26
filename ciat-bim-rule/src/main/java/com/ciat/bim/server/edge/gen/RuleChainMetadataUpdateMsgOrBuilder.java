// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: edge.proto

package com.ciat.bim.server.edge.gen;

public interface RuleChainMetadataUpdateMsgOrBuilder extends
    // @@protoc_insertion_point(interface_extends:edge.RuleChainMetadataUpdateMsg)
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
   * <code>int64 ruleChainIdMSB = 2;</code>
   * @return The ruleChainIdMSB.
   */
  long getRuleChainIdMSB();

  /**
   * <code>int64 ruleChainIdLSB = 3;</code>
   * @return The ruleChainIdLSB.
   */
  long getRuleChainIdLSB();

  /**
   * <code>int32 firstNodeIndex = 4;</code>
   * @return The firstNodeIndex.
   */
  int getFirstNodeIndex();

  /**
   * <code>repeated .edge.RuleNodeProto nodes = 5;</code>
   */
  java.util.List<RuleNodeProto>
      getNodesList();
  /**
   * <code>repeated .edge.RuleNodeProto nodes = 5;</code>
   */
  RuleNodeProto getNodes(int index);
  /**
   * <code>repeated .edge.RuleNodeProto nodes = 5;</code>
   */
  int getNodesCount();
  /**
   * <code>repeated .edge.RuleNodeProto nodes = 5;</code>
   */
  java.util.List<? extends RuleNodeProtoOrBuilder>
      getNodesOrBuilderList();
  /**
   * <code>repeated .edge.RuleNodeProto nodes = 5;</code>
   */
  RuleNodeProtoOrBuilder getNodesOrBuilder(
      int index);

  /**
   * <code>repeated .edge.NodeConnectionInfoProto connections = 6;</code>
   */
  java.util.List<NodeConnectionInfoProto>
      getConnectionsList();
  /**
   * <code>repeated .edge.NodeConnectionInfoProto connections = 6;</code>
   */
  NodeConnectionInfoProto getConnections(int index);
  /**
   * <code>repeated .edge.NodeConnectionInfoProto connections = 6;</code>
   */
  int getConnectionsCount();
  /**
   * <code>repeated .edge.NodeConnectionInfoProto connections = 6;</code>
   */
  java.util.List<? extends NodeConnectionInfoProtoOrBuilder>
      getConnectionsOrBuilderList();
  /**
   * <code>repeated .edge.NodeConnectionInfoProto connections = 6;</code>
   */
  NodeConnectionInfoProtoOrBuilder getConnectionsOrBuilder(
      int index);

  /**
   * <code>repeated .edge.RuleChainConnectionInfoProto ruleChainConnections = 7;</code>
   */
  java.util.List<RuleChainConnectionInfoProto>
      getRuleChainConnectionsList();
  /**
   * <code>repeated .edge.RuleChainConnectionInfoProto ruleChainConnections = 7;</code>
   */
  RuleChainConnectionInfoProto getRuleChainConnections(int index);
  /**
   * <code>repeated .edge.RuleChainConnectionInfoProto ruleChainConnections = 7;</code>
   */
  int getRuleChainConnectionsCount();
  /**
   * <code>repeated .edge.RuleChainConnectionInfoProto ruleChainConnections = 7;</code>
   */
  java.util.List<? extends RuleChainConnectionInfoProtoOrBuilder>
      getRuleChainConnectionsOrBuilderList();
  /**
   * <code>repeated .edge.RuleChainConnectionInfoProto ruleChainConnections = 7;</code>
   */
  RuleChainConnectionInfoProtoOrBuilder getRuleChainConnectionsOrBuilder(
      int index);
}
