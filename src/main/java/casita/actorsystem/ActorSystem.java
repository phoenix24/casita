package casita.actorsystem;

import casita.actor.Actor;
import casita.exceptions.ActorExistsException;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ActorSystem {

    private final String name;
    private final Map<String, Actor> actors;

    private ActorSystem(String name) {
        this.name = name;
        this.actors = new ConcurrentHashMap<String, Actor>();
    }

    public static ActorSystem create(String name) {
        return new ActorSystem(name);
    }

    public Actor createActor(Actor actor) {
        String name = actor.getName();
        if (this.actors.containsKey(name)) {
            throw new ActorExistsException("a duplicate actor of same name already exists");
        }
        this.actors.put(name, actor);
        return actor;
    }

    public void send(String name, String message) {
        Actor actor = this.actors.get(name);
        send(actor, message);
    }

    public void send(Actor actor, String message) {

        actor.receiveMessage(message);
    }
}
