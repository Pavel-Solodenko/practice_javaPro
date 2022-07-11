package academy.prog.domain;

import java.util.LinkedList;
import java.util.List;

public class UserOnlyLogin extends User {
    private boolean status;
    private List<Message> historyOfMessage;

    public UserOnlyLogin(String login,boolean status) {
        this.setLogin(login);
        this.status = status;
        this.historyOfMessage = new LinkedList<>();
    }

    public UserOnlyLogin(String login) {
        this.setLogin(login);
    }

    @Override
    public String toString() {
        return new StringBuilder().append(this.getLogin()).append(" :\n")
                .append(historyOfMessage).toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || this.getClass() != obj.getClass()) return false;
        UserOnlyLogin user = (UserOnlyLogin) obj;
        return (this.getLogin() != null && (this.getLogin().equals(user.getLogin()))) &&
                (this.getPassword() != null && (this.getPassword().equals(user.getPassword()))) &&
                (historyOfMessage != null && (historyOfMessage.equals(user.getHistoryOfMessage()))) &&
                (status == user.isStatus());

    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (this.getPassword() == null ? 0 : this.getPassword().hashCode());
        result = prime * result + (this.getLogin() == null ? 0 : this.getLogin().hashCode());
        result = prime * result + (historyOfMessage == null ? 0 : historyOfMessage.hashCode());
        result = prime * result + Boolean.hashCode(status);
        return result;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public List<Message> getHistoryOfMessage() {
        return historyOfMessage;
    }

    public void setHistoryOfMessage(List<Message> historyOfMessage) {
        this.historyOfMessage = historyOfMessage;
    }

}
