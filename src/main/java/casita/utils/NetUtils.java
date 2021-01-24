package casita.utils;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class NetUtils {

    public static String getHostAddress() {
        try {
            return InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        return null;
    }
}
