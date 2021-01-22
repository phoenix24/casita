package casita.exectution;

import casita.actor.Actor;
import casita.actorsystem.ActorHolder;
import casita.inbox.Inbox;
import casita.utils.ThreadUtils;
import lombok.Getter;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ExecutionContext {

    @Getter
    private String name;

    @Getter
    private Map<Actor, Inbox> inboxes;

    private ExecutorService executor;

    public ExecutionContext(String name, int threads) {
        this.name = name;
        this.inboxes = new ConcurrentHashMap<>();
        this.executor = Executors.newFixedThreadPool(threads, ThreadUtils.tFactory.apply(name));

        this.executor.submit(() -> {
            while (true) {
                for (Map.Entry<Actor, Inbox> entry : this.inboxes.entrySet()) {
                    final Actor actor = entry.getKey();
                    final Inbox inbox = entry.getValue();
                    if (inbox.size() > 0) {
                        final Object message = inbox.get();
                        actor.receive(message);
                    }
                }
            }
        });
    }

    public ExecutionContext(String name) {
        this(name, 1);
    }

    public static ExecutionContext create() {
        return new ExecutionContext("default");
    }

    public void addInbox(ActorHolder holder) {
        this.inboxes.put(holder.getActor(), holder.getInbox());
    }

    public void removeInbox(ActorHolder holder) {
        this.inboxes.remove(holder.getActor());
    }
}
