import akka.NotUsed;
import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import akka.stream.ActorMaterializer;
import akka.stream.OverflowStrategy;
import akka.stream.ThrottleMode;
import akka.stream.javadsl.Sink;
import akka.stream.javadsl.Source;
import scala.collection.Iterator;
import scala.collection.JavaConversions;
import scala.concurrent.duration.FiniteDuration;
import scala.io.Codec;

import java.io.File;
import java.util.concurrent.TimeUnit;

public class StreamWorker extends AbstractActor {


    private final LoggingAdapter log = Logging.getLogger(getContext().getSystem(), this);

    @Override
    public AbstractActor.Receive createReceive() {
        return receiveBuilder()
                .match(String.class, s -> {
                    String[] split = s.split(" ", 2);
                    String filePath = "";
                    try {
                        filePath = "resources/" + split[1] + ".txt";
                        if (!new File(filePath).exists()) {
                            getSender().tell("result: There is no such book in e-library", getSelf());
                        } else {
                            ActorMaterializer materializer = ActorMaterializer.create(context());
                            ActorRef run = Source.actorRef(1000, OverflowStrategy.dropNew())
                                    .throttle(1, FiniteDuration.create(1, TimeUnit.SECONDS), 1, ThrottleMode.shaping())
                                    .to(Sink.actorRef(getSender(), NotUsed.getInstance()))
                                    .run(materializer);
                            Iterator<String> lines = scala.io.Source.fromFile(filePath, Codec.UTF8()).getLines();
                            JavaConversions.asJavaIterator(lines).forEachRemaining(line -> {
                                run.tell("back: " + line, getSelf());
                            });
                        }
                    } catch (ArrayIndexOutOfBoundsException e) {
                        getSender().tell("result: Enter it with the book name", getSelf());
                    }

                })
                .matchAny(o -> log.info("received unknown message"))
                .build();
    }
}
