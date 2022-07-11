package academy.prog.service;

import academy.prog.domain.LastRequestTime;
import academy.prog.domain.UserOnlyLogin;
import academy.prog.domain.lists.UserList;

import java.util.Date;
import java.util.LinkedList;

public class ConcreteLastRequestTimeManager implements LastRequestTimeManager {
    private final UserList userList = UserList.getInstance();
    private final LastRequestTime lastRequestTime = LastRequestTime.getInstance();

    @Override
    public void processRequestDate(String userLogin,Date date) {

        if (userList.containsUser(new UserOnlyLogin(userLogin))) {
            updateUserLastRequestHistory(userLogin,date);
        }

        else {
            addUserToLastRequestHistory(userLogin,date);
        }
    }

    @Override
    public void addUserToLastRequestHistory(String userLogin, Date date) {
        UserOnlyLogin user = new UserOnlyLogin(userLogin,true);
        user.setHistoryOfMessage(new LinkedList<>());

        userList.addUser(user);

        lastRequestTime.add(userLogin,date);
    }

    @Override
    public void updateUserLastRequestHistory(String userLogin, Date date) {
        lastRequestTime.update(userLogin,date);

//        UserOnlyLogin updateUser = userList.getUser(new UserOnlyLogin(userLogin));
//        if (!updateUser.isStatus()) {
//            updateUser.setStatus(true);
//        }
    }


}
