package casita.exectution;

import casita.actorsystem.ActorHolder;
import casita.inbox.Inbox;
import casita.utils.ThreadUtils;
import com.google.common.collect.Maps;
import lombok.Getter;

import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadPoolContext implements ExecutionContext {

    @Getter
    private String name;

    @Getter
    private Map<ActorHolder, Inbox> inboxes;

    private ExecutorService executor;

    public ThreadPoolContext(String name, int threads) {
        this.name = name;
        this.inboxes = Maps.newConcurrentMap();
        this.executor = Executors.newFixedThreadPool(threads, ThreadUtils.tFactory.apply(name));

        this.executor.execute(() -> {
            while (true) {
                for (Map.Entry<ActorHolder, Inbox> entry : this.inboxes.entrySet()) {
                    final Inbox inbox = entry.getValue();
                    final ActorHolder holder = entry.getKey();
                    if (inbox.size() > 0) {
                        holder.execute(inbox.get());
                    }
                }
            }
        });
    }

    public ThreadPoolContext(String name) {
        this(name, 1);
    }

    public static ThreadPoolContext create() {
        return new ThreadPoolContext("default-threadpool-execution-context");
    }

    @Override
    public void addInbox(ActorHolder holder) {
        this.inboxes.put(holder, holder.getInbox());
    }

    @Override
    public void removeInbox(ActorHolder holder) {
        this.inboxes.remove(holder);
    }
}
