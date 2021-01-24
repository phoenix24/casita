package casita.utils;

import java.util.concurrent.ThreadFactory;
import java.util.function.Function;

public class ThreadUtils {

    public static Function<String, ThreadFactory> tFactory = name -> runnable -> new Thread(runnable, name);
}
