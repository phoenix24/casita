package casita.inbox;

import java.util.concurrent.ArrayBlockingQueue;

public class InboxInMemory implements Inbox {

    public ArrayBlockingQueue<Object> queue;

    public InboxInMemory(int capacity) {
        this.queue = new ArrayBlockingQueue<>(capacity);
    }

    @Override
    public void add(Object message) {
        this.queue.offer(message);
    }

    @Override
    public Object get() {
        return this.queue.poll();
    }

    @Override
    public int size() {
        return this.queue.size();
    }
}
