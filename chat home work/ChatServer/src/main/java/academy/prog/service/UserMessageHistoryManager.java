package academy.prog.service;

import academy.prog.domain.Message;
import academy.prog.domain.lists.UserList;
import academy.prog.domain.UserOnlyLogin;

import java.util.List;

public interface UserMessageHistoryManager {
    boolean add(UserList userList, UserOnlyLogin user, Message message);
    List<Message> get(UserList userList, String login);
}
