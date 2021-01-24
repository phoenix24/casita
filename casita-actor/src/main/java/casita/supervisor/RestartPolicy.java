package casita.supervisor;

import java.util.concurrent.atomic.AtomicInteger;

public class RestartPolicy implements Policy {
    private AtomicInteger count;
    private final int threshold;

    public RestartPolicy(int threshold) {
        this.count = new AtomicInteger(0);
        this.threshold = threshold;
    }

    @Override
    public boolean canExecute() {
        if (threshold == 0) return true;
        if (threshold == -1) return false;
        return this.count.get() < this.threshold;
    }

    public boolean shouldRestart() {
        if (threshold == 0) return true;
        if (threshold == -1) return false;
        return this.count.incrementAndGet() < this.threshold;
    }
}
