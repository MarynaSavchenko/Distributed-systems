// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: hashmap.proto

package pl.edu.agh.dsrg.sr.protos;

public final class HashMapMessageProtos {
  private HashMapMessageProtos() {}
  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistry registry) {
  }
  public interface HashMapMessageOrBuilder extends
      // @@protoc_insertion_point(interface_extends:HashMapMessage)
      com.google.protobuf.MessageOrBuilder {

    /**
     * <code>required .HashMapMessage.HashMapType type = 1;</code>
     */
    boolean hasType();
    /**
     * <code>required .HashMapMessage.HashMapType type = 1;</code>
     */
    HashMapMessage.HashMapType getType();

    /**
     * <code>required string key = 2;</code>
     */
    boolean hasKey();
    /**
     * <code>required string key = 2;</code>
     */
    String getKey();
    /**
     * <code>required string key = 2;</code>
     */
    com.google.protobuf.ByteString
        getKeyBytes();

    /**
     * <code>optional int32 value = 3;</code>
     */
    boolean hasValue();
    /**
     * <code>optional int32 value = 3;</code>
     */
    int getValue();
  }
  /**
   * Protobuf type {@code HashMapMessage}
   */
  public static final class HashMapMessage extends
      com.google.protobuf.GeneratedMessage implements
      // @@protoc_insertion_point(message_implements:HashMapMessage)
      HashMapMessageOrBuilder {
    // Use HashMapMessage.newBuilder() to construct.
    private HashMapMessage(com.google.protobuf.GeneratedMessage.Builder<?> builder) {
      super(builder);
      this.unknownFields = builder.getUnknownFields();
    }
    private HashMapMessage(boolean noInit) { this.unknownFields = com.google.protobuf.UnknownFieldSet.getDefaultInstance(); }

    private static final HashMapMessage defaultInstance;
    public static HashMapMessage getDefaultInstance() {
      return defaultInstance;
    }

    public HashMapMessage getDefaultInstanceForType() {
      return defaultInstance;
    }

    private final com.google.protobuf.UnknownFieldSet unknownFields;
    @Override
    public final com.google.protobuf.UnknownFieldSet
        getUnknownFields() {
      return this.unknownFields;
    }
    private HashMapMessage(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      initFields();
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
            default: {
              if (!parseUnknownField(input, unknownFields,
                                     extensionRegistry, tag)) {
                done = true;
              }
              break;
            }
            case 8: {
              int rawValue = input.readEnum();
              HashMapType value = HashMapType.valueOf(rawValue);
              if (value == null) {
                unknownFields.mergeVarintField(1, rawValue);
              } else {
                bitField0_ |= 0x00000001;
                type_ = value;
              }
              break;
            }
            case 18: {
              com.google.protobuf.ByteString bs = input.readBytes();
              bitField0_ |= 0x00000002;
              key_ = bs;
              break;
            }
            case 24: {
              bitField0_ |= 0x00000004;
              value_ = input.readInt32();
              break;
            }
          }
        }
      } catch (com.google.protobuf.InvalidProtocolBufferException e) {
        throw e.setUnfinishedMessage(this);
      } catch (java.io.IOException e) {
        throw new com.google.protobuf.InvalidProtocolBufferException(
            e.getMessage()).setUnfinishedMessage(this);
      } finally {
        this.unknownFields = unknownFields.build();
        makeExtensionsImmutable();
      }
    }
    public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
      return HashMapMessageProtos.internal_static_HashMapMessage_descriptor;
    }

    protected FieldAccessorTable
        internalGetFieldAccessorTable() {
      return HashMapMessageProtos.internal_static_HashMapMessage_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              HashMapMessage.class, Builder.class);
    }

    public static com.google.protobuf.Parser<HashMapMessage> PARSER =
        new com.google.protobuf.AbstractParser<HashMapMessage>() {
      public HashMapMessage parsePartialFrom(
          com.google.protobuf.CodedInputStream input,
          com.google.protobuf.ExtensionRegistryLite extensionRegistry)
          throws com.google.protobuf.InvalidProtocolBufferException {
        return new HashMapMessage(input, extensionRegistry);
      }
    };

    @Override
    public com.google.protobuf.Parser<HashMapMessage> getParserForType() {
      return PARSER;
    }

    /**
     * Protobuf enum {@code HashMapMessage.HashMapType}
     */
    public enum HashMapType
        implements com.google.protobuf.ProtocolMessageEnum {
      /**
       * <code>PUT = 0;</code>
       */
      PUT(0, 0),
      /**
       * <code>REMOVE = 1;</code>
       */
      REMOVE(1, 1),
      ;

      /**
       * <code>PUT = 0;</code>
       */
      public static final int PUT_VALUE = 0;
      /**
       * <code>REMOVE = 1;</code>
       */
      public static final int REMOVE_VALUE = 1;


      public final int getNumber() { return value; }

      public static HashMapType valueOf(int value) {
        switch (value) {
          case 0: return PUT;
          case 1: return REMOVE;
          default: return null;
        }
      }

      public static com.google.protobuf.Internal.EnumLiteMap<HashMapType>
          internalGetValueMap() {
        return internalValueMap;
      }
      private static com.google.protobuf.Internal.EnumLiteMap<HashMapType>
          internalValueMap =
            new com.google.protobuf.Internal.EnumLiteMap<HashMapType>() {
              public HashMapType findValueByNumber(int number) {
                return HashMapType.valueOf(number);
              }
            };

      public final com.google.protobuf.Descriptors.EnumValueDescriptor
          getValueDescriptor() {
        return getDescriptor().getValues().get(index);
      }
      public final com.google.protobuf.Descriptors.EnumDescriptor
          getDescriptorForType() {
        return getDescriptor();
      }
      public static final com.google.protobuf.Descriptors.EnumDescriptor
          getDescriptor() {
        return HashMapMessage.getDescriptor().getEnumTypes().get(0);
      }

      private static final HashMapType[] VALUES = values();

      public static HashMapType valueOf(
          com.google.protobuf.Descriptors.EnumValueDescriptor desc) {
        if (desc.getType() != getDescriptor()) {
          throw new IllegalArgumentException(
            "EnumValueDescriptor is not for this type.");
        }
        return VALUES[desc.getIndex()];
      }

      private final int index;
      private final int value;

      private HashMapType(int index, int value) {
        this.index = index;
        this.value = value;
      }

      // @@protoc_insertion_point(enum_scope:HashMapMessage.HashMapType)
    }

    private int bitField0_;
    public static final int TYPE_FIELD_NUMBER = 1;
    private HashMapType type_;
    /**
     * <code>required .HashMapMessage.HashMapType type = 1;</code>
     */
    public boolean hasType() {
      return ((bitField0_ & 0x00000001) == 0x00000001);
    }
    /**
     * <code>required .HashMapMessage.HashMapType type = 1;</code>
     */
    public HashMapType getType() {
      return type_;
    }

    public static final int KEY_FIELD_NUMBER = 2;
    private Object key_;
    /**
     * <code>required string key = 2;</code>
     */
    public boolean hasKey() {
      return ((bitField0_ & 0x00000002) == 0x00000002);
    }
    /**
     * <code>required string key = 2;</code>
     */
    public String getKey() {
      Object ref = key_;
      if (ref instanceof String) {
        return (String) ref;
      } else {
        com.google.protobuf.ByteString bs = 
            (com.google.protobuf.ByteString) ref;
        String s = bs.toStringUtf8();
        if (bs.isValidUtf8()) {
          key_ = s;
        }
        return s;
      }
    }
    /**
     * <code>required string key = 2;</code>
     */
    public com.google.protobuf.ByteString
        getKeyBytes() {
      Object ref = key_;
      if (ref instanceof String) {
        com.google.protobuf.ByteString b = 
            com.google.protobuf.ByteString.copyFromUtf8(
                (String) ref);
        key_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }

    public static final int VALUE_FIELD_NUMBER = 3;
    private int value_;
    /**
     * <code>optional int32 value = 3;</code>
     */
    public boolean hasValue() {
      return ((bitField0_ & 0x00000004) == 0x00000004);
    }
    /**
     * <code>optional int32 value = 3;</code>
     */
    public int getValue() {
      return value_;
    }

    private void initFields() {
      type_ = HashMapType.PUT;
      key_ = "";
      value_ = 0;
    }
    private byte memoizedIsInitialized = -1;
    public final boolean isInitialized() {
      byte isInitialized = memoizedIsInitialized;
      if (isInitialized == 1) return true;
      if (isInitialized == 0) return false;

      if (!hasType()) {
        memoizedIsInitialized = 0;
        return false;
      }
      if (!hasKey()) {
        memoizedIsInitialized = 0;
        return false;
      }
      memoizedIsInitialized = 1;
      return true;
    }

    public void writeTo(com.google.protobuf.CodedOutputStream output)
                        throws java.io.IOException {
      getSerializedSize();
      if (((bitField0_ & 0x00000001) == 0x00000001)) {
        output.writeEnum(1, type_.getNumber());
      }
      if (((bitField0_ & 0x00000002) == 0x00000002)) {
        output.writeBytes(2, getKeyBytes());
      }
      if (((bitField0_ & 0x00000004) == 0x00000004)) {
        output.writeInt32(3, value_);
      }
      getUnknownFields().writeTo(output);
    }

    private int memoizedSerializedSize = -1;
    public int getSerializedSize() {
      int size = memoizedSerializedSize;
      if (size != -1) return size;

      size = 0;
      if (((bitField0_ & 0x00000001) == 0x00000001)) {
        size += com.google.protobuf.CodedOutputStream
          .computeEnumSize(1, type_.getNumber());
      }
      if (((bitField0_ & 0x00000002) == 0x00000002)) {
        size += com.google.protobuf.CodedOutputStream
          .computeBytesSize(2, getKeyBytes());
      }
      if (((bitField0_ & 0x00000004) == 0x00000004)) {
        size += com.google.protobuf.CodedOutputStream
          .computeInt32Size(3, value_);
      }
      size += getUnknownFields().getSerializedSize();
      memoizedSerializedSize = size;
      return size;
    }

    private static final long serialVersionUID = 0L;
    @Override
    protected Object writeReplace()
        throws java.io.ObjectStreamException {
      return super.writeReplace();
    }

    public static HashMapMessage parseFrom(
        com.google.protobuf.ByteString data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static HashMapMessage parseFrom(
        com.google.protobuf.ByteString data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static HashMapMessage parseFrom(byte[] data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static HashMapMessage parseFrom(
        byte[] data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static HashMapMessage parseFrom(java.io.InputStream input)
        throws java.io.IOException {
      return PARSER.parseFrom(input);
    }
    public static HashMapMessage parseFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return PARSER.parseFrom(input, extensionRegistry);
    }
    public static HashMapMessage parseDelimitedFrom(java.io.InputStream input)
        throws java.io.IOException {
      return PARSER.parseDelimitedFrom(input);
    }
    public static HashMapMessage parseDelimitedFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return PARSER.parseDelimitedFrom(input, extensionRegistry);
    }
    public static HashMapMessage parseFrom(
        com.google.protobuf.CodedInputStream input)
        throws java.io.IOException {
      return PARSER.parseFrom(input);
    }
    public static HashMapMessage parseFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return PARSER.parseFrom(input, extensionRegistry);
    }

    public static Builder newBuilder() { return Builder.create(); }
    public Builder newBuilderForType() { return newBuilder(); }
    public static Builder newBuilder(HashMapMessage prototype) {
      return newBuilder().mergeFrom(prototype);
    }
    public Builder toBuilder() { return newBuilder(this); }

    @Override
    protected Builder newBuilderForType(
        BuilderParent parent) {
      Builder builder = new Builder(parent);
      return builder;
    }
    /**
     * Protobuf type {@code HashMapMessage}
     */
    public static final class Builder extends
        com.google.protobuf.GeneratedMessage.Builder<Builder> implements
        // @@protoc_insertion_point(builder_implements:HashMapMessage)
        HashMapMessageOrBuilder {
      public static final com.google.protobuf.Descriptors.Descriptor
          getDescriptor() {
        return HashMapMessageProtos.internal_static_HashMapMessage_descriptor;
      }

      protected FieldAccessorTable
          internalGetFieldAccessorTable() {
        return HashMapMessageProtos.internal_static_HashMapMessage_fieldAccessorTable
            .ensureFieldAccessorsInitialized(
                HashMapMessage.class, Builder.class);
      }

      // Construct using pl.edu.agh.dsrg.sr.protos.HashMapMessageProtos.HashMapMessage.newBuilder()
      private Builder() {
        maybeForceBuilderInitialization();
      }

      private Builder(
          BuilderParent parent) {
        super(parent);
        maybeForceBuilderInitialization();
      }
      private void maybeForceBuilderInitialization() {
        if (com.google.protobuf.GeneratedMessage.alwaysUseFieldBuilders) {
        }
      }
      private static Builder create() {
        return new Builder();
      }

      public Builder clear() {
        super.clear();
        type_ = HashMapType.PUT;
        bitField0_ = (bitField0_ & ~0x00000001);
        key_ = "";
        bitField0_ = (bitField0_ & ~0x00000002);
        value_ = 0;
        bitField0_ = (bitField0_ & ~0x00000004);
        return this;
      }

      public Builder clone() {
        return create().mergeFrom(buildPartial());
      }

      public com.google.protobuf.Descriptors.Descriptor
          getDescriptorForType() {
        return HashMapMessageProtos.internal_static_HashMapMessage_descriptor;
      }

      public HashMapMessage getDefaultInstanceForType() {
        return HashMapMessage.getDefaultInstance();
      }

      public HashMapMessage build() {
        HashMapMessage result = buildPartial();
        if (!result.isInitialized()) {
          throw newUninitializedMessageException(result);
        }
        return result;
      }

      public HashMapMessage buildPartial() {
        HashMapMessage result = new HashMapMessage(this);
        int from_bitField0_ = bitField0_;
        int to_bitField0_ = 0;
        if (((from_bitField0_ & 0x00000001) == 0x00000001)) {
          to_bitField0_ |= 0x00000001;
        }
        result.type_ = type_;
        if (((from_bitField0_ & 0x00000002) == 0x00000002)) {
          to_bitField0_ |= 0x00000002;
        }
        result.key_ = key_;
        if (((from_bitField0_ & 0x00000004) == 0x00000004)) {
          to_bitField0_ |= 0x00000004;
        }
        result.value_ = value_;
        result.bitField0_ = to_bitField0_;
        onBuilt();
        return result;
      }

      public Builder mergeFrom(com.google.protobuf.Message other) {
        if (other instanceof HashMapMessage) {
          return mergeFrom((HashMapMessage)other);
        } else {
          super.mergeFrom(other);
          return this;
        }
      }

      public Builder mergeFrom(HashMapMessage other) {
        if (other == HashMapMessage.getDefaultInstance()) return this;
        if (other.hasType()) {
          setType(other.getType());
        }
        if (other.hasKey()) {
          bitField0_ |= 0x00000002;
          key_ = other.key_;
          onChanged();
        }
        if (other.hasValue()) {
          setValue(other.getValue());
        }
        this.mergeUnknownFields(other.getUnknownFields());
        return this;
      }

      public final boolean isInitialized() {
        if (!hasType()) {
          
          return false;
        }
        if (!hasKey()) {
          
          return false;
        }
        return true;
      }

      public Builder mergeFrom(
          com.google.protobuf.CodedInputStream input,
          com.google.protobuf.ExtensionRegistryLite extensionRegistry)
          throws java.io.IOException {
        HashMapMessage parsedMessage = null;
        try {
          parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
        } catch (com.google.protobuf.InvalidProtocolBufferException e) {
          parsedMessage = (HashMapMessage) e.getUnfinishedMessage();
          throw e;
        } finally {
          if (parsedMessage != null) {
            mergeFrom(parsedMessage);
          }
        }
        return this;
      }
      private int bitField0_;

      private HashMapType type_ = HashMapType.PUT;
      /**
       * <code>required .HashMapMessage.HashMapType type = 1;</code>
       */
      public boolean hasType() {
        return ((bitField0_ & 0x00000001) == 0x00000001);
      }
      /**
       * <code>required .HashMapMessage.HashMapType type = 1;</code>
       */
      public HashMapType getType() {
        return type_;
      }
      /**
       * <code>required .HashMapMessage.HashMapType type = 1;</code>
       */
      public Builder setType(HashMapType value) {
        if (value == null) {
          throw new NullPointerException();
        }
        bitField0_ |= 0x00000001;
        type_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>required .HashMapMessage.HashMapType type = 1;</code>
       */
      public Builder clearType() {
        bitField0_ = (bitField0_ & ~0x00000001);
        type_ = HashMapType.PUT;
        onChanged();
        return this;
      }

      private Object key_ = "";
      /**
       * <code>required string key = 2;</code>
       */
      public boolean hasKey() {
        return ((bitField0_ & 0x00000002) == 0x00000002);
      }
      /**
       * <code>required string key = 2;</code>
       */
      public String getKey() {
        Object ref = key_;
        if (!(ref instanceof String)) {
          com.google.protobuf.ByteString bs =
              (com.google.protobuf.ByteString) ref;
          String s = bs.toStringUtf8();
          if (bs.isValidUtf8()) {
            key_ = s;
          }
          return s;
        } else {
          return (String) ref;
        }
      }
      /**
       * <code>required string key = 2;</code>
       */
      public com.google.protobuf.ByteString
          getKeyBytes() {
        Object ref = key_;
        if (ref instanceof String) {
          com.google.protobuf.ByteString b = 
              com.google.protobuf.ByteString.copyFromUtf8(
                  (String) ref);
          key_ = b;
          return b;
        } else {
          return (com.google.protobuf.ByteString) ref;
        }
      }
      /**
       * <code>required string key = 2;</code>
       */
      public Builder setKey(
          String value) {
        if (value == null) {
    throw new NullPointerException();
  }
  bitField0_ |= 0x00000002;
        key_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>required string key = 2;</code>
       */
      public Builder clearKey() {
        bitField0_ = (bitField0_ & ~0x00000002);
        key_ = getDefaultInstance().getKey();
        onChanged();
        return this;
      }
      /**
       * <code>required string key = 2;</code>
       */
      public Builder setKeyBytes(
          com.google.protobuf.ByteString value) {
        if (value == null) {
    throw new NullPointerException();
  }
  bitField0_ |= 0x00000002;
        key_ = value;
        onChanged();
        return this;
      }

      private int value_ ;
      /**
       * <code>optional int32 value = 3;</code>
       */
      public boolean hasValue() {
        return ((bitField0_ & 0x00000004) == 0x00000004);
      }
      /**
       * <code>optional int32 value = 3;</code>
       */
      public int getValue() {
        return value_;
      }
      /**
       * <code>optional int32 value = 3;</code>
       */
      public Builder setValue(int value) {
        bitField0_ |= 0x00000004;
        value_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>optional int32 value = 3;</code>
       */
      public Builder clearValue() {
        bitField0_ = (bitField0_ & ~0x00000004);
        value_ = 0;
        onChanged();
        return this;
      }

      // @@protoc_insertion_point(builder_scope:HashMapMessage)
    }

    static {
      defaultInstance = new HashMapMessage(true);
      defaultInstance.initFields();
    }

    // @@protoc_insertion_point(class_scope:HashMapMessage)
  }

  private static final com.google.protobuf.Descriptors.Descriptor
    internal_static_HashMapMessage_descriptor;
  private static
    com.google.protobuf.GeneratedMessage.FieldAccessorTable
      internal_static_HashMapMessage_fieldAccessorTable;

  public static com.google.protobuf.Descriptors.FileDescriptor
      getDescriptor() {
    return descriptor;
  }
  private static com.google.protobuf.Descriptors.FileDescriptor
      descriptor;
  static {
    String[] descriptorData = {
      "\n\rhashmap.proto\"{\n\016HashMapMessage\022)\n\004typ" +
      "e\030\001 \002(\0162\033.HashMapMessage.HashMapType\022\013\n\003" +
      "key\030\002 \002(\t\022\r\n\005value\030\003 \001(\005\"\"\n\013HashMapType\022" +
      "\007\n\003PUT\020\000\022\n\n\006REMOVE\020\001B1\n\031pl.edu.agh.dsrg." +
      "sr.protosB\024HashMapMessageProtos"
    };
    com.google.protobuf.Descriptors.FileDescriptor.InternalDescriptorAssigner assigner =
        new com.google.protobuf.Descriptors.FileDescriptor.    InternalDescriptorAssigner() {
          public com.google.protobuf.ExtensionRegistry assignDescriptors(
              com.google.protobuf.Descriptors.FileDescriptor root) {
            descriptor = root;
            return null;
          }
        };
    com.google.protobuf.Descriptors.FileDescriptor
      .internalBuildGeneratedFileFrom(descriptorData,
        new com.google.protobuf.Descriptors.FileDescriptor[] {
        }, assigner);
    internal_static_HashMapMessage_descriptor =
      getDescriptor().getMessageTypes().get(0);
    internal_static_HashMapMessage_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessage.FieldAccessorTable(
        internal_static_HashMapMessage_descriptor,
        new String[] { "Type", "Key", "Value", });
  }

  // @@protoc_insertion_point(outer_class_scope)
}
