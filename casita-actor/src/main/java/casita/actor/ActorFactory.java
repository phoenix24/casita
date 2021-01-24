package casita.actor;

import casita.actorsystem.ActorConf;
import casita.actorsystem.ActorSystem;

import java.lang.reflect.InvocationTargetException;
import java.util.Objects;

public class ActorFactory {
    public static Actor create(ActorConf conf, ActorSystem system) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        String path = conf.getPath();
        Class<? extends Actor> klass = conf.getKlass();

        Objects.requireNonNull(path, "actor path cannot be null");
        Objects.requireNonNull(klass, "actor class cannot be null");
        Objects.requireNonNull(system, "actor system cannot be null");

        return klass
                .getDeclaredConstructor(ActorSystem.class, String.class)
                .newInstance(system, path);
    }
}
