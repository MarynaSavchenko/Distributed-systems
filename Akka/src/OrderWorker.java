import akka.actor.AbstractActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class OrderWorker extends AbstractActor {

    private final LoggingAdapter log = Logging.getLogger(getContext().getSystem(), this);


    @Override
    public AbstractActor.Receive createReceive() {
        return receiveBuilder()
                .match(String.class, s -> {
                    String result = Order(s);
                    getSender().tell("result: " + result, getSelf());
                })
                .matchAny(o -> log.info("received unknown message"))
                .build();
    }

    private String Order(String s) throws ExecutionException, InterruptedException {
        String[] split = s.split(" ", 2);
        String name = "";
        try {
            name = split[1];
        } catch (ArrayIndexOutOfBoundsException e) {
            return "Enter it with the book name";
        }
        ExecutorService executor = Executors.newCachedThreadPool();
        Future<String> futureCall = executor.submit(new SearchThread("base.txt", name));
        Future<String> futureCall2 = executor.submit(new SearchThread("base2.txt", name));
        String result = futureCall.get(); // Here the thread will be blocked
        String result2 = futureCall2.get(); // Here the thread will be blocked
        if ((result != null) || (result2 != null)) return writeToOrders(name);
        else return "There is no such book\n";
    }

    private String writeToOrders(String name) {
        File file = new File("resources/orders.txt");
        FileWriter fr = null;
        try {
            fr = new FileWriter(file, true);
            fr.write(name + "\n");
            fr.close();
        } catch (IOException e) {
            e.printStackTrace();
            return "Error while adding an order";
        }
        return "Order added " + name;
    }
}


