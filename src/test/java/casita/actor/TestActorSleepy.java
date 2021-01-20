package casita.actor;

import casita.actorsystem.ActorSystem;
import org.junit.Test;

public class TestActorSleepy {

    public class MyActor extends BaseActor {
        public MyActor(String name) {
            super(name);
        }

        @Override
        public void receiveMessage(Object message) {
            try {
                System.out.println(message.toString());
                Thread.sleep(1000L);

            } catch (InterruptedException e) {
                System.out.println("some thing interrupted");
            }
        }
    }

    @Test
    public void createSleepyActor() {
        ActorSystem system = ActorSystem.create("actor-system1");
        Actor actor = system.createActor(new MyActor("sleepy"));

        String message = "hello world";
        system.send(actor, message);
        system.send(actor, message);
    }
}
