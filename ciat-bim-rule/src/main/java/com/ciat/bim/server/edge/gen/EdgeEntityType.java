// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: edge.proto

package com.ciat.bim.server.edge.gen;

/**
 * Protobuf enum {@code edge.EdgeEntityType}
 */
public enum EdgeEntityType
    implements com.google.protobuf.ProtocolMessageEnum {
  /**
   * <code>DEVICE = 0;</code>
   */
  DEVICE(0),
  /**
   * <code>ASSET = 1;</code>
   */
  ASSET(1),
  UNRECOGNIZED(-1),
  ;

  /**
   * <code>DEVICE = 0;</code>
   */
  public static final int DEVICE_VALUE = 0;
  /**
   * <code>ASSET = 1;</code>
   */
  public static final int ASSET_VALUE = 1;


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
  public static EdgeEntityType valueOf(int value) {
    return forNumber(value);
  }

  /**
   * @param value The numeric wire value of the corresponding enum entry.
   * @return The enum associated with the given numeric wire value.
   */
  public static EdgeEntityType forNumber(int value) {
    switch (value) {
      case 0: return DEVICE;
      case 1: return ASSET;
      default: return null;
    }
  }

  public static com.google.protobuf.Internal.EnumLiteMap<EdgeEntityType>
      internalGetValueMap() {
    return internalValueMap;
  }
  private static final com.google.protobuf.Internal.EnumLiteMap<
      EdgeEntityType> internalValueMap =
        new com.google.protobuf.Internal.EnumLiteMap<EdgeEntityType>() {
          public EdgeEntityType findValueByNumber(int number) {
            return EdgeEntityType.forNumber(number);
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
    return EdgeProtos.getDescriptor().getEnumTypes().get(3);
  }

  private static final EdgeEntityType[] VALUES = values();

  public static EdgeEntityType valueOf(
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

  private EdgeEntityType(int value) {
    this.value = value;
  }

  // @@protoc_insertion_point(enum_scope:edge.EdgeEntityType)
}

