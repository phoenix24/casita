package casita.actor;


public interface Actor {

    public String getId();
    public String getName();
    public boolean isShutdown();

    //todo: support for typed messages later.
    public void shutdown();

    public void receive(Object message);
    public void receiveMessage(Object message);
}
