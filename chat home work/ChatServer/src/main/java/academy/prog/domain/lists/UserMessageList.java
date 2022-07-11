/*
package academy.prog.domain;

import java.util.LinkedList;
import java.util.List;

public class UserMessageList {
    private List<Message> messageList;

    public UserMessageList() {
        this.messageList = new LinkedList<>();
    }

    public void addMessage(Message message) {
        messageList.add(message);
    }

    public boolean containstMessage(Message message) {
        return messageList.contains(message);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        UserMessageList messages = (UserMessageList) obj;
        return (messageList != null && (messageList.equals(messages.getMessageList())));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (messageList == null ? 0 : messageList.hashCode());
        return result;
    }

    public List<Message> getMessageList() {
        return messageList;
    }

    public void setMessageList(List<Message> messageList) {
        this.messageList = messageList;
    }
}
*/
