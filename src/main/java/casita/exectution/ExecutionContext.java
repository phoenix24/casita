package casita.exectution;

import casita.actorsystem.ActorHolder;

public interface ExecutionContext {
    public String getName();
    public void addInbox(ActorHolder holder);
    public void removeInbox(ActorHolder holder);
}
