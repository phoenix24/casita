package casita.actor;

import casita.actorsystem.ActorConf;
import casita.actorsystem.ActorSystem;
import org.junit.Test;

public class TestActorError {

    public static class MyActor extends BaseActor {
        public MyActor(ActorSystem system, String name) {
            super(system, name);
        }

        @Override
        public void receiveMessage(Object message) {
            int count = 1 / 0;
            System.out.println(count + ", " + message);
        }
    }

    @Test
    public void createErrorActor() throws InterruptedException {
        ActorSystem system = ActorSystem.create("actor-system1");
        ActorConf conf = ActorConf.builder()
                .klass(MyActor.class)
                .name("erroring")
                .inbox("inmemory")
                .policy("thrice")
                .build();

        Actor actor = system.createActor(conf);
        system.send(actor, "hello world, error actor");
        Thread.sleep(1000L);
    }
}
