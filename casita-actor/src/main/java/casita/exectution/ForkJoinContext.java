package casita.exectution;

import casita.actorsystem.ActorHolder;
import lombok.Getter;


public class ForkJoinContext implements ExecutionContext {

    @Getter
    private String name;

    @Override
    public void addInbox(ActorHolder holder) {
    }

    @Override
    public void removeInbox(ActorHolder holder) {
    }
}
