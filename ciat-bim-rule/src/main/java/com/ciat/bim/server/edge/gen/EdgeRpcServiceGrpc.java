package com.ciat.bim.server.edge.gen;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 * <pre>
 * Interface exported by the ThingsBoard Edge Transport.
 * </pre>
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.38.0)",
    comments = "Source: edge.proto")
public final class EdgeRpcServiceGrpc {

  private EdgeRpcServiceGrpc() {}

  public static final String SERVICE_NAME = "edge.EdgeRpcService";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<RequestMsg,
      ResponseMsg> getHandleMsgsMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "handleMsgs",
      requestType = RequestMsg.class,
      responseType = ResponseMsg.class,
      methodType = io.grpc.MethodDescriptor.MethodType.BIDI_STREAMING)
  public static io.grpc.MethodDescriptor<RequestMsg,
      ResponseMsg> getHandleMsgsMethod() {
    io.grpc.MethodDescriptor<RequestMsg, ResponseMsg> getHandleMsgsMethod;
    if ((getHandleMsgsMethod = EdgeRpcServiceGrpc.getHandleMsgsMethod) == null) {
      synchronized (EdgeRpcServiceGrpc.class) {
        if ((getHandleMsgsMethod = EdgeRpcServiceGrpc.getHandleMsgsMethod) == null) {
          EdgeRpcServiceGrpc.getHandleMsgsMethod = getHandleMsgsMethod =
              io.grpc.MethodDescriptor.<RequestMsg, ResponseMsg>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.BIDI_STREAMING)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "handleMsgs"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  RequestMsg.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  ResponseMsg.getDefaultInstance()))
              .setSchemaDescriptor(new EdgeRpcServiceMethodDescriptorSupplier("handleMsgs"))
              .build();
        }
      }
    }
    return getHandleMsgsMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static EdgeRpcServiceStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<EdgeRpcServiceStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<EdgeRpcServiceStub>() {
        @Override
        public EdgeRpcServiceStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new EdgeRpcServiceStub(channel, callOptions);
        }
      };
    return EdgeRpcServiceStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static EdgeRpcServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<EdgeRpcServiceBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<EdgeRpcServiceBlockingStub>() {
        @Override
        public EdgeRpcServiceBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new EdgeRpcServiceBlockingStub(channel, callOptions);
        }
      };
    return EdgeRpcServiceBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static EdgeRpcServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<EdgeRpcServiceFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<EdgeRpcServiceFutureStub>() {
        @Override
        public EdgeRpcServiceFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new EdgeRpcServiceFutureStub(channel, callOptions);
        }
      };
    return EdgeRpcServiceFutureStub.newStub(factory, channel);
  }

  /**
   * <pre>
   * Interface exported by the ThingsBoard Edge Transport.
   * </pre>
   */
  public static abstract class EdgeRpcServiceImplBase implements io.grpc.BindableService {

    /**
     */
    public io.grpc.stub.StreamObserver<RequestMsg> handleMsgs(
        io.grpc.stub.StreamObserver<ResponseMsg> responseObserver) {
      return io.grpc.stub.ServerCalls.asyncUnimplementedStreamingCall(getHandleMsgsMethod(), responseObserver);
    }

    @Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            getHandleMsgsMethod(),
            io.grpc.stub.ServerCalls.asyncBidiStreamingCall(
              new MethodHandlers<
                RequestMsg,
                ResponseMsg>(
                  this, METHODID_HANDLE_MSGS)))
          .build();
    }
  }

  /**
   * <pre>
   * Interface exported by the ThingsBoard Edge Transport.
   * </pre>
   */
  public static final class EdgeRpcServiceStub extends io.grpc.stub.AbstractAsyncStub<EdgeRpcServiceStub> {
    private EdgeRpcServiceStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @Override
    protected EdgeRpcServiceStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new EdgeRpcServiceStub(channel, callOptions);
    }

    /**
     */
    public io.grpc.stub.StreamObserver<RequestMsg> handleMsgs(
        io.grpc.stub.StreamObserver<ResponseMsg> responseObserver) {
      return io.grpc.stub.ClientCalls.asyncBidiStreamingCall(
          getChannel().newCall(getHandleMsgsMethod(), getCallOptions()), responseObserver);
    }
  }

  /**
   * <pre>
   * Interface exported by the ThingsBoard Edge Transport.
   * </pre>
   */
  public static final class EdgeRpcServiceBlockingStub extends io.grpc.stub.AbstractBlockingStub<EdgeRpcServiceBlockingStub> {
    private EdgeRpcServiceBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @Override
    protected EdgeRpcServiceBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new EdgeRpcServiceBlockingStub(channel, callOptions);
    }
  }

  /**
   * <pre>
   * Interface exported by the ThingsBoard Edge Transport.
   * </pre>
   */
  public static final class EdgeRpcServiceFutureStub extends io.grpc.stub.AbstractFutureStub<EdgeRpcServiceFutureStub> {
    private EdgeRpcServiceFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @Override
    protected EdgeRpcServiceFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new EdgeRpcServiceFutureStub(channel, callOptions);
    }
  }

  private static final int METHODID_HANDLE_MSGS = 0;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final EdgeRpcServiceImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(EdgeRpcServiceImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @Override
    @SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        default:
          throw new AssertionError();
      }
    }

    @Override
    @SuppressWarnings("unchecked")
    public io.grpc.stub.StreamObserver<Req> invoke(
        io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_HANDLE_MSGS:
          return (io.grpc.stub.StreamObserver<Req>) serviceImpl.handleMsgs(
              (io.grpc.stub.StreamObserver<ResponseMsg>) responseObserver);
        default:
          throw new AssertionError();
      }
    }
  }

  private static abstract class EdgeRpcServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    EdgeRpcServiceBaseDescriptorSupplier() {}

    @Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return EdgeProtos.getDescriptor();
    }

    @Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("EdgeRpcService");
    }
  }

  private static final class EdgeRpcServiceFileDescriptorSupplier
      extends EdgeRpcServiceBaseDescriptorSupplier {
    EdgeRpcServiceFileDescriptorSupplier() {}
  }

  private static final class EdgeRpcServiceMethodDescriptorSupplier
      extends EdgeRpcServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    EdgeRpcServiceMethodDescriptorSupplier(String methodName) {
      this.methodName = methodName;
    }

    @Override
    public com.google.protobuf.Descriptors.MethodDescriptor getMethodDescriptor() {
      return getServiceDescriptor().findMethodByName(methodName);
    }
  }

  private static volatile io.grpc.ServiceDescriptor serviceDescriptor;

  public static io.grpc.ServiceDescriptor getServiceDescriptor() {
    io.grpc.ServiceDescriptor result = serviceDescriptor;
    if (result == null) {
      synchronized (EdgeRpcServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new EdgeRpcServiceFileDescriptorSupplier())
              .addMethod(getHandleMsgsMethod())
              .build();
        }
      }
    }
    return result;
  }
}
