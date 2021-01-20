package casita.actor;

import casita.actorsystem.ActorSystem;
import org.junit.Test;

public class TestActorLogger {

    public class LogActor extends BaseActor {
        public LogActor(String name) {
            super(name);
        }

        @Override
        public void receiveMessage(Object message) {
            System.out.println("log-actor implementation: " + message.toString());
        }
    }

    @Test
    public void createActorLogger() {
        ActorSystem system = ActorSystem.create("actor-system1");
        Actor actor = system.createActor(new LogActor("logger"));

        String message = "hello world, test message!";
        actor.receive(message);
    }

    //todo: write a test for duplicate actors.

    @Test
    public void sendMessageActorLogger() {
        ActorSystem system = ActorSystem.create("actor-system1");
        Actor actor = system.createActor(new LogActor("logger"));

        String message = "hello world, test message!";
        system.send(actor, message);
    }
}
