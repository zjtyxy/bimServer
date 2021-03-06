// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: edge.proto

package com.ciat.bim.server.edge.gen;

/**
 * Protobuf type {@code edge.EdgeUpdateMsg}
 */
public final class EdgeUpdateMsg extends
    com.google.protobuf.GeneratedMessageV3 implements
    // @@protoc_insertion_point(message_implements:edge.EdgeUpdateMsg)
        EdgeUpdateMsgOrBuilder {
private static final long serialVersionUID = 0L;
  // Use EdgeUpdateMsg.newBuilder() to construct.
  private EdgeUpdateMsg(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
    super(builder);
  }
  private EdgeUpdateMsg() {
  }

  @Override
  @SuppressWarnings({"unused"})
  protected Object newInstance(
      UnusedPrivateParameter unused) {
    return new EdgeUpdateMsg();
  }

  @Override
  public final com.google.protobuf.UnknownFieldSet
  getUnknownFields() {
    return this.unknownFields;
  }
  private EdgeUpdateMsg(
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
            EdgeConfiguration.Builder subBuilder = null;
            if (configuration_ != null) {
              subBuilder = configuration_.toBuilder();
            }
            configuration_ = input.readMessage(EdgeConfiguration.parser(), extensionRegistry);
            if (subBuilder != null) {
              subBuilder.mergeFrom(configuration_);
              configuration_ = subBuilder.buildPartial();
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
    return EdgeProtos.internal_static_edge_EdgeUpdateMsg_descriptor;
  }

  @Override
  protected FieldAccessorTable
      internalGetFieldAccessorTable() {
    return EdgeProtos.internal_static_edge_EdgeUpdateMsg_fieldAccessorTable
        .ensureFieldAccessorsInitialized(
            EdgeUpdateMsg.class, Builder.class);
  }

  public static final int CONFIGURATION_FIELD_NUMBER = 1;
  private EdgeConfiguration configuration_;
  /**
   * <code>.edge.EdgeConfiguration configuration = 1;</code>
   * @return Whether the configuration field is set.
   */
  @Override
  public boolean hasConfiguration() {
    return configuration_ != null;
  }
  /**
   * <code>.edge.EdgeConfiguration configuration = 1;</code>
   * @return The configuration.
   */
  @Override
  public EdgeConfiguration getConfiguration() {
    return configuration_ == null ? EdgeConfiguration.getDefaultInstance() : configuration_;
  }
  /**
   * <code>.edge.EdgeConfiguration configuration = 1;</code>
   */
  @Override
  public EdgeConfigurationOrBuilder getConfigurationOrBuilder() {
    return getConfiguration();
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
    if (configuration_ != null) {
      output.writeMessage(1, getConfiguration());
    }
    unknownFields.writeTo(output);
  }

  @Override
  public int getSerializedSize() {
    int size = memoizedSize;
    if (size != -1) return size;

    size = 0;
    if (configuration_ != null) {
      size += com.google.protobuf.CodedOutputStream
        .computeMessageSize(1, getConfiguration());
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
    if (!(obj instanceof EdgeUpdateMsg)) {
      return super.equals(obj);
    }
    EdgeUpdateMsg other = (EdgeUpdateMsg) obj;

    if (hasConfiguration() != other.hasConfiguration()) return false;
    if (hasConfiguration()) {
      if (!getConfiguration()
          .equals(other.getConfiguration())) return false;
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
    if (hasConfiguration()) {
      hash = (37 * hash) + CONFIGURATION_FIELD_NUMBER;
      hash = (53 * hash) + getConfiguration().hashCode();
    }
    hash = (29 * hash) + unknownFields.hashCode();
    memoizedHashCode = hash;
    return hash;
  }

  public static EdgeUpdateMsg parseFrom(
      java.nio.ByteBuffer data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static EdgeUpdateMsg parseFrom(
      java.nio.ByteBuffer data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static EdgeUpdateMsg parseFrom(
      com.google.protobuf.ByteString data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static EdgeUpdateMsg parseFrom(
      com.google.protobuf.ByteString data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static EdgeUpdateMsg parseFrom(byte[] data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static EdgeUpdateMsg parseFrom(
      byte[] data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static EdgeUpdateMsg parseFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input);
  }
  public static EdgeUpdateMsg parseFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input, extensionRegistry);
  }
  public static EdgeUpdateMsg parseDelimitedFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseDelimitedWithIOException(PARSER, input);
  }
  public static EdgeUpdateMsg parseDelimitedFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseDelimitedWithIOException(PARSER, input, extensionRegistry);
  }
  public static EdgeUpdateMsg parseFrom(
      com.google.protobuf.CodedInputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input);
  }
  public static EdgeUpdateMsg parseFrom(
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
  public static Builder newBuilder(EdgeUpdateMsg prototype) {
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
   * Protobuf type {@code edge.EdgeUpdateMsg}
   */
  public static final class Builder extends
      com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements
      // @@protoc_insertion_point(builder_implements:edge.EdgeUpdateMsg)
      EdgeUpdateMsgOrBuilder {
    public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
      return EdgeProtos.internal_static_edge_EdgeUpdateMsg_descriptor;
    }

    @Override
    protected FieldAccessorTable
        internalGetFieldAccessorTable() {
      return EdgeProtos.internal_static_edge_EdgeUpdateMsg_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              EdgeUpdateMsg.class, Builder.class);
    }

    // Construct using EdgeUpdateMsg.newBuilder()
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
      if (configurationBuilder_ == null) {
        configuration_ = null;
      } else {
        configuration_ = null;
        configurationBuilder_ = null;
      }
      return this;
    }

    @Override
    public com.google.protobuf.Descriptors.Descriptor
        getDescriptorForType() {
      return EdgeProtos.internal_static_edge_EdgeUpdateMsg_descriptor;
    }

    @Override
    public EdgeUpdateMsg getDefaultInstanceForType() {
      return EdgeUpdateMsg.getDefaultInstance();
    }

    @Override
    public EdgeUpdateMsg build() {
      EdgeUpdateMsg result = buildPartial();
      if (!result.isInitialized()) {
        throw newUninitializedMessageException(result);
      }
      return result;
    }

    @Override
    public EdgeUpdateMsg buildPartial() {
      EdgeUpdateMsg result = new EdgeUpdateMsg(this);
      if (configurationBuilder_ == null) {
        result.configuration_ = configuration_;
      } else {
        result.configuration_ = configurationBuilder_.build();
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
      if (other instanceof EdgeUpdateMsg) {
        return mergeFrom((EdgeUpdateMsg)other);
      } else {
        super.mergeFrom(other);
        return this;
      }
    }

    public Builder mergeFrom(EdgeUpdateMsg other) {
      if (other == EdgeUpdateMsg.getDefaultInstance()) return this;
      if (other.hasConfiguration()) {
        mergeConfiguration(other.getConfiguration());
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
      EdgeUpdateMsg parsedMessage = null;
      try {
        parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
      } catch (com.google.protobuf.InvalidProtocolBufferException e) {
        parsedMessage = (EdgeUpdateMsg) e.getUnfinishedMessage();
        throw e.unwrapIOException();
      } finally {
        if (parsedMessage != null) {
          mergeFrom(parsedMessage);
        }
      }
      return this;
    }

    private EdgeConfiguration configuration_;
    private com.google.protobuf.SingleFieldBuilderV3<
        EdgeConfiguration, EdgeConfiguration.Builder, EdgeConfigurationOrBuilder> configurationBuilder_;
    /**
     * <code>.edge.EdgeConfiguration configuration = 1;</code>
     * @return Whether the configuration field is set.
     */
    public boolean hasConfiguration() {
      return configurationBuilder_ != null || configuration_ != null;
    }
    /**
     * <code>.edge.EdgeConfiguration configuration = 1;</code>
     * @return The configuration.
     */
    public EdgeConfiguration getConfiguration() {
      if (configurationBuilder_ == null) {
        return configuration_ == null ? EdgeConfiguration.getDefaultInstance() : configuration_;
      } else {
        return configurationBuilder_.getMessage();
      }
    }
    /**
     * <code>.edge.EdgeConfiguration configuration = 1;</code>
     */
    public Builder setConfiguration(EdgeConfiguration value) {
      if (configurationBuilder_ == null) {
        if (value == null) {
          throw new NullPointerException();
        }
        configuration_ = value;
        onChanged();
      } else {
        configurationBuilder_.setMessage(value);
      }

      return this;
    }
    /**
     * <code>.edge.EdgeConfiguration configuration = 1;</code>
     */
    public Builder setConfiguration(
        EdgeConfiguration.Builder builderForValue) {
      if (configurationBuilder_ == null) {
        configuration_ = builderForValue.build();
        onChanged();
      } else {
        configurationBuilder_.setMessage(builderForValue.build());
      }

      return this;
    }
    /**
     * <code>.edge.EdgeConfiguration configuration = 1;</code>
     */
    public Builder mergeConfiguration(EdgeConfiguration value) {
      if (configurationBuilder_ == null) {
        if (configuration_ != null) {
          configuration_ =
            EdgeConfiguration.newBuilder(configuration_).mergeFrom(value).buildPartial();
        } else {
          configuration_ = value;
        }
        onChanged();
      } else {
        configurationBuilder_.mergeFrom(value);
      }

      return this;
    }
    /**
     * <code>.edge.EdgeConfiguration configuration = 1;</code>
     */
    public Builder clearConfiguration() {
      if (configurationBuilder_ == null) {
        configuration_ = null;
        onChanged();
      } else {
        configuration_ = null;
        configurationBuilder_ = null;
      }

      return this;
    }
    /**
     * <code>.edge.EdgeConfiguration configuration = 1;</code>
     */
    public EdgeConfiguration.Builder getConfigurationBuilder() {

      onChanged();
      return getConfigurationFieldBuilder().getBuilder();
    }
    /**
     * <code>.edge.EdgeConfiguration configuration = 1;</code>
     */
    public EdgeConfigurationOrBuilder getConfigurationOrBuilder() {
      if (configurationBuilder_ != null) {
        return configurationBuilder_.getMessageOrBuilder();
      } else {
        return configuration_ == null ?
            EdgeConfiguration.getDefaultInstance() : configuration_;
      }
    }
    /**
     * <code>.edge.EdgeConfiguration configuration = 1;</code>
     */
    private com.google.protobuf.SingleFieldBuilderV3<
        EdgeConfiguration, EdgeConfiguration.Builder, EdgeConfigurationOrBuilder>
        getConfigurationFieldBuilder() {
      if (configurationBuilder_ == null) {
        configurationBuilder_ = new com.google.protobuf.SingleFieldBuilderV3<
            EdgeConfiguration, EdgeConfiguration.Builder, EdgeConfigurationOrBuilder>(
                getConfiguration(),
                getParentForChildren(),
                isClean());
        configuration_ = null;
      }
      return configurationBuilder_;
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


    // @@protoc_insertion_point(builder_scope:edge.EdgeUpdateMsg)
  }

  // @@protoc_insertion_point(class_scope:edge.EdgeUpdateMsg)
  private static final EdgeUpdateMsg DEFAULT_INSTANCE;
  static {
    DEFAULT_INSTANCE = new EdgeUpdateMsg();
  }

  public static EdgeUpdateMsg getDefaultInstance() {
    return DEFAULT_INSTANCE;
  }

  private static final com.google.protobuf.Parser<EdgeUpdateMsg>
      PARSER = new com.google.protobuf.AbstractParser<EdgeUpdateMsg>() {
    @Override
    public EdgeUpdateMsg parsePartialFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return new EdgeUpdateMsg(input, extensionRegistry);
    }
  };

  public static com.google.protobuf.Parser<EdgeUpdateMsg> parser() {
    return PARSER;
  }

  @Override
  public com.google.protobuf.Parser<EdgeUpdateMsg> getParserForType() {
    return PARSER;
  }

  @Override
  public EdgeUpdateMsg getDefaultInstanceForType() {
    return DEFAULT_INSTANCE;
  }

}

