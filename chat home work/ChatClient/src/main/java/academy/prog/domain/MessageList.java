package academy.prog.domain;

import java.util.LinkedList;
import java.util.List;

public class MessageList {
    private static final MessageList msgList = new MessageList();

    private final List<Message> list = new LinkedList<>();

    private MessageList() {}

    public static MessageList getInstance() {
        return msgList;
    }

    public boolean add(Message message) {
        for (Message currentMessage : list) {
            if ((currentMessage.getDate().compareTo(message.getDate())) == 0 &&
                    (currentMessage.getFrom().getLogin().equals(message.getFrom().getLogin())) &&
                    (currentMessage.getTo().getLogin().equals(message.getTo().getLogin())) &&
                    (currentMessage.getText().equals(message.getText()))
            ) return false;
        }
        list.add(message);
        return true;
    }

    public List<Message> getList() {
        return list;
    }



}
