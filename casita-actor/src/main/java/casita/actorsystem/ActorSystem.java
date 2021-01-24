package casita.actorsystem;

import casita.actor.Actor;
import casita.exception.DuplicateActorException;
import casita.exception.BadActorException;
import casita.exectution.ExecutionContext;
import casita.exectution.ThreadPoolContext;
import casita.inbox.Inbox;
import casita.utils.NetUtils;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import lombok.Getter;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public final class ActorSystem {

    @Getter
    private final String name;

    @Getter
    private final String hostname;

    private final Map<ActorPath, ActorHolder> actors;
    private final Map<ActorPath, ExecutionContext> actorContexts;
    private final Map<String, ExecutionContext> executionContexts;
    private final static ExecutionContext EXEC_DEFAULT = ThreadPoolContext.create();

    private ActorSystem(String name, List<ExecutionContext> contexts) {
        this.name = name;
        this.hostname = NetUtils.getHostAddress();

        this.actors = Maps.newConcurrentMap();
        this.actorContexts = Maps.newConcurrentMap();
        this.executionContexts = contexts.stream()
                            .collect(Collectors.toMap(ExecutionContext::getName, ex -> ex));
    }

    public static ActorSystem create(String name, List<ExecutionContext> contexts) {
        ImmutableList<ExecutionContext> _contexts = ImmutableList.<ExecutionContext>builder()
                .addAll(contexts)
                .add(EXEC_DEFAULT)
                .build();
        return new ActorSystem(name, _contexts);
    }

    public static ActorSystem create(String name) {
        return create(name, Lists.newArrayList());
    }

    public Actor createActor(ActorConf conf) {
        Objects.requireNonNull(conf, "actor conf cannot be null");

        ActorPath path = ActorPath.create(conf.getPath());
        if (this.actors.containsKey(path)) {
            throw new DuplicateActorException("a duplicate actor of same path already exists");
        }

        ActorHolder holder = ActorHolder.create(this, conf);
        ActorPath actorPath = holder.getActorPath();

        ExecutionContext context = conf.getContext() == null
                ? EXEC_DEFAULT
                : this.executionContexts.get(conf.getContext());

        context.addInbox(holder);
        this.actors.put(actorPath, holder);
        this.actorContexts.put(actorPath, context);
        return holder.getActor();
    }

    public void shutdownActor(final ActorHolder holder) {
        ActorPath path = holder.getActorPath();
        synchronized (path) {
            ExecutionContext context = this.actorContexts.get(path);
            context.removeInbox(holder);
            this.actors.remove(path);
            this.actorContexts.remove(path);
        }
    }

    public void send(final ActorPath path, final Object message) {
        //todo: extend for actor lifecycle message.
        //todo: extend for remote actor.
        //todo: this.actors or inbox for remove are not available.

        if (Objects.isNull(path) || !this.actors.containsKey(path)) {
            throw new BadActorException("invalid actor: bad actor specified");
        }

        ActorHolder holder = this.actors.get(path);
        Actor actor = holder.getActor();
        if (Objects.isNull(actor) || !actor.isAlive()) {
            throw new BadActorException("invalid actor: already shut or died");
        }

        Inbox inbox = holder.getInbox();
        inbox.add(message);
    }

    public void send(Actor actor, Object message) {
        send(actor.getPath(), message);
    }

    public void send(String path, Object message) {
        send(ActorPath.create(path), message);
    }
}
