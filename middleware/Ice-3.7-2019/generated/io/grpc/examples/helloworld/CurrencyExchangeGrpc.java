package io.grpc.examples.helloworld;

import static io.grpc.stub.ClientCalls.asyncUnaryCall;
import static io.grpc.stub.ClientCalls.asyncServerStreamingCall;
import static io.grpc.stub.ClientCalls.asyncClientStreamingCall;
import static io.grpc.stub.ClientCalls.asyncBidiStreamingCall;
import static io.grpc.stub.ClientCalls.blockingUnaryCall;
import static io.grpc.stub.ClientCalls.blockingServerStreamingCall;
import static io.grpc.stub.ClientCalls.futureUnaryCall;
import static io.grpc.MethodDescriptor.generateFullMethodName;
import static io.grpc.stub.ServerCalls.asyncUnaryCall;
import static io.grpc.stub.ServerCalls.asyncServerStreamingCall;
import static io.grpc.stub.ServerCalls.asyncClientStreamingCall;
import static io.grpc.stub.ServerCalls.asyncBidiStreamingCall;
import static io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall;
import static io.grpc.stub.ServerCalls.asyncUnimplementedStreamingCall;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.2.0)",
    comments = "Source: currency.proto")
public final class CurrencyExchangeGrpc {

  private CurrencyExchangeGrpc() {}

  public static final String SERVICE_NAME = "helloworld.CurrencyExchange";

  // Static method descriptors that strictly reflect the proto.
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<io.grpc.examples.helloworld.RateRequest,
      io.grpc.examples.helloworld.Rate> METHOD_GET_RATE =
      io.grpc.MethodDescriptor.create(
          io.grpc.MethodDescriptor.MethodType.SERVER_STREAMING,
          generateFullMethodName(
              "helloworld.CurrencyExchange", "getRate"),
          io.grpc.protobuf.ProtoUtils.marshaller(io.grpc.examples.helloworld.RateRequest.getDefaultInstance()),
          io.grpc.protobuf.ProtoUtils.marshaller(io.grpc.examples.helloworld.Rate.getDefaultInstance()));

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static CurrencyExchangeStub newStub(io.grpc.Channel channel) {
    return new CurrencyExchangeStub(channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static CurrencyExchangeBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    return new CurrencyExchangeBlockingStub(channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary and streaming output calls on the service
   */
  public static CurrencyExchangeFutureStub newFutureStub(
      io.grpc.Channel channel) {
    return new CurrencyExchangeFutureStub(channel);
  }

  /**
   */
  public static abstract class CurrencyExchangeImplBase implements io.grpc.BindableService {

    /**
     */
    public void getRate(io.grpc.examples.helloworld.RateRequest request,
        io.grpc.stub.StreamObserver<io.grpc.examples.helloworld.Rate> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_GET_RATE, responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            METHOD_GET_RATE,
            asyncServerStreamingCall(
              new MethodHandlers<
                io.grpc.examples.helloworld.RateRequest,
                io.grpc.examples.helloworld.Rate>(
                  this, METHODID_GET_RATE)))
          .build();
    }
  }

  /**
   */
  public static final class CurrencyExchangeStub extends io.grpc.stub.AbstractStub<CurrencyExchangeStub> {
    private CurrencyExchangeStub(io.grpc.Channel channel) {
      super(channel);
    }

    private CurrencyExchangeStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected CurrencyExchangeStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new CurrencyExchangeStub(channel, callOptions);
    }

    /**
     */
    public void getRate(io.grpc.examples.helloworld.RateRequest request,
        io.grpc.stub.StreamObserver<io.grpc.examples.helloworld.Rate> responseObserver) {
      asyncServerStreamingCall(
          getChannel().newCall(METHOD_GET_RATE, getCallOptions()), request, responseObserver);
    }
  }

  /**
   */
  public static final class CurrencyExchangeBlockingStub extends io.grpc.stub.AbstractStub<CurrencyExchangeBlockingStub> {
    private CurrencyExchangeBlockingStub(io.grpc.Channel channel) {
      super(channel);
    }

    private CurrencyExchangeBlockingStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected CurrencyExchangeBlockingStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new CurrencyExchangeBlockingStub(channel, callOptions);
    }

    /**
     */
    public java.util.Iterator<io.grpc.examples.helloworld.Rate> getRate(
        io.grpc.examples.helloworld.RateRequest request) {
      return blockingServerStreamingCall(
          getChannel(), METHOD_GET_RATE, getCallOptions(), request);
    }
  }

  /**
   */
  public static final class CurrencyExchangeFutureStub extends io.grpc.stub.AbstractStub<CurrencyExchangeFutureStub> {
    private CurrencyExchangeFutureStub(io.grpc.Channel channel) {
      super(channel);
    }

    private CurrencyExchangeFutureStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected CurrencyExchangeFutureStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new CurrencyExchangeFutureStub(channel, callOptions);
    }
  }

  private static final int METHODID_GET_RATE = 0;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final CurrencyExchangeImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(CurrencyExchangeImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_GET_RATE:
          serviceImpl.getRate((io.grpc.examples.helloworld.RateRequest) request,
              (io.grpc.stub.StreamObserver<io.grpc.examples.helloworld.Rate>) responseObserver);
          break;
        default:
          throw new AssertionError();
      }
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public io.grpc.stub.StreamObserver<Req> invoke(
        io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        default:
          throw new AssertionError();
      }
    }
  }

  private static final class CurrencyExchangeDescriptorSupplier implements io.grpc.protobuf.ProtoFileDescriptorSupplier {
    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return io.grpc.examples.helloworld.HelloWorldProto.getDescriptor();
    }
  }

  private static volatile io.grpc.ServiceDescriptor serviceDescriptor;

  public static io.grpc.ServiceDescriptor getServiceDescriptor() {
    io.grpc.ServiceDescriptor result = serviceDescriptor;
    if (result == null) {
      synchronized (CurrencyExchangeGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new CurrencyExchangeDescriptorSupplier())
              .addMethod(METHOD_GET_RATE)
              .build();
        }
      }
    }
    return result;
  }
}
