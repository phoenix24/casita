package casita.supervisor;

import java.util.Objects;

public class PolicyFactory {
    public static Policy create(String conf) {
        Objects.requireNonNull(conf);

        return switch (conf.toUpperCase()) {
            case "NEVER" -> new RestartPolicy(-1);
            case "RESTART" -> new RestartPolicy(0);

            case "ONCE" -> new RestartPolicy(1);
            case "TWICE" -> new RestartPolicy(2);
            case "THRICE" -> new RestartPolicy(3);
            case "FOUR" -> new RestartPolicy(4);
            case "FIVE" -> new RestartPolicy(5);

            default -> throw new RuntimeException("invalid actor policy specified");
        };
    }
}
