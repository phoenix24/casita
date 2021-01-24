package casita.actor;


import casita.actorsystem.ActorPath;

public interface Actor {

    //todo: support for typed messages later.
    public ActorPath getPath();

    public void shutdown();
    public boolean isAlive();

    public void send(Actor actor, Object message);
    public void send(String actor, Object message);
    public void send(ActorPath actor, Object message);

    public void receive(Object message);
    public void receiveMessage(Object message);
}
