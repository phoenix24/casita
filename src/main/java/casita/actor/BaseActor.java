package casita.actor;


import casita.exceptions.ActorShutException;
import lombok.Getter;

import java.util.Deque;
import java.util.Queue;
import java.util.UUID;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public abstract class BaseActor implements Actor {

    private final String id;
    private final String name;

    @Getter //note: stateful
    private boolean isShutdown;

    private ArrayBlockingQueue<Object> inbox;
    private ExecutorService executor = Executors.newFixedThreadPool(10);

    public BaseActor(String name) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.inbox = new ArrayBlockingQueue<>(100);
        this.isShutdown = true;
//        start();
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public boolean isShutdown() {
        return isShutdown;
    }

    @Override
    public void receive(Object message) {

        if (!this.isShutdown) {
            throw new ActorShutException("sending message to an already shutdown actor");
        }

        //todo: add future message or state sanitization
//        this.inbox.offer(message);
        this.receiveMessage(message);
    }

//    private void start() {
//        final Actor actor = this;
//        executor.submit(new Runnable() {
//            @Override
//            public void run() {
//                actor.receiveMessage(actor.);
//            }
//        });
//    }

    public void shutdown() {
        this.isShutdown = false;
    }
}
