package academy.prog.domain.lists;

import academy.prog.domain.UserOnlyLogin;
import java.util.LinkedList;
import java.util.List;

public class UserList {
    private static final UserList userList = new UserList();

    private final List<UserOnlyLogin> list = new LinkedList<>();;

    private UserList() {}

    public static UserList getInstance() {
        return userList;
    }

    public synchronized void addUser(UserOnlyLogin user) {
        list.add(user);
    }

    public synchronized boolean containsUser(UserOnlyLogin user) {
        for (UserOnlyLogin currentUser : list) {
            if (currentUser.getLogin().equals(user.getLogin())) {
                return true;
            }
        }
        return false;
    }

    public synchronized UserOnlyLogin getUser(UserOnlyLogin user) {
        UserOnlyLogin result = null;
        for (UserOnlyLogin currentUser : list) {
            if(currentUser.getLogin().equals(user.getLogin())) {
                result = currentUser;
                break;
            }
        }
        return result;
    }

    public List<UserOnlyLogin> getUserList() {
        return list;
    }
}
