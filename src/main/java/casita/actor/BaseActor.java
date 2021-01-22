package casita.actor;


import casita.actorsystem.ActorSystem;
import casita.exceptions.ActorShutException;
import lombok.Getter;


public abstract class BaseActor implements Actor {

    @Getter
    private final String name;

    private boolean alive;
    private final ActorSystem system;

    public BaseActor(final ActorSystem system, final String name) {
        this.name = name;
        this.alive = true;
        this.system = system;
    }

    @Override
    public final void receive(Object message) {
        if (!this.alive) {
            throw new ActorShutException("sending message to an already shutdown actor");
        }

        //todo: add future message or state sanitization
        this.receiveMessage(message);
    }

    @Override
    public final void send(String actor, Object message) {
        system.send(actor, message);
    }

    @Override
    public final void send(Actor actor, Object message) {
        system.send(actor, message);
    }

    public boolean isAlive() {
        return this.alive;
    }

    public void shutdown() {
        this.alive = false;
    }
}
