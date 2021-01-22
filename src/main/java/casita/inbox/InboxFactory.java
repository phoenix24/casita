package casita.inbox;

import java.util.Objects;

public class InboxFactory {
    public static Inbox create(String conf) {
        Objects.requireNonNull(conf);

        return switch (conf.toUpperCase()) {
            case "DB" -> throw new RuntimeException("inbox not implemented");
            case "INMEMORY" -> new InboxInMemory(100);
            default -> throw new RuntimeException("invalid inbox specified");
        };
    }
}
