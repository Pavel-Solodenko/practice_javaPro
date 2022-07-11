package academy.prog.service;

import academy.prog.domain.LastRequestTime;
import academy.prog.domain.UserOnlyLogin;
import academy.prog.domain.lists.UserList;
import academy.prog.domain.utils.Utils;

import java.util.*;

public class ConcreteStatusManager implements StatusManager {
    private final LastRequestTime lastRequestTime = LastRequestTime.getInstance();
    private final UserList userList = UserList.getInstance();

    @Override
    public void execute() {
        changeStatus();
    }

    private void changeStatus() {

        Map<String, Date> workMap = lastRequestTime.getUserDateMap();
        Set<String> loginSet = workMap.keySet();


        for (String currentUserLogin : loginSet) {
            Date dateToCheck = workMap.get(currentUserLogin);


            UserOnlyLogin userToChangeStatus = userList.getUser(new UserOnlyLogin(currentUserLogin));

            if (!checkTime(dateToCheck)) {
                userToChangeStatus.setStatus(false);
            }

            else {
                if(!userToChangeStatus.isStatus()) userToChangeStatus.setStatus(true);
            }

        }

    }

    private boolean checkTime(Date dateToCheck) {
        Date currentDate = new Date();
        long currentTimeMillis = currentDate.getTime();
        long dateToCheckMillis = dateToCheck.getTime();
        long result = (currentTimeMillis - dateToCheckMillis) / 1000;

        if (result >= Utils.getTimeToChangeStatus()) {

            return false;
        }

        return true;
    }

/*    private void changeUserStatusInList(List<UserOnlyLogin> userListToChange) {
        for (UserOnlyLogin currentUserFromUserList : userList.getUserList()) {

            for (UserOnlyLogin currentUserFromUserListToChange : userListToChange) {

                if (currentUserFromUserList.getLogin().
                        equals(currentUserFromUserListToChange.getLogin()) ) {
                    currentUserFromUserListToChange.setStatus(false);
                }

            }

        }
    }*/

}
