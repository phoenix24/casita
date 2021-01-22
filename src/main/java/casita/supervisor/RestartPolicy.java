package casita.supervisor;

public class RestartPolicy implements Policy {
    private int count;
    private final int threshold;
    private final Object lock = new Object();

    public RestartPolicy(int threshold) {
        this.count = 0;
        this.threshold = threshold;
    }

    public boolean shouldRestart() {
        if (threshold == 0) return true;
        if (threshold == -1) return false;

        synchronized (lock) {
            this.count += 1;
        }
        return this.count < this.threshold;
    }
}
