// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: edge.proto

package com.ciat.bim.server.edge.gen;

/**
 * Protobuf type {@code edge.ResponseMsg}
 */
public final class ResponseMsg extends
    com.google.protobuf.GeneratedMessageV3 implements
    // @@protoc_insertion_point(message_implements:edge.ResponseMsg)
        ResponseMsgOrBuilder {
private static final long serialVersionUID = 0L;
  // Use ResponseMsg.newBuilder() to construct.
  private ResponseMsg(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
    super(builder);
  }
  private ResponseMsg() {
  }

  @Override
  @SuppressWarnings({"unused"})
  protected Object newInstance(
      UnusedPrivateParameter unused) {
    return new ResponseMsg();
  }

  @Override
  public final com.google.protobuf.UnknownFieldSet
  getUnknownFields() {
    return this.unknownFields;
  }
  private ResponseMsg(
      com.google.protobuf.CodedInputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    this();
    if (extensionRegistry == null) {
      throw new NullPointerException();
    }
    com.google.protobuf.UnknownFieldSet.Builder unknownFields =
        com.google.protobuf.UnknownFieldSet.newBuilder();
    try {
      boolean done = false;
      while (!done) {
        int tag = input.readTag();
        switch (tag) {
          case 0:
            done = true;
            break;
          case 10: {
            ConnectResponseMsg.Builder subBuilder = null;
            if (connectResponseMsg_ != null) {
              subBuilder = connectResponseMsg_.toBuilder();
            }
            connectResponseMsg_ = input.readMessage(ConnectResponseMsg.parser(), extensionRegistry);
            if (subBuilder != null) {
              subBuilder.mergeFrom(connectResponseMsg_);
              connectResponseMsg_ = subBuilder.buildPartial();
            }

            break;
          }
          case 18: {
            UplinkResponseMsg.Builder subBuilder = null;
            if (uplinkResponseMsg_ != null) {
              subBuilder = uplinkResponseMsg_.toBuilder();
            }
            uplinkResponseMsg_ = input.readMessage(UplinkResponseMsg.parser(), extensionRegistry);
            if (subBuilder != null) {
              subBuilder.mergeFrom(uplinkResponseMsg_);
              uplinkResponseMsg_ = subBuilder.buildPartial();
            }

            break;
          }
          case 26: {
            DownlinkMsg.Builder subBuilder = null;
            if (downlinkMsg_ != null) {
              subBuilder = downlinkMsg_.toBuilder();
            }
            downlinkMsg_ = input.readMessage(DownlinkMsg.parser(), extensionRegistry);
            if (subBuilder != null) {
              subBuilder.mergeFrom(downlinkMsg_);
              downlinkMsg_ = subBuilder.buildPartial();
            }

            break;
          }
          case 34: {
            EdgeUpdateMsg.Builder subBuilder = null;
            if (edgeUpdateMsg_ != null) {
              subBuilder = edgeUpdateMsg_.toBuilder();
            }
            edgeUpdateMsg_ = input.readMessage(EdgeUpdateMsg.parser(), extensionRegistry);
            if (subBuilder != null) {
              subBuilder.mergeFrom(edgeUpdateMsg_);
              edgeUpdateMsg_ = subBuilder.buildPartial();
            }

            break;
          }
          default: {
            if (!parseUnknownField(
                input, unknownFields, extensionRegistry, tag)) {
              done = true;
            }
            break;
          }
        }
      }
    } catch (com.google.protobuf.InvalidProtocolBufferException e) {
      throw e.setUnfinishedMessage(this);
    } catch (java.io.IOException e) {
      throw new com.google.protobuf.InvalidProtocolBufferException(
          e).setUnfinishedMessage(this);
    } finally {
      this.unknownFields = unknownFields.build();
      makeExtensionsImmutable();
    }
  }
  public static final com.google.protobuf.Descriptors.Descriptor
      getDescriptor() {
    return EdgeProtos.internal_static_edge_ResponseMsg_descriptor;
  }

  @Override
  protected FieldAccessorTable
      internalGetFieldAccessorTable() {
    return EdgeProtos.internal_static_edge_ResponseMsg_fieldAccessorTable
        .ensureFieldAccessorsInitialized(
            ResponseMsg.class, Builder.class);
  }

  public static final int CONNECTRESPONSEMSG_FIELD_NUMBER = 1;
  private ConnectResponseMsg connectResponseMsg_;
  /**
   * <code>.edge.ConnectResponseMsg connectResponseMsg = 1;</code>
   * @return Whether the connectResponseMsg field is set.
   */
  @Override
  public boolean hasConnectResponseMsg() {
    return connectResponseMsg_ != null;
  }
  /**
   * <code>.edge.ConnectResponseMsg connectResponseMsg = 1;</code>
   * @return The connectResponseMsg.
   */
  @Override
  public ConnectResponseMsg getConnectResponseMsg() {
    return connectResponseMsg_ == null ? ConnectResponseMsg.getDefaultInstance() : connectResponseMsg_;
  }
  /**
   * <code>.edge.ConnectResponseMsg connectResponseMsg = 1;</code>
   */
  @Override
  public ConnectResponseMsgOrBuilder getConnectResponseMsgOrBuilder() {
    return getConnectResponseMsg();
  }

  public static final int UPLINKRESPONSEMSG_FIELD_NUMBER = 2;
  private UplinkResponseMsg uplinkResponseMsg_;
  /**
   * <code>.edge.UplinkResponseMsg uplinkResponseMsg = 2;</code>
   * @return Whether the uplinkResponseMsg field is set.
   */
  @Override
  public boolean hasUplinkResponseMsg() {
    return uplinkResponseMsg_ != null;
  }
  /**
   * <code>.edge.UplinkResponseMsg uplinkResponseMsg = 2;</code>
   * @return The uplinkResponseMsg.
   */
  @Override
  public UplinkResponseMsg getUplinkResponseMsg() {
    return uplinkResponseMsg_ == null ? UplinkResponseMsg.getDefaultInstance() : uplinkResponseMsg_;
  }
  /**
   * <code>.edge.UplinkResponseMsg uplinkResponseMsg = 2;</code>
   */
  @Override
  public UplinkResponseMsgOrBuilder getUplinkResponseMsgOrBuilder() {
    return getUplinkResponseMsg();
  }

  public static final int DOWNLINKMSG_FIELD_NUMBER = 3;
  private DownlinkMsg downlinkMsg_;
  /**
   * <code>.edge.DownlinkMsg downlinkMsg = 3;</code>
   * @return Whether the downlinkMsg field is set.
   */
  @Override
  public boolean hasDownlinkMsg() {
    return downlinkMsg_ != null;
  }
  /**
   * <code>.edge.DownlinkMsg downlinkMsg = 3;</code>
   * @return The downlinkMsg.
   */
  @Override
  public DownlinkMsg getDownlinkMsg() {
    return downlinkMsg_ == null ? DownlinkMsg.getDefaultInstance() : downlinkMsg_;
  }
  /**
   * <code>.edge.DownlinkMsg downlinkMsg = 3;</code>
   */
  @Override
  public DownlinkMsgOrBuilder getDownlinkMsgOrBuilder() {
    return getDownlinkMsg();
  }

  public static final int EDGEUPDATEMSG_FIELD_NUMBER = 4;
  private EdgeUpdateMsg edgeUpdateMsg_;
  /**
   * <code>.edge.EdgeUpdateMsg edgeUpdateMsg = 4;</code>
   * @return Whether the edgeUpdateMsg field is set.
   */
  @Override
  public boolean hasEdgeUpdateMsg() {
    return edgeUpdateMsg_ != null;
  }
  /**
   * <code>.edge.EdgeUpdateMsg edgeUpdateMsg = 4;</code>
   * @return The edgeUpdateMsg.
   */
  @Override
  public EdgeUpdateMsg getEdgeUpdateMsg() {
    return edgeUpdateMsg_ == null ? EdgeUpdateMsg.getDefaultInstance() : edgeUpdateMsg_;
  }
  /**
   * <code>.edge.EdgeUpdateMsg edgeUpdateMsg = 4;</code>
   */
  @Override
  public EdgeUpdateMsgOrBuilder getEdgeUpdateMsgOrBuilder() {
    return getEdgeUpdateMsg();
  }

  private byte memoizedIsInitialized = -1;
  @Override
  public final boolean isInitialized() {
    byte isInitialized = memoizedIsInitialized;
    if (isInitialized == 1) return true;
    if (isInitialized == 0) return false;

    memoizedIsInitialized = 1;
    return true;
  }

  @Override
  public void writeTo(com.google.protobuf.CodedOutputStream output)
                      throws java.io.IOException {
    if (connectResponseMsg_ != null) {
      output.writeMessage(1, getConnectResponseMsg());
    }
    if (uplinkResponseMsg_ != null) {
      output.writeMessage(2, getUplinkResponseMsg());
    }
    if (downlinkMsg_ != null) {
      output.writeMessage(3, getDownlinkMsg());
    }
    if (edgeUpdateMsg_ != null) {
      output.writeMessage(4, getEdgeUpdateMsg());
    }
    unknownFields.writeTo(output);
  }

  @Override
  public int getSerializedSize() {
    int size = memoizedSize;
    if (size != -1) return size;

    size = 0;
    if (connectResponseMsg_ != null) {
      size += com.google.protobuf.CodedOutputStream
        .computeMessageSize(1, getConnectResponseMsg());
    }
    if (uplinkResponseMsg_ != null) {
      size += com.google.protobuf.CodedOutputStream
        .computeMessageSize(2, getUplinkResponseMsg());
    }
    if (downlinkMsg_ != null) {
      size += com.google.protobuf.CodedOutputStream
        .computeMessageSize(3, getDownlinkMsg());
    }
    if (edgeUpdateMsg_ != null) {
      size += com.google.protobuf.CodedOutputStream
        .computeMessageSize(4, getEdgeUpdateMsg());
    }
    size += unknownFields.getSerializedSize();
    memoizedSize = size;
    return size;
  }

  @Override
  public boolean equals(final Object obj) {
    if (obj == this) {
     return true;
    }
    if (!(obj instanceof ResponseMsg)) {
      return super.equals(obj);
    }
    ResponseMsg other = (ResponseMsg) obj;

    if (hasConnectResponseMsg() != other.hasConnectResponseMsg()) return false;
    if (hasConnectResponseMsg()) {
      if (!getConnectResponseMsg()
          .equals(other.getConnectResponseMsg())) return false;
    }
    if (hasUplinkResponseMsg() != other.hasUplinkResponseMsg()) return false;
    if (hasUplinkResponseMsg()) {
      if (!getUplinkResponseMsg()
          .equals(other.getUplinkResponseMsg())) return false;
    }
    if (hasDownlinkMsg() != other.hasDownlinkMsg()) return false;
    if (hasDownlinkMsg()) {
      if (!getDownlinkMsg()
          .equals(other.getDownlinkMsg())) return false;
    }
    if (hasEdgeUpdateMsg() != other.hasEdgeUpdateMsg()) return false;
    if (hasEdgeUpdateMsg()) {
      if (!getEdgeUpdateMsg()
          .equals(other.getEdgeUpdateMsg())) return false;
    }
    if (!unknownFields.equals(other.unknownFields)) return false;
    return true;
  }

  @Override
  public int hashCode() {
    if (memoizedHashCode != 0) {
      return memoizedHashCode;
    }
    int hash = 41;
    hash = (19 * hash) + getDescriptor().hashCode();
    if (hasConnectResponseMsg()) {
      hash = (37 * hash) + CONNECTRESPONSEMSG_FIELD_NUMBER;
      hash = (53 * hash) + getConnectResponseMsg().hashCode();
    }
    if (hasUplinkResponseMsg()) {
      hash = (37 * hash) + UPLINKRESPONSEMSG_FIELD_NUMBER;
      hash = (53 * hash) + getUplinkResponseMsg().hashCode();
    }
    if (hasDownlinkMsg()) {
      hash = (37 * hash) + DOWNLINKMSG_FIELD_NUMBER;
      hash = (53 * hash) + getDownlinkMsg().hashCode();
    }
    if (hasEdgeUpdateMsg()) {
      hash = (37 * hash) + EDGEUPDATEMSG_FIELD_NUMBER;
      hash = (53 * hash) + getEdgeUpdateMsg().hashCode();
    }
    hash = (29 * hash) + unknownFields.hashCode();
    memoizedHashCode = hash;
    return hash;
  }

  public static ResponseMsg parseFrom(
      java.nio.ByteBuffer data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static ResponseMsg parseFrom(
      java.nio.ByteBuffer data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static ResponseMsg parseFrom(
      com.google.protobuf.ByteString data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static ResponseMsg parseFrom(
      com.google.protobuf.ByteString data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static ResponseMsg parseFrom(byte[] data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static ResponseMsg parseFrom(
      byte[] data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static ResponseMsg parseFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input);
  }
  public static ResponseMsg parseFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input, extensionRegistry);
  }
  public static ResponseMsg parseDelimitedFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseDelimitedWithIOException(PARSER, input);
  }
  public static ResponseMsg parseDelimitedFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseDelimitedWithIOException(PARSER, input, extensionRegistry);
  }
  public static ResponseMsg parseFrom(
      com.google.protobuf.CodedInputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input);
  }
  public static ResponseMsg parseFrom(
      com.google.protobuf.CodedInputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input, extensionRegistry);
  }

  @Override
  public Builder newBuilderForType() { return newBuilder(); }
  public static Builder newBuilder() {
    return DEFAULT_INSTANCE.toBuilder();
  }
  public static Builder newBuilder(ResponseMsg prototype) {
    return DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
  }
  @Override
  public Builder toBuilder() {
    return this == DEFAULT_INSTANCE
        ? new Builder() : new Builder().mergeFrom(this);
  }

  @Override
  protected Builder newBuilderForType(
      BuilderParent parent) {
    Builder builder = new Builder(parent);
    return builder;
  }
  /**
   * Protobuf type {@code edge.ResponseMsg}
   */
  public static final class Builder extends
      com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements
      // @@protoc_insertion_point(builder_implements:edge.ResponseMsg)
      ResponseMsgOrBuilder {
    public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
      return EdgeProtos.internal_static_edge_ResponseMsg_descriptor;
    }

    @Override
    protected FieldAccessorTable
        internalGetFieldAccessorTable() {
      return EdgeProtos.internal_static_edge_ResponseMsg_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              ResponseMsg.class, Builder.class);
    }

    // Construct using ResponseMsg.newBuilder()
    private Builder() {
      maybeForceBuilderInitialization();
    }

    private Builder(
        BuilderParent parent) {
      super(parent);
      maybeForceBuilderInitialization();
    }
    private void maybeForceBuilderInitialization() {
      if (com.google.protobuf.GeneratedMessageV3
              .alwaysUseFieldBuilders) {
      }
    }
    @Override
    public Builder clear() {
      super.clear();
      if (connectResponseMsgBuilder_ == null) {
        connectResponseMsg_ = null;
      } else {
        connectResponseMsg_ = null;
        connectResponseMsgBuilder_ = null;
      }
      if (uplinkResponseMsgBuilder_ == null) {
        uplinkResponseMsg_ = null;
      } else {
        uplinkResponseMsg_ = null;
        uplinkResponseMsgBuilder_ = null;
      }
      if (downlinkMsgBuilder_ == null) {
        downlinkMsg_ = null;
      } else {
        downlinkMsg_ = null;
        downlinkMsgBuilder_ = null;
      }
      if (edgeUpdateMsgBuilder_ == null) {
        edgeUpdateMsg_ = null;
      } else {
        edgeUpdateMsg_ = null;
        edgeUpdateMsgBuilder_ = null;
      }
      return this;
    }

    @Override
    public com.google.protobuf.Descriptors.Descriptor
        getDescriptorForType() {
      return EdgeProtos.internal_static_edge_ResponseMsg_descriptor;
    }

    @Override
    public ResponseMsg getDefaultInstanceForType() {
      return ResponseMsg.getDefaultInstance();
    }

    @Override
    public ResponseMsg build() {
      ResponseMsg result = buildPartial();
      if (!result.isInitialized()) {
        throw newUninitializedMessageException(result);
      }
      return result;
    }

    @Override
    public ResponseMsg buildPartial() {
      ResponseMsg result = new ResponseMsg(this);
      if (connectResponseMsgBuilder_ == null) {
        result.connectResponseMsg_ = connectResponseMsg_;
      } else {
        result.connectResponseMsg_ = connectResponseMsgBuilder_.build();
      }
      if (uplinkResponseMsgBuilder_ == null) {
        result.uplinkResponseMsg_ = uplinkResponseMsg_;
      } else {
        result.uplinkResponseMsg_ = uplinkResponseMsgBuilder_.build();
      }
      if (downlinkMsgBuilder_ == null) {
        result.downlinkMsg_ = downlinkMsg_;
      } else {
        result.downlinkMsg_ = downlinkMsgBuilder_.build();
      }
      if (edgeUpdateMsgBuilder_ == null) {
        result.edgeUpdateMsg_ = edgeUpdateMsg_;
      } else {
        result.edgeUpdateMsg_ = edgeUpdateMsgBuilder_.build();
      }
      onBuilt();
      return result;
    }

    @Override
    public Builder clone() {
      return super.clone();
    }
    @Override
    public Builder setField(
        com.google.protobuf.Descriptors.FieldDescriptor field,
        Object value) {
      return super.setField(field, value);
    }
    @Override
    public Builder clearField(
        com.google.protobuf.Descriptors.FieldDescriptor field) {
      return super.clearField(field);
    }
    @Override
    public Builder clearOneof(
        com.google.protobuf.Descriptors.OneofDescriptor oneof) {
      return super.clearOneof(oneof);
    }
    @Override
    public Builder setRepeatedField(
        com.google.protobuf.Descriptors.FieldDescriptor field,
        int index, Object value) {
      return super.setRepeatedField(field, index, value);
    }
    @Override
    public Builder addRepeatedField(
        com.google.protobuf.Descriptors.FieldDescriptor field,
        Object value) {
      return super.addRepeatedField(field, value);
    }
    @Override
    public Builder mergeFrom(com.google.protobuf.Message other) {
      if (other instanceof ResponseMsg) {
        return mergeFrom((ResponseMsg)other);
      } else {
        super.mergeFrom(other);
        return this;
      }
    }

    public Builder mergeFrom(ResponseMsg other) {
      if (other == ResponseMsg.getDefaultInstance()) return this;
      if (other.hasConnectResponseMsg()) {
        mergeConnectResponseMsg(other.getConnectResponseMsg());
      }
      if (other.hasUplinkResponseMsg()) {
        mergeUplinkResponseMsg(other.getUplinkResponseMsg());
      }
      if (other.hasDownlinkMsg()) {
        mergeDownlinkMsg(other.getDownlinkMsg());
      }
      if (other.hasEdgeUpdateMsg()) {
        mergeEdgeUpdateMsg(other.getEdgeUpdateMsg());
      }
      this.mergeUnknownFields(other.unknownFields);
      onChanged();
      return this;
    }

    @Override
    public final boolean isInitialized() {
      return true;
    }

    @Override
    public Builder mergeFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      ResponseMsg parsedMessage = null;
      try {
        parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
      } catch (com.google.protobuf.InvalidProtocolBufferException e) {
        parsedMessage = (ResponseMsg) e.getUnfinishedMessage();
        throw e.unwrapIOException();
      } finally {
        if (parsedMessage != null) {
          mergeFrom(parsedMessage);
        }
      }
      return this;
    }

    private ConnectResponseMsg connectResponseMsg_;
    private com.google.protobuf.SingleFieldBuilderV3<
        ConnectResponseMsg, ConnectResponseMsg.Builder, ConnectResponseMsgOrBuilder> connectResponseMsgBuilder_;
    /**
     * <code>.edge.ConnectResponseMsg connectResponseMsg = 1;</code>
     * @return Whether the connectResponseMsg field is set.
     */
    public boolean hasConnectResponseMsg() {
      return connectResponseMsgBuilder_ != null || connectResponseMsg_ != null;
    }
    /**
     * <code>.edge.ConnectResponseMsg connectResponseMsg = 1;</code>
     * @return The connectResponseMsg.
     */
    public ConnectResponseMsg getConnectResponseMsg() {
      if (connectResponseMsgBuilder_ == null) {
        return connectResponseMsg_ == null ? ConnectResponseMsg.getDefaultInstance() : connectResponseMsg_;
      } else {
        return connectResponseMsgBuilder_.getMessage();
      }
    }
    /**
     * <code>.edge.ConnectResponseMsg connectResponseMsg = 1;</code>
     */
    public Builder setConnectResponseMsg(ConnectResponseMsg value) {
      if (connectResponseMsgBuilder_ == null) {
        if (value == null) {
          throw new NullPointerException();
        }
        connectResponseMsg_ = value;
        onChanged();
      } else {
        connectResponseMsgBuilder_.setMessage(value);
      }

      return this;
    }
    /**
     * <code>.edge.ConnectResponseMsg connectResponseMsg = 1;</code>
     */
    public Builder setConnectResponseMsg(
        ConnectResponseMsg.Builder builderForValue) {
      if (connectResponseMsgBuilder_ == null) {
        connectResponseMsg_ = builderForValue.build();
        onChanged();
      } else {
        connectResponseMsgBuilder_.setMessage(builderForValue.build());
      }

      return this;
    }
    /**
     * <code>.edge.ConnectResponseMsg connectResponseMsg = 1;</code>
     */
    public Builder mergeConnectResponseMsg(ConnectResponseMsg value) {
      if (connectResponseMsgBuilder_ == null) {
        if (connectResponseMsg_ != null) {
          connectResponseMsg_ =
            ConnectResponseMsg.newBuilder(connectResponseMsg_).mergeFrom(value).buildPartial();
        } else {
          connectResponseMsg_ = value;
        }
        onChanged();
      } else {
        connectResponseMsgBuilder_.mergeFrom(value);
      }

      return this;
    }
    /**
     * <code>.edge.ConnectResponseMsg connectResponseMsg = 1;</code>
     */
    public Builder clearConnectResponseMsg() {
      if (connectResponseMsgBuilder_ == null) {
        connectResponseMsg_ = null;
        onChanged();
      } else {
        connectResponseMsg_ = null;
        connectResponseMsgBuilder_ = null;
      }

      return this;
    }
    /**
     * <code>.edge.ConnectResponseMsg connectResponseMsg = 1;</code>
     */
    public ConnectResponseMsg.Builder getConnectResponseMsgBuilder() {

      onChanged();
      return getConnectResponseMsgFieldBuilder().getBuilder();
    }
    /**
     * <code>.edge.ConnectResponseMsg connectResponseMsg = 1;</code>
     */
    public ConnectResponseMsgOrBuilder getConnectResponseMsgOrBuilder() {
      if (connectResponseMsgBuilder_ != null) {
        return connectResponseMsgBuilder_.getMessageOrBuilder();
      } else {
        return connectResponseMsg_ == null ?
            ConnectResponseMsg.getDefaultInstance() : connectResponseMsg_;
      }
    }
    /**
     * <code>.edge.ConnectResponseMsg connectResponseMsg = 1;</code>
     */
    private com.google.protobuf.SingleFieldBuilderV3<
        ConnectResponseMsg, ConnectResponseMsg.Builder, ConnectResponseMsgOrBuilder>
        getConnectResponseMsgFieldBuilder() {
      if (connectResponseMsgBuilder_ == null) {
        connectResponseMsgBuilder_ = new com.google.protobuf.SingleFieldBuilderV3<
            ConnectResponseMsg, ConnectResponseMsg.Builder, ConnectResponseMsgOrBuilder>(
                getConnectResponseMsg(),
                getParentForChildren(),
                isClean());
        connectResponseMsg_ = null;
      }
      return connectResponseMsgBuilder_;
    }

    private UplinkResponseMsg uplinkResponseMsg_;
    private com.google.protobuf.SingleFieldBuilderV3<
        UplinkResponseMsg, UplinkResponseMsg.Builder, UplinkResponseMsgOrBuilder> uplinkResponseMsgBuilder_;
    /**
     * <code>.edge.UplinkResponseMsg uplinkResponseMsg = 2;</code>
     * @return Whether the uplinkResponseMsg field is set.
     */
    public boolean hasUplinkResponseMsg() {
      return uplinkResponseMsgBuilder_ != null || uplinkResponseMsg_ != null;
    }
    /**
     * <code>.edge.UplinkResponseMsg uplinkResponseMsg = 2;</code>
     * @return The uplinkResponseMsg.
     */
    public UplinkResponseMsg getUplinkResponseMsg() {
      if (uplinkResponseMsgBuilder_ == null) {
        return uplinkResponseMsg_ == null ? UplinkResponseMsg.getDefaultInstance() : uplinkResponseMsg_;
      } else {
        return uplinkResponseMsgBuilder_.getMessage();
      }
    }
    /**
     * <code>.edge.UplinkResponseMsg uplinkResponseMsg = 2;</code>
     */
    public Builder setUplinkResponseMsg(UplinkResponseMsg value) {
      if (uplinkResponseMsgBuilder_ == null) {
        if (value == null) {
          throw new NullPointerException();
        }
        uplinkResponseMsg_ = value;
        onChanged();
      } else {
        uplinkResponseMsgBuilder_.setMessage(value);
      }

      return this;
    }
    /**
     * <code>.edge.UplinkResponseMsg uplinkResponseMsg = 2;</code>
     */
    public Builder setUplinkResponseMsg(
        UplinkResponseMsg.Builder builderForValue) {
      if (uplinkResponseMsgBuilder_ == null) {
        uplinkResponseMsg_ = builderForValue.build();
        onChanged();
      } else {
        uplinkResponseMsgBuilder_.setMessage(builderForValue.build());
      }

      return this;
    }
    /**
     * <code>.edge.UplinkResponseMsg uplinkResponseMsg = 2;</code>
     */
    public Builder mergeUplinkResponseMsg(UplinkResponseMsg value) {
      if (uplinkResponseMsgBuilder_ == null) {
        if (uplinkResponseMsg_ != null) {
          uplinkResponseMsg_ =
            UplinkResponseMsg.newBuilder(uplinkResponseMsg_).mergeFrom(value).buildPartial();
        } else {
          uplinkResponseMsg_ = value;
        }
        onChanged();
      } else {
        uplinkResponseMsgBuilder_.mergeFrom(value);
      }

      return this;
    }
    /**
     * <code>.edge.UplinkResponseMsg uplinkResponseMsg = 2;</code>
     */
    public Builder clearUplinkResponseMsg() {
      if (uplinkResponseMsgBuilder_ == null) {
        uplinkResponseMsg_ = null;
        onChanged();
      } else {
        uplinkResponseMsg_ = null;
        uplinkResponseMsgBuilder_ = null;
      }

      return this;
    }
    /**
     * <code>.edge.UplinkResponseMsg uplinkResponseMsg = 2;</code>
     */
    public UplinkResponseMsg.Builder getUplinkResponseMsgBuilder() {

      onChanged();
      return getUplinkResponseMsgFieldBuilder().getBuilder();
    }
    /**
     * <code>.edge.UplinkResponseMsg uplinkResponseMsg = 2;</code>
     */
    public UplinkResponseMsgOrBuilder getUplinkResponseMsgOrBuilder() {
      if (uplinkResponseMsgBuilder_ != null) {
        return uplinkResponseMsgBuilder_.getMessageOrBuilder();
      } else {
        return uplinkResponseMsg_ == null ?
            UplinkResponseMsg.getDefaultInstance() : uplinkResponseMsg_;
      }
    }
    /**
     * <code>.edge.UplinkResponseMsg uplinkResponseMsg = 2;</code>
     */
    private com.google.protobuf.SingleFieldBuilderV3<
        UplinkResponseMsg, UplinkResponseMsg.Builder, UplinkResponseMsgOrBuilder>
        getUplinkResponseMsgFieldBuilder() {
      if (uplinkResponseMsgBuilder_ == null) {
        uplinkResponseMsgBuilder_ = new com.google.protobuf.SingleFieldBuilderV3<
            UplinkResponseMsg, UplinkResponseMsg.Builder, UplinkResponseMsgOrBuilder>(
                getUplinkResponseMsg(),
                getParentForChildren(),
                isClean());
        uplinkResponseMsg_ = null;
      }
      return uplinkResponseMsgBuilder_;
    }

    private DownlinkMsg downlinkMsg_;
    private com.google.protobuf.SingleFieldBuilderV3<
        DownlinkMsg, DownlinkMsg.Builder, DownlinkMsgOrBuilder> downlinkMsgBuilder_;
    /**
     * <code>.edge.DownlinkMsg downlinkMsg = 3;</code>
     * @return Whether the downlinkMsg field is set.
     */
    public boolean hasDownlinkMsg() {
      return downlinkMsgBuilder_ != null || downlinkMsg_ != null;
    }
    /**
     * <code>.edge.DownlinkMsg downlinkMsg = 3;</code>
     * @return The downlinkMsg.
     */
    public DownlinkMsg getDownlinkMsg() {
      if (downlinkMsgBuilder_ == null) {
        return downlinkMsg_ == null ? DownlinkMsg.getDefaultInstance() : downlinkMsg_;
      } else {
        return downlinkMsgBuilder_.getMessage();
      }
    }
    /**
     * <code>.edge.DownlinkMsg downlinkMsg = 3;</code>
     */
    public Builder setDownlinkMsg(DownlinkMsg value) {
      if (downlinkMsgBuilder_ == null) {
        if (value == null) {
          throw new NullPointerException();
        }
        downlinkMsg_ = value;
        onChanged();
      } else {
        downlinkMsgBuilder_.setMessage(value);
      }

      return this;
    }
    /**
     * <code>.edge.DownlinkMsg downlinkMsg = 3;</code>
     */
    public Builder setDownlinkMsg(
        DownlinkMsg.Builder builderForValue) {
      if (downlinkMsgBuilder_ == null) {
        downlinkMsg_ = builderForValue.build();
        onChanged();
      } else {
        downlinkMsgBuilder_.setMessage(builderForValue.build());
      }

      return this;
    }
    /**
     * <code>.edge.DownlinkMsg downlinkMsg = 3;</code>
     */
    public Builder mergeDownlinkMsg(DownlinkMsg value) {
      if (downlinkMsgBuilder_ == null) {
        if (downlinkMsg_ != null) {
          downlinkMsg_ =
            DownlinkMsg.newBuilder(downlinkMsg_).mergeFrom(value).buildPartial();
        } else {
          downlinkMsg_ = value;
        }
        onChanged();
      } else {
        downlinkMsgBuilder_.mergeFrom(value);
      }

      return this;
    }
    /**
     * <code>.edge.DownlinkMsg downlinkMsg = 3;</code>
     */
    public Builder clearDownlinkMsg() {
      if (downlinkMsgBuilder_ == null) {
        downlinkMsg_ = null;
        onChanged();
      } else {
        downlinkMsg_ = null;
        downlinkMsgBuilder_ = null;
      }

      return this;
    }
    /**
     * <code>.edge.DownlinkMsg downlinkMsg = 3;</code>
     */
    public DownlinkMsg.Builder getDownlinkMsgBuilder() {

      onChanged();
      return getDownlinkMsgFieldBuilder().getBuilder();
    }
    /**
     * <code>.edge.DownlinkMsg downlinkMsg = 3;</code>
     */
    public DownlinkMsgOrBuilder getDownlinkMsgOrBuilder() {
      if (downlinkMsgBuilder_ != null) {
        return downlinkMsgBuilder_.getMessageOrBuilder();
      } else {
        return downlinkMsg_ == null ?
            DownlinkMsg.getDefaultInstance() : downlinkMsg_;
      }
    }
    /**
     * <code>.edge.DownlinkMsg downlinkMsg = 3;</code>
     */
    private com.google.protobuf.SingleFieldBuilderV3<
        DownlinkMsg, DownlinkMsg.Builder, DownlinkMsgOrBuilder>
        getDownlinkMsgFieldBuilder() {
      if (downlinkMsgBuilder_ == null) {
        downlinkMsgBuilder_ = new com.google.protobuf.SingleFieldBuilderV3<
            DownlinkMsg, DownlinkMsg.Builder, DownlinkMsgOrBuilder>(
                getDownlinkMsg(),
                getParentForChildren(),
                isClean());
        downlinkMsg_ = null;
      }
      return downlinkMsgBuilder_;
    }

    private EdgeUpdateMsg edgeUpdateMsg_;
    private com.google.protobuf.SingleFieldBuilderV3<
        EdgeUpdateMsg, EdgeUpdateMsg.Builder, EdgeUpdateMsgOrBuilder> edgeUpdateMsgBuilder_;
    /**
     * <code>.edge.EdgeUpdateMsg edgeUpdateMsg = 4;</code>
     * @return Whether the edgeUpdateMsg field is set.
     */
    public boolean hasEdgeUpdateMsg() {
      return edgeUpdateMsgBuilder_ != null || edgeUpdateMsg_ != null;
    }
    /**
     * <code>.edge.EdgeUpdateMsg edgeUpdateMsg = 4;</code>
     * @return The edgeUpdateMsg.
     */
    public EdgeUpdateMsg getEdgeUpdateMsg() {
      if (edgeUpdateMsgBuilder_ == null) {
        return edgeUpdateMsg_ == null ? EdgeUpdateMsg.getDefaultInstance() : edgeUpdateMsg_;
      } else {
        return edgeUpdateMsgBuilder_.getMessage();
      }
    }
    /**
     * <code>.edge.EdgeUpdateMsg edgeUpdateMsg = 4;</code>
     */
    public Builder setEdgeUpdateMsg(EdgeUpdateMsg value) {
      if (edgeUpdateMsgBuilder_ == null) {
        if (value == null) {
          throw new NullPointerException();
        }
        edgeUpdateMsg_ = value;
        onChanged();
      } else {
        edgeUpdateMsgBuilder_.setMessage(value);
      }

      return this;
    }
    /**
     * <code>.edge.EdgeUpdateMsg edgeUpdateMsg = 4;</code>
     */
    public Builder setEdgeUpdateMsg(
        EdgeUpdateMsg.Builder builderForValue) {
      if (edgeUpdateMsgBuilder_ == null) {
        edgeUpdateMsg_ = builderForValue.build();
        onChanged();
      } else {
        edgeUpdateMsgBuilder_.setMessage(builderForValue.build());
      }

      return this;
    }
    /**
     * <code>.edge.EdgeUpdateMsg edgeUpdateMsg = 4;</code>
     */
    public Builder mergeEdgeUpdateMsg(EdgeUpdateMsg value) {
      if (edgeUpdateMsgBuilder_ == null) {
        if (edgeUpdateMsg_ != null) {
          edgeUpdateMsg_ =
            EdgeUpdateMsg.newBuilder(edgeUpdateMsg_).mergeFrom(value).buildPartial();
        } else {
          edgeUpdateMsg_ = value;
        }
        onChanged();
      } else {
        edgeUpdateMsgBuilder_.mergeFrom(value);
      }

      return this;
    }
    /**
     * <code>.edge.EdgeUpdateMsg edgeUpdateMsg = 4;</code>
     */
    public Builder clearEdgeUpdateMsg() {
      if (edgeUpdateMsgBuilder_ == null) {
        edgeUpdateMsg_ = null;
        onChanged();
      } else {
        edgeUpdateMsg_ = null;
        edgeUpdateMsgBuilder_ = null;
      }

      return this;
    }
    /**
     * <code>.edge.EdgeUpdateMsg edgeUpdateMsg = 4;</code>
     */
    public EdgeUpdateMsg.Builder getEdgeUpdateMsgBuilder() {

      onChanged();
      return getEdgeUpdateMsgFieldBuilder().getBuilder();
    }
    /**
     * <code>.edge.EdgeUpdateMsg edgeUpdateMsg = 4;</code>
     */
    public EdgeUpdateMsgOrBuilder getEdgeUpdateMsgOrBuilder() {
      if (edgeUpdateMsgBuilder_ != null) {
        return edgeUpdateMsgBuilder_.getMessageOrBuilder();
      } else {
        return edgeUpdateMsg_ == null ?
            EdgeUpdateMsg.getDefaultInstance() : edgeUpdateMsg_;
      }
    }
    /**
     * <code>.edge.EdgeUpdateMsg edgeUpdateMsg = 4;</code>
     */
    private com.google.protobuf.SingleFieldBuilderV3<
        EdgeUpdateMsg, EdgeUpdateMsg.Builder, EdgeUpdateMsgOrBuilder>
        getEdgeUpdateMsgFieldBuilder() {
      if (edgeUpdateMsgBuilder_ == null) {
        edgeUpdateMsgBuilder_ = new com.google.protobuf.SingleFieldBuilderV3<
            EdgeUpdateMsg, EdgeUpdateMsg.Builder, EdgeUpdateMsgOrBuilder>(
                getEdgeUpdateMsg(),
                getParentForChildren(),
                isClean());
        edgeUpdateMsg_ = null;
      }
      return edgeUpdateMsgBuilder_;
    }
    @Override
    public final Builder setUnknownFields(
        final com.google.protobuf.UnknownFieldSet unknownFields) {
      return super.setUnknownFields(unknownFields);
    }

    @Override
    public final Builder mergeUnknownFields(
        final com.google.protobuf.UnknownFieldSet unknownFields) {
      return super.mergeUnknownFields(unknownFields);
    }


    // @@protoc_insertion_point(builder_scope:edge.ResponseMsg)
  }

  // @@protoc_insertion_point(class_scope:edge.ResponseMsg)
  private static final ResponseMsg DEFAULT_INSTANCE;
  static {
    DEFAULT_INSTANCE = new ResponseMsg();
  }

  public static ResponseMsg getDefaultInstance() {
    return DEFAULT_INSTANCE;
  }

  private static final com.google.protobuf.Parser<ResponseMsg>
      PARSER = new com.google.protobuf.AbstractParser<ResponseMsg>() {
    @Override
    public ResponseMsg parsePartialFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return new ResponseMsg(input, extensionRegistry);
    }
  };

  public static com.google.protobuf.Parser<ResponseMsg> parser() {
    return PARSER;
  }

  @Override
  public com.google.protobuf.Parser<ResponseMsg> getParserForType() {
    return PARSER;
  }

  @Override
  public ResponseMsg getDefaultInstanceForType() {
    return DEFAULT_INSTANCE;
  }

}

