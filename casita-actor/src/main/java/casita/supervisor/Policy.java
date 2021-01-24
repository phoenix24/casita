package casita.supervisor;

public interface Policy {
    public boolean canExecute();
    public boolean shouldRestart();
}
