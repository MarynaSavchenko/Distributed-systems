import akka.actor.*;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import akka.japi.pf.DeciderBuilder;
import scala.concurrent.duration.Duration;

import java.util.concurrent.ExecutionException;

import static akka.actor.SupervisorStrategy.restart;
import static akka.actor.SupervisorStrategy.resume;

public class ServerActor extends AbstractActor {

    private final LoggingAdapter log = Logging.getLogger(getContext().getSystem(), this);


    // must be implemented -> creates initial behaviour
    @Override
    public AbstractActor.Receive createReceive() {
        return receiveBuilder()
                .match(String.class, s -> {
                    if (s.startsWith("search")) {
                        context().child("searchWorker").get().tell(s, getSelf()); // send task to child
                    }
                    else if (s.startsWith("order")){
                        context().child("orderWorker").get().tell(s, getSelf()); // send task to child

                    }
                    else if (s.startsWith("stream")){
                        context().child("streamWorker").get().tell(s, getSelf()); // send task to child
                    }
                    else if (s.startsWith("result") || s.startsWith("back")) {
                        // result from child
                        getContext().actorSelection("akka.tcp://remote_system@127.0.0.1:3552/user/client").tell(s, getSelf());
                }
                })
                .matchAny(o -> log.info("received unknown message"))
                .build();
    }

    // optional
    @Override
    public void preStart() throws Exception {
        context().actorOf(Props.create(SearchWorker.class), "searchWorker");
        context().actorOf(Props.create(OrderWorker.class), "orderWorker");
        context().actorOf(Props.create(StreamWorker.class), "streamWorker");
    }

    private static SupervisorStrategy strategy
            = new OneForOneStrategy(10, Duration.create("1 minute"), DeciderBuilder.
                    match(ExecutionException.class, o -> resume()).
                    match(InterruptedException.class, o -> resume()).
                    matchAny(o -> restart()).
                    build());

    @Override
    public SupervisorStrategy supervisorStrategy() {
        return strategy;
    }

}
