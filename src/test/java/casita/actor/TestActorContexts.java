package casita.actor;

import casita.actorsystem.ActorConf;
import casita.actorsystem.ActorSystem;
import casita.exectution.ThreadPoolContext;
import org.junit.Test;

import java.util.List;

public class TestActorContexts {

    public static class MyActor extends BaseActor {
        public MyActor(ActorSystem system, String name) {
            super(system, name);
        }

        @Override
        public void receiveMessage(Object message) {
            System.out.println(this.getPath() + "-actor implementation: " + message);
        }
    }

    @Test
    public void createContextActor() throws InterruptedException {
        ActorSystem system = ActorSystem.create(
        "actor-system1",
            List.of(
                new ThreadPoolContext("context1"),
                new ThreadPoolContext("context2")
            )
        );
        ActorConf.ActorConfBuilder builder = ActorConf.builder()
                .klass(MyActor.class)
                .inbox("inmemory")
                .policy("never");

        Actor actor1 = system.createActor(builder.name("actr1").context("context1").build());
        Actor actor2 = system.createActor(builder.name("actr2").context("context2").build());
        Actor actor3 = system.createActor(builder.name("actr3").build());
        Actor actor4 = system.createActor(builder.name("actr4").build());
        Actor actor5 = system.createActor(builder.name("actr5").build());
        String message = "hello world";

        for (int i = 0; i < 5; i++) {
            system.send(actor1, String.format("%s - %s, new context1", message, i));
            system.send(actor2, String.format("%s - %s, new context2", message, i));
            system.send(actor3, String.format("%s - %s, def context", message, i));
            system.send(actor4, String.format("%s - %s, def context", message, i));
            system.send(actor5, String.format("%s - %s, def context", message, i));
        }

        Thread.sleep(10000L);
    }
}
