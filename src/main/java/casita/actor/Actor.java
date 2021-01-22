package casita.actor;


public interface Actor {

    //todo: support for typed messages later.
    public String getName();

    public void shutdown();
    public boolean isAlive();

    public void send(Actor actor, Object message);
    public void send(String actor, Object message);
    public void receive(Object message);

    public void receiveMessage(Object message);
}
