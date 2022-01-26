// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: edge.proto

package com.ciat.bim.server.edge.gen;

/**
 * Protobuf type {@code edge.DownlinkResponseMsg}
 */
public final class DownlinkResponseMsg extends
    com.google.protobuf.GeneratedMessageV3 implements
    // @@protoc_insertion_point(message_implements:edge.DownlinkResponseMsg)
    DownlinkResponseMsgOrBuilder {
private static final long serialVersionUID = 0L;
  // Use DownlinkResponseMsg.newBuilder() to construct.
  private DownlinkResponseMsg(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
    super(builder);
  }
  private DownlinkResponseMsg() {
    errorMsg_ = "";
  }

  @Override
  @SuppressWarnings({"unused"})
  protected Object newInstance(
      UnusedPrivateParameter unused) {
    return new DownlinkResponseMsg();
  }

  @Override
  public final com.google.protobuf.UnknownFieldSet
  getUnknownFields() {
    return this.unknownFields;
  }
  private DownlinkResponseMsg(
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
          case 8: {

            success_ = input.readBool();
            break;
          }
          case 18: {
            String s = input.readStringRequireUtf8();

            errorMsg_ = s;
            break;
          }
          case 24: {

            downlinkMsgId_ = input.readInt32();
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
    return EdgeProtos.internal_static_edge_DownlinkResponseMsg_descriptor;
  }

  @Override
  protected FieldAccessorTable
      internalGetFieldAccessorTable() {
    return EdgeProtos.internal_static_edge_DownlinkResponseMsg_fieldAccessorTable
        .ensureFieldAccessorsInitialized(
            DownlinkResponseMsg.class, Builder.class);
  }

  public static final int SUCCESS_FIELD_NUMBER = 1;
  private boolean success_;
  /**
   * <code>bool success = 1;</code>
   * @return The success.
   */
  @Override
  public boolean getSuccess() {
    return success_;
  }

  public static final int ERRORMSG_FIELD_NUMBER = 2;
  private volatile Object errorMsg_;
  /**
   * <code>string errorMsg = 2;</code>
   * @return The errorMsg.
   */
  @Override
  public String getErrorMsg() {
    Object ref = errorMsg_;
    if (ref instanceof String) {
      return (String) ref;
    } else {
      com.google.protobuf.ByteString bs =
          (com.google.protobuf.ByteString) ref;
      String s = bs.toStringUtf8();
      errorMsg_ = s;
      return s;
    }
  }
  /**
   * <code>string errorMsg = 2;</code>
   * @return The bytes for errorMsg.
   */
  @Override
  public com.google.protobuf.ByteString
      getErrorMsgBytes() {
    Object ref = errorMsg_;
    if (ref instanceof String) {
      com.google.protobuf.ByteString b =
          com.google.protobuf.ByteString.copyFromUtf8(
              (String) ref);
      errorMsg_ = b;
      return b;
    } else {
      return (com.google.protobuf.ByteString) ref;
    }
  }

  public static final int DOWNLINKMSGID_FIELD_NUMBER = 3;
  private int downlinkMsgId_;
  /**
   * <code>int32 downlinkMsgId = 3;</code>
   * @return The downlinkMsgId.
   */
  @Override
  public int getDownlinkMsgId() {
    return downlinkMsgId_;
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
    if (success_ != false) {
      output.writeBool(1, success_);
    }
    if (!getErrorMsgBytes().isEmpty()) {
      com.google.protobuf.GeneratedMessageV3.writeString(output, 2, errorMsg_);
    }
    if (downlinkMsgId_ != 0) {
      output.writeInt32(3, downlinkMsgId_);
    }
    unknownFields.writeTo(output);
  }

  @Override
  public int getSerializedSize() {
    int size = memoizedSize;
    if (size != -1) return size;

    size = 0;
    if (success_ != false) {
      size += com.google.protobuf.CodedOutputStream
        .computeBoolSize(1, success_);
    }
    if (!getErrorMsgBytes().isEmpty()) {
      size += com.google.protobuf.GeneratedMessageV3.computeStringSize(2, errorMsg_);
    }
    if (downlinkMsgId_ != 0) {
      size += com.google.protobuf.CodedOutputStream
        .computeInt32Size(3, downlinkMsgId_);
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
    if (!(obj instanceof DownlinkResponseMsg)) {
      return super.equals(obj);
    }
    DownlinkResponseMsg other = (DownlinkResponseMsg) obj;

    if (getSuccess()
        != other.getSuccess()) return false;
    if (!getErrorMsg()
        .equals(other.getErrorMsg())) return false;
    if (getDownlinkMsgId()
        != other.getDownlinkMsgId()) return false;
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
    hash = (37 * hash) + SUCCESS_FIELD_NUMBER;
    hash = (53 * hash) + com.google.protobuf.Internal.hashBoolean(
        getSuccess());
    hash = (37 * hash) + ERRORMSG_FIELD_NUMBER;
    hash = (53 * hash) + getErrorMsg().hashCode();
    hash = (37 * hash) + DOWNLINKMSGID_FIELD_NUMBER;
    hash = (53 * hash) + getDownlinkMsgId();
    hash = (29 * hash) + unknownFields.hashCode();
    memoizedHashCode = hash;
    return hash;
  }

  public static DownlinkResponseMsg parseFrom(
      java.nio.ByteBuffer data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static DownlinkResponseMsg parseFrom(
      java.nio.ByteBuffer data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static DownlinkResponseMsg parseFrom(
      com.google.protobuf.ByteString data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static DownlinkResponseMsg parseFrom(
      com.google.protobuf.ByteString data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static DownlinkResponseMsg parseFrom(byte[] data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static DownlinkResponseMsg parseFrom(
      byte[] data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static DownlinkResponseMsg parseFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input);
  }
  public static DownlinkResponseMsg parseFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input, extensionRegistry);
  }
  public static DownlinkResponseMsg parseDelimitedFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseDelimitedWithIOException(PARSER, input);
  }
  public static DownlinkResponseMsg parseDelimitedFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseDelimitedWithIOException(PARSER, input, extensionRegistry);
  }
  public static DownlinkResponseMsg parseFrom(
      com.google.protobuf.CodedInputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input);
  }
  public static DownlinkResponseMsg parseFrom(
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
  public static Builder newBuilder(DownlinkResponseMsg prototype) {
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
   * Protobuf type {@code edge.DownlinkResponseMsg}
   */
  public static final class Builder extends
      com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements
      // @@protoc_insertion_point(builder_implements:edge.DownlinkResponseMsg)
      DownlinkResponseMsgOrBuilder {
    public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
      return EdgeProtos.internal_static_edge_DownlinkResponseMsg_descriptor;
    }

    @Override
    protected FieldAccessorTable
        internalGetFieldAccessorTable() {
      return EdgeProtos.internal_static_edge_DownlinkResponseMsg_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              DownlinkResponseMsg.class, Builder.class);
    }

    // Construct using DownlinkResponseMsg.newBuilder()
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
      success_ = false;

      errorMsg_ = "";

      downlinkMsgId_ = 0;

      return this;
    }

    @Override
    public com.google.protobuf.Descriptors.Descriptor
        getDescriptorForType() {
      return EdgeProtos.internal_static_edge_DownlinkResponseMsg_descriptor;
    }

    @Override
    public DownlinkResponseMsg getDefaultInstanceForType() {
      return DownlinkResponseMsg.getDefaultInstance();
    }

    @Override
    public DownlinkResponseMsg build() {
      DownlinkResponseMsg result = buildPartial();
      if (!result.isInitialized()) {
        throw newUninitializedMessageException(result);
      }
      return result;
    }

    @Override
    public DownlinkResponseMsg buildPartial() {
      DownlinkResponseMsg result = new DownlinkResponseMsg(this);
      result.success_ = success_;
      result.errorMsg_ = errorMsg_;
      result.downlinkMsgId_ = downlinkMsgId_;
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
      if (other instanceof DownlinkResponseMsg) {
        return mergeFrom((DownlinkResponseMsg)other);
      } else {
        super.mergeFrom(other);
        return this;
      }
    }

    public Builder mergeFrom(DownlinkResponseMsg other) {
      if (other == DownlinkResponseMsg.getDefaultInstance()) return this;
      if (other.getSuccess() != false) {
        setSuccess(other.getSuccess());
      }
      if (!other.getErrorMsg().isEmpty()) {
        errorMsg_ = other.errorMsg_;
        onChanged();
      }
      if (other.getDownlinkMsgId() != 0) {
        setDownlinkMsgId(other.getDownlinkMsgId());
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
      DownlinkResponseMsg parsedMessage = null;
      try {
        parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
      } catch (com.google.protobuf.InvalidProtocolBufferException e) {
        parsedMessage = (DownlinkResponseMsg) e.getUnfinishedMessage();
        throw e.unwrapIOException();
      } finally {
        if (parsedMessage != null) {
          mergeFrom(parsedMessage);
        }
      }
      return this;
    }

    private boolean success_ ;
    /**
     * <code>bool success = 1;</code>
     * @return The success.
     */
    @Override
    public boolean getSuccess() {
      return success_;
    }
    /**
     * <code>bool success = 1;</code>
     * @param value The success to set.
     * @return This builder for chaining.
     */
    public Builder setSuccess(boolean value) {

      success_ = value;
      onChanged();
      return this;
    }
    /**
     * <code>bool success = 1;</code>
     * @return This builder for chaining.
     */
    public Builder clearSuccess() {

      success_ = false;
      onChanged();
      return this;
    }

    private Object errorMsg_ = "";
    /**
     * <code>string errorMsg = 2;</code>
     * @return The errorMsg.
     */
    public String getErrorMsg() {
      Object ref = errorMsg_;
      if (!(ref instanceof String)) {
        com.google.protobuf.ByteString bs =
            (com.google.protobuf.ByteString) ref;
        String s = bs.toStringUtf8();
        errorMsg_ = s;
        return s;
      } else {
        return (String) ref;
      }
    }
    /**
     * <code>string errorMsg = 2;</code>
     * @return The bytes for errorMsg.
     */
    public com.google.protobuf.ByteString
        getErrorMsgBytes() {
      Object ref = errorMsg_;
      if (ref instanceof String) {
        com.google.protobuf.ByteString b =
            com.google.protobuf.ByteString.copyFromUtf8(
                (String) ref);
        errorMsg_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }
    /**
     * <code>string errorMsg = 2;</code>
     * @param value The errorMsg to set.
     * @return This builder for chaining.
     */
    public Builder setErrorMsg(
        String value) {
      if (value == null) {
    throw new NullPointerException();
  }

      errorMsg_ = value;
      onChanged();
      return this;
    }
    /**
     * <code>string errorMsg = 2;</code>
     * @return This builder for chaining.
     */
    public Builder clearErrorMsg() {

      errorMsg_ = getDefaultInstance().getErrorMsg();
      onChanged();
      return this;
    }
    /**
     * <code>string errorMsg = 2;</code>
     * @param value The bytes for errorMsg to set.
     * @return This builder for chaining.
     */
    public Builder setErrorMsgBytes(
        com.google.protobuf.ByteString value) {
      if (value == null) {
    throw new NullPointerException();
  }
  checkByteStringIsUtf8(value);

      errorMsg_ = value;
      onChanged();
      return this;
    }

    private int downlinkMsgId_ ;
    /**
     * <code>int32 downlinkMsgId = 3;</code>
     * @return The downlinkMsgId.
     */
    @Override
    public int getDownlinkMsgId() {
      return downlinkMsgId_;
    }
    /**
     * <code>int32 downlinkMsgId = 3;</code>
     * @param value The downlinkMsgId to set.
     * @return This builder for chaining.
     */
    public Builder setDownlinkMsgId(int value) {

      downlinkMsgId_ = value;
      onChanged();
      return this;
    }
    /**
     * <code>int32 downlinkMsgId = 3;</code>
     * @return This builder for chaining.
     */
    public Builder clearDownlinkMsgId() {

      downlinkMsgId_ = 0;
      onChanged();
      return this;
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


    // @@protoc_insertion_point(builder_scope:edge.DownlinkResponseMsg)
  }

  // @@protoc_insertion_point(class_scope:edge.DownlinkResponseMsg)
  private static final DownlinkResponseMsg DEFAULT_INSTANCE;
  static {
    DEFAULT_INSTANCE = new DownlinkResponseMsg();
  }

  public static DownlinkResponseMsg getDefaultInstance() {
    return DEFAULT_INSTANCE;
  }

  private static final com.google.protobuf.Parser<DownlinkResponseMsg>
      PARSER = new com.google.protobuf.AbstractParser<DownlinkResponseMsg>() {
    @Override
    public DownlinkResponseMsg parsePartialFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return new DownlinkResponseMsg(input, extensionRegistry);
    }
  };

  public static com.google.protobuf.Parser<DownlinkResponseMsg> parser() {
    return PARSER;
  }

  @Override
  public com.google.protobuf.Parser<DownlinkResponseMsg> getParserForType() {
    return PARSER;
  }

  @Override
  public DownlinkResponseMsg getDefaultInstanceForType() {
    return DEFAULT_INSTANCE;
  }

}

