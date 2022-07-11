package academy.prog.service;

import academy.prog.domain.LastRequestTime;
import academy.prog.domain.Message;
import academy.prog.domain.lists.UserList;
import academy.prog.domain.UserOnlyLogin;
import academy.prog.domain.utils.Utils;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class ConcreteUserMessageHistoryManager implements UserMessageHistoryManager{

    private final LastRequestTime lastRequestTime = LastRequestTime.getInstance();

    public ConcreteUserMessageHistoryManager() {}


    @Override
    public synchronized boolean add(UserList userList, UserOnlyLogin user, Message message) {
        if (userList.containsUser(user)) {
            userList.getUser(user).getHistoryOfMessage().add(message);
                return true;
        }

        List<Message> messages = user.getHistoryOfMessage();
        if (messages == null) {
            messages = new LinkedList<>();
            messages.add(message);
            user.setHistoryOfMessage(messages);
        }

        if (!user.getLogin().equals("null")) {
            user.setStatus(false);
            userList.getUserList().add(user);
            Date dateToSet = new Date(new Date().getTime() - (Utils.getTimeToChangeStatus() * 1000));

            lastRequestTime.add(user.getLogin(),dateToSet);
        }


        return false;
    }

    @Override
    public List<Message> get(UserList userList, String login) {
        List<Message> result = new ArrayList<>();
        UserOnlyLogin workUser = userList.getUser(new UserOnlyLogin(login));

        if (workUser == null) {
            return result;
        }

        return workUser.getHistoryOfMessage();
    }
}
