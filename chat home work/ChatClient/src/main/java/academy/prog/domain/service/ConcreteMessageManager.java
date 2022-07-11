package academy.prog.domain.service;

import academy.prog.domain.Message;
import academy.prog.domain.UserOnlyLogin;
import academy.prog.domain.parsers.AdressatParser;
import academy.prog.domain.parsers.JsonParser;
import academy.prog.domain.parsers.Parser;
import academy.prog.domain.utils.OpenConnection;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.nio.charset.StandardCharsets;

public class ConcreteMessageManager implements MessageManager {
    private final Parser jsonParser = new JsonParser("");
    private final Parser toUserParser = new AdressatParser();

    @Override
    public Message createMessage(String text, String loginFrom) {
        if (text.charAt(0) == '/') return null;

        Message message = (Message) toUserParser.parseFrom(text, Message.class);

        if (message == null) {


            message = new Message(new UserOnlyLogin(loginFrom),new UserOnlyLogin("null"),text);
        }
        else {
            message.setFrom(new UserOnlyLogin(loginFrom));
        }

        return message;
    }

    @Override
    public void sentMessage(Message message) {
        int res = -1;
        HttpURLConnection connection;
        try {
            String jsonMessage = jsonParser.parseTo(message);
            connection = OpenConnection.openConnection("/add","POST",true);
            write(connection,jsonMessage);
            res = connection.getResponseCode();
        }

        catch (Exception e) {
            e.printStackTrace();
        }

        if (res != 200 && res != -1) { // 200 OK
            System.out.println("HTTP error occurred: " + res);
        }
    }

    private void write(HttpURLConnection conn,String jsonMessage) throws IOException{
        try (OutputStream os = conn.getOutputStream()) {
            os.write(jsonMessage.getBytes(StandardCharsets.UTF_8));
        }
    }

}
