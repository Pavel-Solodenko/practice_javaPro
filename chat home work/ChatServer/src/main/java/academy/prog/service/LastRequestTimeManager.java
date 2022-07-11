package academy.prog.service;

import java.util.Date;

public interface LastRequestTimeManager {
    void processRequestDate(String userLogin,Date date);
    void addUserToLastRequestHistory(String userLogin, Date date);
    void updateUserLastRequestHistory(String userLogin,Date date);
}
