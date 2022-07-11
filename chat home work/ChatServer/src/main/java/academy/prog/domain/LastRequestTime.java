package academy.prog.domain;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class LastRequestTime {
    private static final LastRequestTime lastRqTime = new LastRequestTime();

    private final Map<String, Date> userDateMap = new HashMap<>();

    public static LastRequestTime getInstance() {
        return lastRqTime;
    }

    private LastRequestTime() {}

    public synchronized void add(String userLogin,Date date) {
        userDateMap.put(userLogin,date);
    }

    public synchronized void update(String userLogin,Date date) {
        userDateMap.replace(userLogin,date);
    }

    public synchronized boolean containsUser(String userLogin) {
        Set<String> logins = userDateMap.keySet();
        for (String currentLogin : logins) {
            if (currentLogin.equals(userLogin)) return true;
        }

        return false;
    }


    public Map<String, Date> getUserDateMap() {
        return userDateMap;
    }
}
