package casita.inbox;

public interface Inbox {
    public int size();
    public void add(Object message);
    public Object get();

}
