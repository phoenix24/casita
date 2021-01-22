package casita.actorsystem;

import casita.actor.Actor;
import casita.actor.ActorFactory;
import casita.exceptions.ActorCreationException;
import casita.exectution.ExecutionContext;
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
    private Actor actor;
    private Inbox inbox;
    private Policy policy;
    private Address address;

    private ActorHolder(Actor actor, Inbox inbox, Policy policy, Address address) {
        this.actor = actor;
        this.inbox = inbox;
        this.policy = policy;
        this.address = address;
    }

    public static ActorHolder create(ActorSystem system, ActorConf conf) {
        try {
            return ActorHolder.builder()
                    .actor(ActorFactory.create(requireNonNull(conf), system))
                    .inbox(InboxFactory.create(requireNonNull(conf.getInbox())))
                    .policy(PolicyFactory.create(requireNonNull(conf.getPolicy())))
                    .address(Address.create(requireNonNull(conf)))
                    .build();


        } catch (Exception e) {
            e.printStackTrace();
            throw new ActorCreationException("failed to create actor:" + conf.getName());
        }
    }
}
