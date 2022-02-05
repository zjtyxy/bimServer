// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: edge.proto

package com.ciat.bim.server.edge.gen;

/**
 * Protobuf enum {@code edge.UpdateMsgType}
 */
public enum UpdateMsgType
    implements com.google.protobuf.ProtocolMessageEnum {
  /**
   * <code>ENTITY_CREATED_RPC_MESSAGE = 0;</code>
   */
  ENTITY_CREATED_RPC_MESSAGE(0),
  /**
   * <code>ENTITY_UPDATED_RPC_MESSAGE = 1;</code>
   */
  ENTITY_UPDATED_RPC_MESSAGE(1),
  /**
   * <code>ENTITY_DELETED_RPC_MESSAGE = 2;</code>
   */
  ENTITY_DELETED_RPC_MESSAGE(2),
  /**
   * <code>ALARM_ACK_RPC_MESSAGE = 3;</code>
   */
  ALARM_ACK_RPC_MESSAGE(3),
  /**
   * <code>ALARM_CLEAR_RPC_MESSAGE = 4;</code>
   */
  ALARM_CLEAR_RPC_MESSAGE(4),
  /**
   * <code>ENTITY_MERGE_RPC_MESSAGE = 5;</code>
   */
  ENTITY_MERGE_RPC_MESSAGE(5),
  UNRECOGNIZED(-1),
  ;

  /**
   * <code>ENTITY_CREATED_RPC_MESSAGE = 0;</code>
   */
  public static final int ENTITY_CREATED_RPC_MESSAGE_VALUE = 0;
  /**
   * <code>ENTITY_UPDATED_RPC_MESSAGE = 1;</code>
   */
  public static final int ENTITY_UPDATED_RPC_MESSAGE_VALUE = 1;
  /**
   * <code>ENTITY_DELETED_RPC_MESSAGE = 2;</code>
   */
  public static final int ENTITY_DELETED_RPC_MESSAGE_VALUE = 2;
  /**
   * <code>ALARM_ACK_RPC_MESSAGE = 3;</code>
   */
  public static final int ALARM_ACK_RPC_MESSAGE_VALUE = 3;
  /**
   * <code>ALARM_CLEAR_RPC_MESSAGE = 4;</code>
   */
  public static final int ALARM_CLEAR_RPC_MESSAGE_VALUE = 4;
  /**
   * <code>ENTITY_MERGE_RPC_MESSAGE = 5;</code>
   */
  public static final int ENTITY_MERGE_RPC_MESSAGE_VALUE = 5;


  public final int getNumber() {
    if (this == UNRECOGNIZED) {
      throw new IllegalArgumentException(
          "Can't get the number of an unknown enum value.");
    }
    return value;
  }

  /**
   * @param value The numeric wire value of the corresponding enum entry.
   * @return The enum associated with the given numeric wire value.
   * @deprecated Use {@link #forNumber(int)} instead.
   */
  @Deprecated
  public static UpdateMsgType valueOf(int value) {
    return forNumber(value);
  }

  /**
   * @param value The numeric wire value of the corresponding enum entry.
   * @return The enum associated with the given numeric wire value.
   */
  public static UpdateMsgType forNumber(int value) {
    switch (value) {
      case 0: return ENTITY_CREATED_RPC_MESSAGE;
      case 1: return ENTITY_UPDATED_RPC_MESSAGE;
      case 2: return ENTITY_DELETED_RPC_MESSAGE;
      case 3: return ALARM_ACK_RPC_MESSAGE;
      case 4: return ALARM_CLEAR_RPC_MESSAGE;
      case 5: return ENTITY_MERGE_RPC_MESSAGE;
      default: return null;
    }
  }

  public static com.google.protobuf.Internal.EnumLiteMap<UpdateMsgType>
      internalGetValueMap() {
    return internalValueMap;
  }
  private static final com.google.protobuf.Internal.EnumLiteMap<
      UpdateMsgType> internalValueMap =
        new com.google.protobuf.Internal.EnumLiteMap<UpdateMsgType>() {
          public UpdateMsgType findValueByNumber(int number) {
            return UpdateMsgType.forNumber(number);
          }
        };

  public final com.google.protobuf.Descriptors.EnumValueDescriptor
      getValueDescriptor() {
    if (this == UNRECOGNIZED) {
      throw new IllegalStateException(
          "Can't get the descriptor of an unrecognized enum value.");
    }
    return getDescriptor().getValues().get(ordinal());
  }
  public final com.google.protobuf.Descriptors.EnumDescriptor
      getDescriptorForType() {
    return getDescriptor();
  }
  public static final com.google.protobuf.Descriptors.EnumDescriptor
      getDescriptor() {
    return EdgeProtos.getDescriptor().getEnumTypes().get(2);
  }

  private static final UpdateMsgType[] VALUES = values();

  public static UpdateMsgType valueOf(
      com.google.protobuf.Descriptors.EnumValueDescriptor desc) {
    if (desc.getType() != getDescriptor()) {
      throw new IllegalArgumentException(
        "EnumValueDescriptor is not for this type.");
    }
    if (desc.getIndex() == -1) {
      return UNRECOGNIZED;
    }
    return VALUES[desc.getIndex()];
  }

  private final int value;

  private UpdateMsgType(int value) {
    this.value = value;
  }

  // @@protoc_insertion_point(enum_scope:edge.UpdateMsgType)
}
