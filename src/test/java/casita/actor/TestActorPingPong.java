package casita.actor;

import casita.actorsystem.ActorConf;
import casita.actorsystem.ActorSystem;
import org.junit.Test;

public class TestActorPingPong {

    public static class PingActor extends BaseActor {
        private long count = 0;

        public PingActor(ActorSystem system, String name) {
            super(system, name);
        }

        @Override
        public void receiveMessage(Object message) {
            System.out.println("ping-actor: " + this.getName() + " " + count);
            if (count <= 10) {
                count += 1;
                this.send("pong", message);
            } else {
                System.out.println("10 messages sent; stopping");
            }
        }
    }

    public static class PongActor extends BaseActor {
        private long count = 0;

        public PongActor(ActorSystem system, String name) {
            super(system, name);
        }

        @Override
        public void receiveMessage(Object message) {
            System.out.println("pong-actor: " + this.getName() + " " + count);
            if (count <= 10) {
                count += 1;
                this.send("ping", message);
            } else {
                System.out.println("10 messages sent; stopping");
            }
        }
    }

    @Test
    public void createPingPongActor() throws InterruptedException {
        ActorSystem system = ActorSystem.create("actor-system1");

        //ping actor
        ActorConf confPing = ActorConf.builder()
                .klass(PingActor.class)
                .name("ping")
                .inbox("inmemory")
                .policy("never")
                .build();
        Actor ping = system.createActor(confPing);


        //pong actor
        ActorConf confPong = ActorConf.builder()
                .klass(PongActor.class)
                .name("pong")
                .inbox("inmemory")
                .policy("never")
                .build();
        Actor pong = system.createActor(confPong);

        // send message to actor
        ping.receive("hello world");
        system.send("pong", "here");
    }

    @Test
    public void createPingPongViaQueue() throws InterruptedException {
        ActorSystem system = ActorSystem.create("actor-system1");

        //ping actor
        ActorConf confPing = ActorConf.builder()
                .klass(PingActor.class)
                .name("ping")
                .inbox("inmemory")
                .policy("never")
                .build();
        Actor ping = system.createActor(confPing);


        //pong actor
        ActorConf confPong = ActorConf.builder()
                .klass(PongActor.class)
                .name("pong")
                .inbox("inmemory")
                .policy("never")
                .build();
        Actor pong = system.createActor(confPong);

        // send message to actor-system
        system.send(ping, "hello world");
    }
}
