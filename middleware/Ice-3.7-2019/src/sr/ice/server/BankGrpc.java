package sr.ice.server;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.StatusRuntimeException;
import io.grpc.examples.helloworld.Currency;
import io.grpc.examples.helloworld.CurrencyExchangeGrpc;
import io.grpc.examples.helloworld.Rate;
import io.grpc.examples.helloworld.RateRequest;

import java.util.Iterator;
import java.util.concurrent.TimeUnit;

import java.util.logging.Level;
import java.util.logging.Logger;


public class BankGrpc {


    private static final Logger logger = Logger.getLogger(BankGrpc.class.getName());

    private ManagedChannel channel;
    private final CurrencyExchangeGrpc.CurrencyExchangeBlockingStub currencyExchangeBlockingStub;
    private BankServiceI bankServiceI;


    /**
     * Construct client connecting to HelloWorld server at {@code host:port}.
     */
    public BankGrpc(String host, int port, BankServiceI bankServiceI) {
        this.bankServiceI = bankServiceI;
        channel = ManagedChannelBuilder.forAddress(host, port)
                // Channels are secure by default (via SSL/TLS). For the example we disable TLS to avoid needing certificates.
                .usePlaintext(true)
                .build();

        currencyExchangeBlockingStub = CurrencyExchangeGrpc.newBlockingStub(channel);
    }


    public void shutdown() throws InterruptedException {
        channel.shutdown().awaitTermination(5, TimeUnit.SECONDS);
    }


    public void test() throws InterruptedException {
        try {
            ThreadBank threadBank = null;
            for (String i : bankServiceI.mapRates.keySet()) {
                System.out.println(i);
                threadBank = new ThreadBank(i,currencyExchangeBlockingStub,bankServiceI,logger);
                threadBank.start();
            }
            threadBank.join();
        } finally {
            shutdown();
        }

    }
/*
    public void getRates(String currency) {

        RateRequest request = RateRequest.newBuilder().build().newBuilder().setCurrencyFrom(Currency.EUR).setCurrencyTo(parseCurrency(currency)).build();
        Iterator<Rate> rates;
        try {
            rates = currencyExchangeBlockingStub.getRate(request);
            while (rates.hasNext()) {
                Rate rate = rates.next();
                this.bankServiceI.mapRates.put(currency, rate.getValue());
                System.out.println("Number: " + rate.getValue());
            }
        } catch (StatusRuntimeException ex) {
            logger.log(Level.WARNING, "RPC failed: {0}", ex.getStatus());
            return;
        }
    }
    */

}

class ThreadBank extends Thread{
    String currency;
    CurrencyExchangeGrpc.CurrencyExchangeBlockingStub currencyExchangeBlockingStub;
    BankServiceI bankServiceI;
    Logger logger;
    public ThreadBank(String currency, CurrencyExchangeGrpc.CurrencyExchangeBlockingStub currencyExchangeBlockingStub,
                        BankServiceI bankServiceI, Logger logger){
        this.currency = currency;
        this.currencyExchangeBlockingStub = currencyExchangeBlockingStub;
        this.bankServiceI = bankServiceI;
        this.logger = logger;
    }

    @Override
    public void run() {
        RateRequest request = RateRequest.newBuilder().build().newBuilder().setCurrencyFrom(parseCurrency(bankServiceI.homeCurrency)).setCurrencyTo(parseCurrency(currency)).build();
        Iterator<Rate> rates;
        try {
            rates = currencyExchangeBlockingStub.getRate(request);
            while (rates.hasNext()) {
                Rate rate = rates.next();
                this.bankServiceI.mapRates.put(currency, rate.getValue());
                System.out.println("Number: " + rate.getValue());
            }
        } catch (StatusRuntimeException ex) {
            logger.log(Level.WARNING, "RPC failed: {0}", ex.getStatus());
            return;
        }
    }

    private static Currency parseCurrency(String currency) {
        switch (currency) {
            case "EUR":
                return Currency.EUR;
            case "USD":
                return Currency.USD;
            case "PLN":
                return Currency.PLN;
            default:
                System.out.println("We don't work with such currency");
                return null;
        }
    }
}

