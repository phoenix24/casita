package casita.actorsystem;

import lombok.Data;

@Data
public class Address {
    private final String name;
    private final String host;
    private final String port;

    private Address(String name, String host, String port) {
        this.name = name;
        this.port = port;
        this.host = host;
    }

    public static Address create(ActorConf conf) {
        return new Address(conf.getName(), "localhost", "");
    }

    @Override
    public String toString() {
        return name + "@" + host + ":" + port;
    }
}
