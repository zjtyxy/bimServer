// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: edge.proto

package com.ciat.bim.server.edge.gen;

public interface AttributeDeleteMsgOrBuilder extends
    // @@protoc_insertion_point(interface_extends:edge.AttributeDeleteMsg)
    com.google.protobuf.MessageOrBuilder {

  /**
   * <code>string scope = 1;</code>
   * @return The scope.
   */
  String getScope();
  /**
   * <code>string scope = 1;</code>
   * @return The bytes for scope.
   */
  com.google.protobuf.ByteString
      getScopeBytes();

  /**
   * <code>repeated string attributeNames = 2;</code>
   * @return A list containing the attributeNames.
   */
  java.util.List<String>
      getAttributeNamesList();
  /**
   * <code>repeated string attributeNames = 2;</code>
   * @return The count of attributeNames.
   */
  int getAttributeNamesCount();
  /**
   * <code>repeated string attributeNames = 2;</code>
   * @param index The index of the element to return.
   * @return The attributeNames at the given index.
   */
  String getAttributeNames(int index);
  /**
   * <code>repeated string attributeNames = 2;</code>
   * @param index The index of the value to return.
   * @return The bytes of the attributeNames at the given index.
   */
  com.google.protobuf.ByteString
      getAttributeNamesBytes(int index);
}
