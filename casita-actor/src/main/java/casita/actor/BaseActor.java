package casita.actor;


import casita.actorsystem.ActorPath;
import casita.actorsystem.ActorSystem;
import casita.exception.ActorShutException;


public abstract class BaseActor implements Actor {

    private boolean alive;
    private final ActorPath path;
    private final ActorSystem system;

    public BaseActor(final ActorSystem system, final ActorPath path) {
        this.path = path;
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
    public final void send(Actor actor, Object message) {
        system.send(actor, message);
    }

    @Override
    public final void send(ActorPath actor, Object message) {
        system.send(actor, message);
    }

    @Override
    public final void send(String actor, Object message) {
        system.send(actor, message);
    }

    public boolean isAlive() {
        return this.alive;
    }

    @Override
    public ActorPath getPath() {
        return path;
    }

    public void shutdown() {
        this.alive = false;
    }
}
