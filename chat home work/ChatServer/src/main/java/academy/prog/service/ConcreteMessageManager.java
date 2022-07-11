package academy.prog.service;

import academy.prog.domain.Message;
import academy.prog.domain.lists.ServerMessageList;
import java.util.LinkedList;
import java.util.List;

public class ConcreteMessageManager implements MessageManager{
    @Override
    public List<Message> get(ServerMessageList serverMessageList,String login) {

        List<Message> result = new LinkedList<>();

        for (Message currentMessage : serverMessageList.getList()) {
            if(!currentMessage.getFrom().getLogin().equals(login)
            && currentMessage.getTo().getLogin().equals("null"))
                result.add(currentMessage);
        }

        return result;
    }
}
