package casita.exceptions;

public class ActorExistsException extends RuntimeException {
    public ActorExistsException(String message) {
        super(message);
    }
}
