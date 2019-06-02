package sr.ice.server;

import io.grpc.examples.helloworld.CurrencyExchangeGrpc;
import io.grpc.examples.helloworld.Rate;
import io.grpc.examples.helloworld.RateRequest;
import io.grpc.stub.StreamObserver;

public class CurrencyExchangeImpl extends CurrencyExchangeGrpc.CurrencyExchangeImplBase {

    @Override
    public void getRate(RateRequest request, StreamObserver<Rate> responseObserver) {
        System.out.println("generatePrimeNumbers");
        while (true){

                Rate rate = Rate.newBuilder().setValue(12).build();
                responseObserver.onNext(rate);
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
        //responseObserver.onCompleted();
    }
}
