package academy.prog.service;

import academy.prog.domain.Message;
import academy.prog.domain.lists.ServerMessageList;

import java.util.List;

public interface MessageManager {
    List<Message> get(ServerMessageList serverMessageList,String login);
}
