package casita.actor;

import casita.actorsystem.ActorConf;
import casita.actorsystem.ActorPath;
import casita.actorsystem.ActorSystem;
import org.junit.Test;

public class TestActorSleepy {

    public static class MyActor extends BaseActor {
        public MyActor(ActorSystem system, String path) {
            super(system, ActorPath.create(path));
        }

        @Override
        public void receiveMessage(Object message) {
            try {
                System.out.println("sleepy-actor implementation: " + message);
                Thread.sleep(1000L);

            } catch (InterruptedException e) {
                System.out.println("some thing interrupted");
            }
        }
    }

    @Test
    public void createSleepyActor() throws InterruptedException {
        ActorSystem system = ActorSystem.create("actor-system1");
        ActorConf conf = ActorConf.builder()
                .klass(MyActor.class)
                .path("sleepy")
                .inbox("inmemory")
                .policy("never")
                .build();

        Actor actor = system.createActor(conf);
        String message = "hello world";

        for (int i = 0; i < 20; i++) {
            system.send(actor, String.format("%s - %s", message, i));
        }
    }
}
