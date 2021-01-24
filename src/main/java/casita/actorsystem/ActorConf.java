package casita.actorsystem;

import casita.actor.Actor;
import lombok.Builder;
import lombok.Data;


@Data
@Builder
public class ActorConf {
    private String path;
    private String inbox;
    private String policy;
    private String context;

    private Class<? extends Actor> klass;
}
