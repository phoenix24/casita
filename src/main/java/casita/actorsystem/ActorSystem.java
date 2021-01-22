package casita.actorsystem;

import casita.actor.Actor;
import casita.exceptions.ActorExistsException;
import casita.exectution.ExecutionContext;
import casita.inbox.Inbox;
import casita.message.Message;
import com.google.common.collect.ImmutableList;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import static casita.message.Message.KILL;

public class ActorSystem {

    private final String name;
    //todo: change to use actor-address instead of actor-name
    private final Map<String, ActorHolder> actors;

    private final Map<String, ExecutionContext> contexts;
    private final static ExecutionContext EXEC_DEFAULT = ExecutionContext.create();

    private ActorSystem(String name, List<ExecutionContext> contexts) {
        this.name = name;
        this.actors = new ConcurrentHashMap<>();
        this.contexts = contexts.stream()
                            .collect(Collectors.toMap(ExecutionContext::getName, ex -> ex));
    }

    public static ActorSystem create(String name, List<ExecutionContext> contexts) {
        ImmutableList<ExecutionContext> _contexts = ImmutableList.<ExecutionContext>builder()
                .addAll(contexts)
                .add(EXEC_DEFAULT)
                .build();
        return new ActorSystem(name, _contexts);
    }

    public static ActorSystem create(String name) {
        return create(name, List.of(EXEC_DEFAULT));
    }

    public Actor createActor(ActorConf conf) {
        Objects.requireNonNull(conf, "actor conf cannot be null");

        String name = conf.getName();
        if (this.actors.containsKey(name)) {
            throw new ActorExistsException("a duplicate actor of same name already exists");
        }

        ActorHolder holder = ActorHolder.create(this, conf);
        ExecutionContext context = conf.getContext() == null
                ? EXEC_DEFAULT
                : this.contexts.get(conf.getContext());

        context.addInbox(holder);
        this.actors.put(name, holder);
        return holder.getActor();
    }

    public void send(String name, Object message) {
        ActorHolder holder = this.actors.get(name);
        Actor actor = holder.getActor();
        send(actor, message);
    }

    public void send(Actor actor, Object message) {
        if (Objects.isNull(actor) || !actor.isAlive()) {
            throw new RuntimeException("actor is already shut or died.");
        }

        String name = actor.getName();
        if (message instanceof Message) {
            Message msg = (Message) message;
            if (msg == KILL) {
                actorShutdown(name);
            }
            System.out.println("ignoring unsupported message: " + message);

        } else {
            actorMessage(name, message);
        }
    }

    private void actorMessage(String name, Object message) {
        ActorHolder holder = this.actors.get(name);
        Inbox inbox = holder.getInbox();
        inbox.add(message);
    }

    private void actorShutdown(String name) {
        //todo: additional housekeeping
        ActorHolder holder = this.actors.get(name);
        Actor actor = holder.getActor();
        actor.shutdown();
        this.actors.remove(name);
    }
}
