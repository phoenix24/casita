package casita.actor;

import casita.actorsystem.ActorConf;
import casita.actorsystem.ActorSystem;
import org.junit.Test;

public class TestActorLogger {

    public static class LogActor extends BaseActor {
        public LogActor(ActorSystem system, String name) {
            super(system, name);
        }

        @Override
        public void receiveMessage(Object message) {
            System.out.println("log-actor implementation: " + message);
        }
    }

    @Test
    public void createActorLogger() {
        ActorSystem system = ActorSystem.create("actor-system1");
        ActorConf conf = ActorConf.builder()
                .name("logger")
                .klass(LogActor.class)
                .inbox("inmemory")
                .policy("never")
                .build();

        Actor actor = system.createActor(conf);
        actor.receive("hello world, test message!");
    }

    //todo: write a test for duplicate actors.

    @Test
    public void sendMessageActorLogger() throws InterruptedException {
        ActorSystem system = ActorSystem.create("actor-system1");
        ActorConf conf = ActorConf.builder()
                .name("logger")
                .klass(LogActor.class)
                .inbox("inmemory")
                .policy("never")
                .build();

        Actor actor = system.createActor(conf);
        system.send(actor, "hello world, test message!");
    }
}
