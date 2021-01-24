package casita.actorsystem;

import lombok.Data;

@Data
public class ActorPath {
    private final String name;

    private ActorPath(String name) {
        this.name = name;
    }

    public static ActorPath create(String name) {
        return new ActorPath(name);
    }
}
