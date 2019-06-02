import akka.actor.AbstractActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;

import java.io.*;
import java.util.Scanner;
import java.util.concurrent.*;

public class SearchWorker extends AbstractActor {

    private final LoggingAdapter log = Logging.getLogger(getContext().getSystem(), this);


    @Override
    public AbstractActor.Receive createReceive() {
        return receiveBuilder()
                .match(String.class, s -> {
                    String result = Search(s);
                    getSender().tell("result: " + result, getSelf());
                })
                .matchAny(o -> log.info("received unknown message"))
                .build();
    }

    private String Search(String s) throws ExecutionException, InterruptedException {
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
        if (result != null) return result;
        else if (result2 != null) return result2;
        else return "There is no such book\n";
    }
}

class SearchThread implements Callable<String> {
    private String bookName;
    private String fileName;

    public SearchThread(String fileName, String bookName) {
        this.fileName = fileName;
        this.bookName = bookName;
    }

    @Override
    public String call() {
        try {
            Scanner scanner = new Scanner(new File("resources/" + fileName));
            while (scanner.hasNextLine()) {
                String[] split = scanner.nextLine().split(",");
                if (split[0].equals(bookName))
                    return split[1];
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;

    }

}
