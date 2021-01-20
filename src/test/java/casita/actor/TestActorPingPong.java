package casita.actor;

import casita.actorsystem.ActorSystem;
import org.junit.Test;

public class TestActorPingPong {

    //public class NotifyActor
    //public class PingPongStateActor



    public class MyActor extends BaseActor {
        private Actor another;
        private long count = 0;


        public MyActor(String name) {
            super(name);
        }

        @Override
        public void receiveMessage(Object message) {
            System.out.println("sleepy-actor: " + this.getName() + " " + count);
            if (count <= 10) {
                count += 1;
                this.another.receive(message);
            } else {
                System.out.println("10 messages sent; stopping");
            }
        }

        public void setAnother(Actor another) {
            this.another = another;
        }
    }

    @Test
    public void createPingPongActor() {
        ActorSystem system = ActorSystem.create("actor-system1");
        Actor ping = system.createActor(new MyActor("ping"));
        Actor pong = system.createActor(new MyActor("pong"));

        ((MyActor) ping).setAnother(pong);
        ((MyActor) pong).setAnother(ping);

        String message = "hello world";
        ping.receive(message);
    }

    @Test
    public void createPingPongViaQueue() {
        ActorSystem system = ActorSystem.create("actor-system1");
        Actor ping = system.createActor(new MyActor("ping"));
        Actor pong = system.createActor(new MyActor("pong"));

        ((MyActor) ping).setAnother(pong);
        ((MyActor) pong).setAnother(ping);

        String message = "hello world";
        system.send(ping, message);
    }
}
