package academy.prog.domain.service;

import academy.prog.domain.Message;

public interface MessageManager {
    void sentMessage(Message message);
    Message createMessage(String text,String loginFrom);
}
