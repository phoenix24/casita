package casita.actorsystem;

import casita.actor.Actor;
import casita.actor.ActorFactory;
import casita.exception.ActorCreationException;
import casita.inbox.Inbox;
import casita.inbox.InboxFactory;
import casita.supervisor.Policy;
import casita.supervisor.PolicyFactory;
import lombok.Builder;
import lombok.Data;

import static java.util.Objects.requireNonNull;

@Data
@Builder
public class ActorHolder {
    private Inbox inbox;
    private Policy policy;

    private Actor actor;
    private ActorPath actorPath;

    public static ActorHolder create(ActorSystem system, ActorConf conf) {
        try {
            return ActorHolder.builder()
                    .actor(ActorFactory.create(requireNonNull(conf), system))
                    .inbox(InboxFactory.create(requireNonNull(conf.getInbox())))
                    .policy(PolicyFactory.create(requireNonNull(conf.getPolicy())))
                    .actorPath(ActorPath.create(requireNonNull(conf.getPath())))
                    .build();

        } catch (Exception e) {
            e.printStackTrace();
            throw new ActorCreationException("failed to create actor:" + conf.getPath());
        }
    }

    public void execute(final Object message) {
        while (this.policy.canExecute()) {
            try {
                this.actor.receive(message);
                return;

            } catch (Exception e) {
                System.err.println("actor execution exception: " + e.getMessage());
                boolean restart = this.policy.shouldRestart();
                System.err.println("actor supervision policy restart? " + restart);
            }
        }
    }
}
