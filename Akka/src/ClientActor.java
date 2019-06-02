import akka.actor.AbstractActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;


public class ClientActor extends AbstractActor {

    private final LoggingAdapter log = Logging.getLogger(getContext().getSystem(), this);

    @Override
    public AbstractActor.Receive createReceive() {
        return receiveBuilder()
                .match(String.class, s -> {

                    if (s.startsWith("search") || (s.startsWith("order")) || (s.startsWith("stream"))) {
                        getContext().actorSelection("akka.tcp://local_system@127.0.0.1:2552/user/server").tell(s, getSelf());
                    } else if (s.startsWith("result")) {
                        System.out.println(s);              // result from child
                    } else if (s.startsWith("back")) {
                        String[] split = s.split(" ", 2);
                        System.out.println(split[1]);
                    }
                })
                .matchAny(o -> log.info("received unknown message"))
                .build();
    }


}
