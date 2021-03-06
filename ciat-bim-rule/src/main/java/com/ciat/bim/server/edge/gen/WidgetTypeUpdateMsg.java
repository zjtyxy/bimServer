// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: edge.proto

package com.ciat.bim.server.edge.gen;

/**
 * Protobuf type {@code edge.WidgetTypeUpdateMsg}
 */
public final class WidgetTypeUpdateMsg extends
    com.google.protobuf.GeneratedMessageV3 implements
    // @@protoc_insertion_point(message_implements:edge.WidgetTypeUpdateMsg)
    WidgetTypeUpdateMsgOrBuilder {
private static final long serialVersionUID = 0L;
  // Use WidgetTypeUpdateMsg.newBuilder() to construct.
  private WidgetTypeUpdateMsg(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
    super(builder);
  }
  private WidgetTypeUpdateMsg() {
    msgType_ = 0;
    bundleAlias_ = "";
    alias_ = "";
    name_ = "";
    descriptorJson_ = "";
  }

  @Override
  @SuppressWarnings({"unused"})
  protected Object newInstance(
      UnusedPrivateParameter unused) {
    return new WidgetTypeUpdateMsg();
  }

  @Override
  public final com.google.protobuf.UnknownFieldSet
  getUnknownFields() {
    return this.unknownFields;
  }
  private WidgetTypeUpdateMsg(
      com.google.protobuf.CodedInputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    this();
    if (extensionRegistry == null) {
      throw new NullPointerException();
    }
    int mutable_bitField0_ = 0;
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
            int rawValue = input.readEnum();

            msgType_ = rawValue;
            break;
          }
          case 16: {

            idMSB_ = input.readInt64();
            break;
          }
          case 24: {

            idLSB_ = input.readInt64();
            break;
          }
          case 34: {
            String s = input.readStringRequireUtf8();
            bitField0_ |= 0x00000001;
            bundleAlias_ = s;
            break;
          }
          case 42: {
            String s = input.readStringRequireUtf8();
            bitField0_ |= 0x00000002;
            alias_ = s;
            break;
          }
          case 50: {
            String s = input.readStringRequireUtf8();
            bitField0_ |= 0x00000004;
            name_ = s;
            break;
          }
          case 58: {
            String s = input.readStringRequireUtf8();
            bitField0_ |= 0x00000008;
            descriptorJson_ = s;
            break;
          }
          case 64: {

            isSystem_ = input.readBool();
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
    return EdgeProtos.internal_static_edge_WidgetTypeUpdateMsg_descriptor;
  }

  @Override
  protected FieldAccessorTable
      internalGetFieldAccessorTable() {
    return EdgeProtos.internal_static_edge_WidgetTypeUpdateMsg_fieldAccessorTable
        .ensureFieldAccessorsInitialized(
            WidgetTypeUpdateMsg.class, Builder.class);
  }

  private int bitField0_;
  public static final int MSGTYPE_FIELD_NUMBER = 1;
  private int msgType_;
  /**
   * <code>.edge.UpdateMsgType msgType = 1;</code>
   * @return The enum numeric value on the wire for msgType.
   */
  @Override public int getMsgTypeValue() {
    return msgType_;
  }
  /**
   * <code>.edge.UpdateMsgType msgType = 1;</code>
   * @return The msgType.
   */
  @Override public UpdateMsgType getMsgType() {
    @SuppressWarnings("deprecation")
    UpdateMsgType result = UpdateMsgType.valueOf(msgType_);
    return result == null ? UpdateMsgType.UNRECOGNIZED : result;
  }

  public static final int IDMSB_FIELD_NUMBER = 2;
  private long idMSB_;
  /**
   * <code>int64 idMSB = 2;</code>
   * @return The idMSB.
   */
  @Override
  public long getIdMSB() {
    return idMSB_;
  }

  public static final int IDLSB_FIELD_NUMBER = 3;
  private long idLSB_;
  /**
   * <code>int64 idLSB = 3;</code>
   * @return The idLSB.
   */
  @Override
  public long getIdLSB() {
    return idLSB_;
  }

  public static final int BUNDLEALIAS_FIELD_NUMBER = 4;
  private volatile Object bundleAlias_;
  /**
   * <code>optional string bundleAlias = 4;</code>
   * @return Whether the bundleAlias field is set.
   */
  @Override
  public boolean hasBundleAlias() {
    return ((bitField0_ & 0x00000001) != 0);
  }
  /**
   * <code>optional string bundleAlias = 4;</code>
   * @return The bundleAlias.
   */
  @Override
  public String getBundleAlias() {
    Object ref = bundleAlias_;
    if (ref instanceof String) {
      return (String) ref;
    } else {
      com.google.protobuf.ByteString bs =
          (com.google.protobuf.ByteString) ref;
      String s = bs.toStringUtf8();
      bundleAlias_ = s;
      return s;
    }
  }
  /**
   * <code>optional string bundleAlias = 4;</code>
   * @return The bytes for bundleAlias.
   */
  @Override
  public com.google.protobuf.ByteString
      getBundleAliasBytes() {
    Object ref = bundleAlias_;
    if (ref instanceof String) {
      com.google.protobuf.ByteString b =
          com.google.protobuf.ByteString.copyFromUtf8(
              (String) ref);
      bundleAlias_ = b;
      return b;
    } else {
      return (com.google.protobuf.ByteString) ref;
    }
  }

  public static final int ALIAS_FIELD_NUMBER = 5;
  private volatile Object alias_;
  /**
   * <code>optional string alias = 5;</code>
   * @return Whether the alias field is set.
   */
  @Override
  public boolean hasAlias() {
    return ((bitField0_ & 0x00000002) != 0);
  }
  /**
   * <code>optional string alias = 5;</code>
   * @return The alias.
   */
  @Override
  public String getAlias() {
    Object ref = alias_;
    if (ref instanceof String) {
      return (String) ref;
    } else {
      com.google.protobuf.ByteString bs =
          (com.google.protobuf.ByteString) ref;
      String s = bs.toStringUtf8();
      alias_ = s;
      return s;
    }
  }
  /**
   * <code>optional string alias = 5;</code>
   * @return The bytes for alias.
   */
  @Override
  public com.google.protobuf.ByteString
      getAliasBytes() {
    Object ref = alias_;
    if (ref instanceof String) {
      com.google.protobuf.ByteString b =
          com.google.protobuf.ByteString.copyFromUtf8(
              (String) ref);
      alias_ = b;
      return b;
    } else {
      return (com.google.protobuf.ByteString) ref;
    }
  }

  public static final int NAME_FIELD_NUMBER = 6;
  private volatile Object name_;
  /**
   * <code>optional string name = 6;</code>
   * @return Whether the name field is set.
   */
  @Override
  public boolean hasName() {
    return ((bitField0_ & 0x00000004) != 0);
  }
  /**
   * <code>optional string name = 6;</code>
   * @return The name.
   */
  @Override
  public String getName() {
    Object ref = name_;
    if (ref instanceof String) {
      return (String) ref;
    } else {
      com.google.protobuf.ByteString bs =
          (com.google.protobuf.ByteString) ref;
      String s = bs.toStringUtf8();
      name_ = s;
      return s;
    }
  }
  /**
   * <code>optional string name = 6;</code>
   * @return The bytes for name.
   */
  @Override
  public com.google.protobuf.ByteString
      getNameBytes() {
    Object ref = name_;
    if (ref instanceof String) {
      com.google.protobuf.ByteString b =
          com.google.protobuf.ByteString.copyFromUtf8(
              (String) ref);
      name_ = b;
      return b;
    } else {
      return (com.google.protobuf.ByteString) ref;
    }
  }

  public static final int DESCRIPTORJSON_FIELD_NUMBER = 7;
  private volatile Object descriptorJson_;
  /**
   * <code>optional string descriptorJson = 7;</code>
   * @return Whether the descriptorJson field is set.
   */
  @Override
  public boolean hasDescriptorJson() {
    return ((bitField0_ & 0x00000008) != 0);
  }
  /**
   * <code>optional string descriptorJson = 7;</code>
   * @return The descriptorJson.
   */
  @Override
  public String getDescriptorJson() {
    Object ref = descriptorJson_;
    if (ref instanceof String) {
      return (String) ref;
    } else {
      com.google.protobuf.ByteString bs =
          (com.google.protobuf.ByteString) ref;
      String s = bs.toStringUtf8();
      descriptorJson_ = s;
      return s;
    }
  }
  /**
   * <code>optional string descriptorJson = 7;</code>
   * @return The bytes for descriptorJson.
   */
  @Override
  public com.google.protobuf.ByteString
      getDescriptorJsonBytes() {
    Object ref = descriptorJson_;
    if (ref instanceof String) {
      com.google.protobuf.ByteString b =
          com.google.protobuf.ByteString.copyFromUtf8(
              (String) ref);
      descriptorJson_ = b;
      return b;
    } else {
      return (com.google.protobuf.ByteString) ref;
    }
  }

  public static final int ISSYSTEM_FIELD_NUMBER = 8;
  private boolean isSystem_;
  /**
   * <code>bool isSystem = 8;</code>
   * @return The isSystem.
   */
  @Override
  public boolean getIsSystem() {
    return isSystem_;
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
    if (msgType_ != UpdateMsgType.ENTITY_CREATED_RPC_MESSAGE.getNumber()) {
      output.writeEnum(1, msgType_);
    }
    if (idMSB_ != 0L) {
      output.writeInt64(2, idMSB_);
    }
    if (idLSB_ != 0L) {
      output.writeInt64(3, idLSB_);
    }
    if (((bitField0_ & 0x00000001) != 0)) {
      com.google.protobuf.GeneratedMessageV3.writeString(output, 4, bundleAlias_);
    }
    if (((bitField0_ & 0x00000002) != 0)) {
      com.google.protobuf.GeneratedMessageV3.writeString(output, 5, alias_);
    }
    if (((bitField0_ & 0x00000004) != 0)) {
      com.google.protobuf.GeneratedMessageV3.writeString(output, 6, name_);
    }
    if (((bitField0_ & 0x00000008) != 0)) {
      com.google.protobuf.GeneratedMessageV3.writeString(output, 7, descriptorJson_);
    }
    if (isSystem_ != false) {
      output.writeBool(8, isSystem_);
    }
    unknownFields.writeTo(output);
  }

  @Override
  public int getSerializedSize() {
    int size = memoizedSize;
    if (size != -1) return size;

    size = 0;
    if (msgType_ != UpdateMsgType.ENTITY_CREATED_RPC_MESSAGE.getNumber()) {
      size += com.google.protobuf.CodedOutputStream
        .computeEnumSize(1, msgType_);
    }
    if (idMSB_ != 0L) {
      size += com.google.protobuf.CodedOutputStream
        .computeInt64Size(2, idMSB_);
    }
    if (idLSB_ != 0L) {
      size += com.google.protobuf.CodedOutputStream
        .computeInt64Size(3, idLSB_);
    }
    if (((bitField0_ & 0x00000001) != 0)) {
      size += com.google.protobuf.GeneratedMessageV3.computeStringSize(4, bundleAlias_);
    }
    if (((bitField0_ & 0x00000002) != 0)) {
      size += com.google.protobuf.GeneratedMessageV3.computeStringSize(5, alias_);
    }
    if (((bitField0_ & 0x00000004) != 0)) {
      size += com.google.protobuf.GeneratedMessageV3.computeStringSize(6, name_);
    }
    if (((bitField0_ & 0x00000008) != 0)) {
      size += com.google.protobuf.GeneratedMessageV3.computeStringSize(7, descriptorJson_);
    }
    if (isSystem_ != false) {
      size += com.google.protobuf.CodedOutputStream
        .computeBoolSize(8, isSystem_);
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
    if (!(obj instanceof WidgetTypeUpdateMsg)) {
      return super.equals(obj);
    }
    WidgetTypeUpdateMsg other = (WidgetTypeUpdateMsg) obj;

    if (msgType_ != other.msgType_) return false;
    if (getIdMSB()
        != other.getIdMSB()) return false;
    if (getIdLSB()
        != other.getIdLSB()) return false;
    if (hasBundleAlias() != other.hasBundleAlias()) return false;
    if (hasBundleAlias()) {
      if (!getBundleAlias()
          .equals(other.getBundleAlias())) return false;
    }
    if (hasAlias() != other.hasAlias()) return false;
    if (hasAlias()) {
      if (!getAlias()
          .equals(other.getAlias())) return false;
    }
    if (hasName() != other.hasName()) return false;
    if (hasName()) {
      if (!getName()
          .equals(other.getName())) return false;
    }
    if (hasDescriptorJson() != other.hasDescriptorJson()) return false;
    if (hasDescriptorJson()) {
      if (!getDescriptorJson()
          .equals(other.getDescriptorJson())) return false;
    }
    if (getIsSystem()
        != other.getIsSystem()) return false;
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
    hash = (37 * hash) + MSGTYPE_FIELD_NUMBER;
    hash = (53 * hash) + msgType_;
    hash = (37 * hash) + IDMSB_FIELD_NUMBER;
    hash = (53 * hash) + com.google.protobuf.Internal.hashLong(
        getIdMSB());
    hash = (37 * hash) + IDLSB_FIELD_NUMBER;
    hash = (53 * hash) + com.google.protobuf.Internal.hashLong(
        getIdLSB());
    if (hasBundleAlias()) {
      hash = (37 * hash) + BUNDLEALIAS_FIELD_NUMBER;
      hash = (53 * hash) + getBundleAlias().hashCode();
    }
    if (hasAlias()) {
      hash = (37 * hash) + ALIAS_FIELD_NUMBER;
      hash = (53 * hash) + getAlias().hashCode();
    }
    if (hasName()) {
      hash = (37 * hash) + NAME_FIELD_NUMBER;
      hash = (53 * hash) + getName().hashCode();
    }
    if (hasDescriptorJson()) {
      hash = (37 * hash) + DESCRIPTORJSON_FIELD_NUMBER;
      hash = (53 * hash) + getDescriptorJson().hashCode();
    }
    hash = (37 * hash) + ISSYSTEM_FIELD_NUMBER;
    hash = (53 * hash) + com.google.protobuf.Internal.hashBoolean(
        getIsSystem());
    hash = (29 * hash) + unknownFields.hashCode();
    memoizedHashCode = hash;
    return hash;
  }

  public static WidgetTypeUpdateMsg parseFrom(
      java.nio.ByteBuffer data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static WidgetTypeUpdateMsg parseFrom(
      java.nio.ByteBuffer data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static WidgetTypeUpdateMsg parseFrom(
      com.google.protobuf.ByteString data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static WidgetTypeUpdateMsg parseFrom(
      com.google.protobuf.ByteString data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static WidgetTypeUpdateMsg parseFrom(byte[] data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static WidgetTypeUpdateMsg parseFrom(
      byte[] data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static WidgetTypeUpdateMsg parseFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input);
  }
  public static WidgetTypeUpdateMsg parseFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input, extensionRegistry);
  }
  public static WidgetTypeUpdateMsg parseDelimitedFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseDelimitedWithIOException(PARSER, input);
  }
  public static WidgetTypeUpdateMsg parseDelimitedFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseDelimitedWithIOException(PARSER, input, extensionRegistry);
  }
  public static WidgetTypeUpdateMsg parseFrom(
      com.google.protobuf.CodedInputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input);
  }
  public static WidgetTypeUpdateMsg parseFrom(
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
  public static Builder newBuilder(WidgetTypeUpdateMsg prototype) {
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
   * Protobuf type {@code edge.WidgetTypeUpdateMsg}
   */
  public static final class Builder extends
      com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements
      // @@protoc_insertion_point(builder_implements:edge.WidgetTypeUpdateMsg)
      WidgetTypeUpdateMsgOrBuilder {
    public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
      return EdgeProtos.internal_static_edge_WidgetTypeUpdateMsg_descriptor;
    }

    @Override
    protected FieldAccessorTable
        internalGetFieldAccessorTable() {
      return EdgeProtos.internal_static_edge_WidgetTypeUpdateMsg_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              WidgetTypeUpdateMsg.class, Builder.class);
    }

    // Construct using WidgetTypeUpdateMsg.newBuilder()
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
      msgType_ = 0;

      idMSB_ = 0L;

      idLSB_ = 0L;

      bundleAlias_ = "";
      bitField0_ = (bitField0_ & ~0x00000001);
      alias_ = "";
      bitField0_ = (bitField0_ & ~0x00000002);
      name_ = "";
      bitField0_ = (bitField0_ & ~0x00000004);
      descriptorJson_ = "";
      bitField0_ = (bitField0_ & ~0x00000008);
      isSystem_ = false;

      return this;
    }

    @Override
    public com.google.protobuf.Descriptors.Descriptor
        getDescriptorForType() {
      return EdgeProtos.internal_static_edge_WidgetTypeUpdateMsg_descriptor;
    }

    @Override
    public WidgetTypeUpdateMsg getDefaultInstanceForType() {
      return WidgetTypeUpdateMsg.getDefaultInstance();
    }

    @Override
    public WidgetTypeUpdateMsg build() {
      WidgetTypeUpdateMsg result = buildPartial();
      if (!result.isInitialized()) {
        throw newUninitializedMessageException(result);
      }
      return result;
    }

    @Override
    public WidgetTypeUpdateMsg buildPartial() {
      WidgetTypeUpdateMsg result = new WidgetTypeUpdateMsg(this);
      int from_bitField0_ = bitField0_;
      int to_bitField0_ = 0;
      result.msgType_ = msgType_;
      result.idMSB_ = idMSB_;
      result.idLSB_ = idLSB_;
      if (((from_bitField0_ & 0x00000001) != 0)) {
        to_bitField0_ |= 0x00000001;
      }
      result.bundleAlias_ = bundleAlias_;
      if (((from_bitField0_ & 0x00000002) != 0)) {
        to_bitField0_ |= 0x00000002;
      }
      result.alias_ = alias_;
      if (((from_bitField0_ & 0x00000004) != 0)) {
        to_bitField0_ |= 0x00000004;
      }
      result.name_ = name_;
      if (((from_bitField0_ & 0x00000008) != 0)) {
        to_bitField0_ |= 0x00000008;
      }
      result.descriptorJson_ = descriptorJson_;
      result.isSystem_ = isSystem_;
      result.bitField0_ = to_bitField0_;
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
      if (other instanceof WidgetTypeUpdateMsg) {
        return mergeFrom((WidgetTypeUpdateMsg)other);
      } else {
        super.mergeFrom(other);
        return this;
      }
    }

    public Builder mergeFrom(WidgetTypeUpdateMsg other) {
      if (other == WidgetTypeUpdateMsg.getDefaultInstance()) return this;
      if (other.msgType_ != 0) {
        setMsgTypeValue(other.getMsgTypeValue());
      }
      if (other.getIdMSB() != 0L) {
        setIdMSB(other.getIdMSB());
      }
      if (other.getIdLSB() != 0L) {
        setIdLSB(other.getIdLSB());
      }
      if (other.hasBundleAlias()) {
        bitField0_ |= 0x00000001;
        bundleAlias_ = other.bundleAlias_;
        onChanged();
      }
      if (other.hasAlias()) {
        bitField0_ |= 0x00000002;
        alias_ = other.alias_;
        onChanged();
      }
      if (other.hasName()) {
        bitField0_ |= 0x00000004;
        name_ = other.name_;
        onChanged();
      }
      if (other.hasDescriptorJson()) {
        bitField0_ |= 0x00000008;
        descriptorJson_ = other.descriptorJson_;
        onChanged();
      }
      if (other.getIsSystem() != false) {
        setIsSystem(other.getIsSystem());
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
      WidgetTypeUpdateMsg parsedMessage = null;
      try {
        parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
      } catch (com.google.protobuf.InvalidProtocolBufferException e) {
        parsedMessage = (WidgetTypeUpdateMsg) e.getUnfinishedMessage();
        throw e.unwrapIOException();
      } finally {
        if (parsedMessage != null) {
          mergeFrom(parsedMessage);
        }
      }
      return this;
    }
    private int bitField0_;

    private int msgType_ = 0;
    /**
     * <code>.edge.UpdateMsgType msgType = 1;</code>
     * @return The enum numeric value on the wire for msgType.
     */
    @Override public int getMsgTypeValue() {
      return msgType_;
    }
    /**
     * <code>.edge.UpdateMsgType msgType = 1;</code>
     * @param value The enum numeric value on the wire for msgType to set.
     * @return This builder for chaining.
     */
    public Builder setMsgTypeValue(int value) {

      msgType_ = value;
      onChanged();
      return this;
    }
    /**
     * <code>.edge.UpdateMsgType msgType = 1;</code>
     * @return The msgType.
     */
    @Override
    public UpdateMsgType getMsgType() {
      @SuppressWarnings("deprecation")
      UpdateMsgType result = UpdateMsgType.valueOf(msgType_);
      return result == null ? UpdateMsgType.UNRECOGNIZED : result;
    }
    /**
     * <code>.edge.UpdateMsgType msgType = 1;</code>
     * @param value The msgType to set.
     * @return This builder for chaining.
     */
    public Builder setMsgType(UpdateMsgType value) {
      if (value == null) {
        throw new NullPointerException();
      }

      msgType_ = value.getNumber();
      onChanged();
      return this;
    }
    /**
     * <code>.edge.UpdateMsgType msgType = 1;</code>
     * @return This builder for chaining.
     */
    public Builder clearMsgType() {

      msgType_ = 0;
      onChanged();
      return this;
    }

    private long idMSB_ ;
    /**
     * <code>int64 idMSB = 2;</code>
     * @return The idMSB.
     */
    @Override
    public long getIdMSB() {
      return idMSB_;
    }
    /**
     * <code>int64 idMSB = 2;</code>
     * @param value The idMSB to set.
     * @return This builder for chaining.
     */
    public Builder setIdMSB(long value) {

      idMSB_ = value;
      onChanged();
      return this;
    }
    /**
     * <code>int64 idMSB = 2;</code>
     * @return This builder for chaining.
     */
    public Builder clearIdMSB() {

      idMSB_ = 0L;
      onChanged();
      return this;
    }

    private long idLSB_ ;
    /**
     * <code>int64 idLSB = 3;</code>
     * @return The idLSB.
     */
    @Override
    public long getIdLSB() {
      return idLSB_;
    }
    /**
     * <code>int64 idLSB = 3;</code>
     * @param value The idLSB to set.
     * @return This builder for chaining.
     */
    public Builder setIdLSB(long value) {

      idLSB_ = value;
      onChanged();
      return this;
    }
    /**
     * <code>int64 idLSB = 3;</code>
     * @return This builder for chaining.
     */
    public Builder clearIdLSB() {

      idLSB_ = 0L;
      onChanged();
      return this;
    }

    private Object bundleAlias_ = "";
    /**
     * <code>optional string bundleAlias = 4;</code>
     * @return Whether the bundleAlias field is set.
     */
    public boolean hasBundleAlias() {
      return ((bitField0_ & 0x00000001) != 0);
    }
    /**
     * <code>optional string bundleAlias = 4;</code>
     * @return The bundleAlias.
     */
    public String getBundleAlias() {
      Object ref = bundleAlias_;
      if (!(ref instanceof String)) {
        com.google.protobuf.ByteString bs =
            (com.google.protobuf.ByteString) ref;
        String s = bs.toStringUtf8();
        bundleAlias_ = s;
        return s;
      } else {
        return (String) ref;
      }
    }
    /**
     * <code>optional string bundleAlias = 4;</code>
     * @return The bytes for bundleAlias.
     */
    public com.google.protobuf.ByteString
        getBundleAliasBytes() {
      Object ref = bundleAlias_;
      if (ref instanceof String) {
        com.google.protobuf.ByteString b =
            com.google.protobuf.ByteString.copyFromUtf8(
                (String) ref);
        bundleAlias_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }
    /**
     * <code>optional string bundleAlias = 4;</code>
     * @param value The bundleAlias to set.
     * @return This builder for chaining.
     */
    public Builder setBundleAlias(
        String value) {
      if (value == null) {
    throw new NullPointerException();
  }
  bitField0_ |= 0x00000001;
      bundleAlias_ = value;
      onChanged();
      return this;
    }
    /**
     * <code>optional string bundleAlias = 4;</code>
     * @return This builder for chaining.
     */
    public Builder clearBundleAlias() {
      bitField0_ = (bitField0_ & ~0x00000001);
      bundleAlias_ = getDefaultInstance().getBundleAlias();
      onChanged();
      return this;
    }
    /**
     * <code>optional string bundleAlias = 4;</code>
     * @param value The bytes for bundleAlias to set.
     * @return This builder for chaining.
     */
    public Builder setBundleAliasBytes(
        com.google.protobuf.ByteString value) {
      if (value == null) {
    throw new NullPointerException();
  }
  checkByteStringIsUtf8(value);
      bitField0_ |= 0x00000001;
      bundleAlias_ = value;
      onChanged();
      return this;
    }

    private Object alias_ = "";
    /**
     * <code>optional string alias = 5;</code>
     * @return Whether the alias field is set.
     */
    public boolean hasAlias() {
      return ((bitField0_ & 0x00000002) != 0);
    }
    /**
     * <code>optional string alias = 5;</code>
     * @return The alias.
     */
    public String getAlias() {
      Object ref = alias_;
      if (!(ref instanceof String)) {
        com.google.protobuf.ByteString bs =
            (com.google.protobuf.ByteString) ref;
        String s = bs.toStringUtf8();
        alias_ = s;
        return s;
      } else {
        return (String) ref;
      }
    }
    /**
     * <code>optional string alias = 5;</code>
     * @return The bytes for alias.
     */
    public com.google.protobuf.ByteString
        getAliasBytes() {
      Object ref = alias_;
      if (ref instanceof String) {
        com.google.protobuf.ByteString b =
            com.google.protobuf.ByteString.copyFromUtf8(
                (String) ref);
        alias_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }
    /**
     * <code>optional string alias = 5;</code>
     * @param value The alias to set.
     * @return This builder for chaining.
     */
    public Builder setAlias(
        String value) {
      if (value == null) {
    throw new NullPointerException();
  }
  bitField0_ |= 0x00000002;
      alias_ = value;
      onChanged();
      return this;
    }
    /**
     * <code>optional string alias = 5;</code>
     * @return This builder for chaining.
     */
    public Builder clearAlias() {
      bitField0_ = (bitField0_ & ~0x00000002);
      alias_ = getDefaultInstance().getAlias();
      onChanged();
      return this;
    }
    /**
     * <code>optional string alias = 5;</code>
     * @param value The bytes for alias to set.
     * @return This builder for chaining.
     */
    public Builder setAliasBytes(
        com.google.protobuf.ByteString value) {
      if (value == null) {
    throw new NullPointerException();
  }
  checkByteStringIsUtf8(value);
      bitField0_ |= 0x00000002;
      alias_ = value;
      onChanged();
      return this;
    }

    private Object name_ = "";
    /**
     * <code>optional string name = 6;</code>
     * @return Whether the name field is set.
     */
    public boolean hasName() {
      return ((bitField0_ & 0x00000004) != 0);
    }
    /**
     * <code>optional string name = 6;</code>
     * @return The name.
     */
    public String getName() {
      Object ref = name_;
      if (!(ref instanceof String)) {
        com.google.protobuf.ByteString bs =
            (com.google.protobuf.ByteString) ref;
        String s = bs.toStringUtf8();
        name_ = s;
        return s;
      } else {
        return (String) ref;
      }
    }
    /**
     * <code>optional string name = 6;</code>
     * @return The bytes for name.
     */
    public com.google.protobuf.ByteString
        getNameBytes() {
      Object ref = name_;
      if (ref instanceof String) {
        com.google.protobuf.ByteString b =
            com.google.protobuf.ByteString.copyFromUtf8(
                (String) ref);
        name_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }
    /**
     * <code>optional string name = 6;</code>
     * @param value The name to set.
     * @return This builder for chaining.
     */
    public Builder setName(
        String value) {
      if (value == null) {
    throw new NullPointerException();
  }
  bitField0_ |= 0x00000004;
      name_ = value;
      onChanged();
      return this;
    }
    /**
     * <code>optional string name = 6;</code>
     * @return This builder for chaining.
     */
    public Builder clearName() {
      bitField0_ = (bitField0_ & ~0x00000004);
      name_ = getDefaultInstance().getName();
      onChanged();
      return this;
    }
    /**
     * <code>optional string name = 6;</code>
     * @param value The bytes for name to set.
     * @return This builder for chaining.
     */
    public Builder setNameBytes(
        com.google.protobuf.ByteString value) {
      if (value == null) {
    throw new NullPointerException();
  }
  checkByteStringIsUtf8(value);
      bitField0_ |= 0x00000004;
      name_ = value;
      onChanged();
      return this;
    }

    private Object descriptorJson_ = "";
    /**
     * <code>optional string descriptorJson = 7;</code>
     * @return Whether the descriptorJson field is set.
     */
    public boolean hasDescriptorJson() {
      return ((bitField0_ & 0x00000008) != 0);
    }
    /**
     * <code>optional string descriptorJson = 7;</code>
     * @return The descriptorJson.
     */
    public String getDescriptorJson() {
      Object ref = descriptorJson_;
      if (!(ref instanceof String)) {
        com.google.protobuf.ByteString bs =
            (com.google.protobuf.ByteString) ref;
        String s = bs.toStringUtf8();
        descriptorJson_ = s;
        return s;
      } else {
        return (String) ref;
      }
    }
    /**
     * <code>optional string descriptorJson = 7;</code>
     * @return The bytes for descriptorJson.
     */
    public com.google.protobuf.ByteString
        getDescriptorJsonBytes() {
      Object ref = descriptorJson_;
      if (ref instanceof String) {
        com.google.protobuf.ByteString b =
            com.google.protobuf.ByteString.copyFromUtf8(
                (String) ref);
        descriptorJson_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }
    /**
     * <code>optional string descriptorJson = 7;</code>
     * @param value The descriptorJson to set.
     * @return This builder for chaining.
     */
    public Builder setDescriptorJson(
        String value) {
      if (value == null) {
    throw new NullPointerException();
  }
  bitField0_ |= 0x00000008;
      descriptorJson_ = value;
      onChanged();
      return this;
    }
    /**
     * <code>optional string descriptorJson = 7;</code>
     * @return This builder for chaining.
     */
    public Builder clearDescriptorJson() {
      bitField0_ = (bitField0_ & ~0x00000008);
      descriptorJson_ = getDefaultInstance().getDescriptorJson();
      onChanged();
      return this;
    }
    /**
     * <code>optional string descriptorJson = 7;</code>
     * @param value The bytes for descriptorJson to set.
     * @return This builder for chaining.
     */
    public Builder setDescriptorJsonBytes(
        com.google.protobuf.ByteString value) {
      if (value == null) {
    throw new NullPointerException();
  }
  checkByteStringIsUtf8(value);
      bitField0_ |= 0x00000008;
      descriptorJson_ = value;
      onChanged();
      return this;
    }

    private boolean isSystem_ ;
    /**
     * <code>bool isSystem = 8;</code>
     * @return The isSystem.
     */
    @Override
    public boolean getIsSystem() {
      return isSystem_;
    }
    /**
     * <code>bool isSystem = 8;</code>
     * @param value The isSystem to set.
     * @return This builder for chaining.
     */
    public Builder setIsSystem(boolean value) {

      isSystem_ = value;
      onChanged();
      return this;
    }
    /**
     * <code>bool isSystem = 8;</code>
     * @return This builder for chaining.
     */
    public Builder clearIsSystem() {

      isSystem_ = false;
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


    // @@protoc_insertion_point(builder_scope:edge.WidgetTypeUpdateMsg)
  }

  // @@protoc_insertion_point(class_scope:edge.WidgetTypeUpdateMsg)
  private static final WidgetTypeUpdateMsg DEFAULT_INSTANCE;
  static {
    DEFAULT_INSTANCE = new WidgetTypeUpdateMsg();
  }

  public static WidgetTypeUpdateMsg getDefaultInstance() {
    return DEFAULT_INSTANCE;
  }

  private static final com.google.protobuf.Parser<WidgetTypeUpdateMsg>
      PARSER = new com.google.protobuf.AbstractParser<WidgetTypeUpdateMsg>() {
    @Override
    public WidgetTypeUpdateMsg parsePartialFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return new WidgetTypeUpdateMsg(input, extensionRegistry);
    }
  };

  public static com.google.protobuf.Parser<WidgetTypeUpdateMsg> parser() {
    return PARSER;
  }

  @Override
  public com.google.protobuf.Parser<WidgetTypeUpdateMsg> getParserForType() {
    return PARSER;
  }

  @Override
  public WidgetTypeUpdateMsg getDefaultInstanceForType() {
    return DEFAULT_INSTANCE;
  }

}

