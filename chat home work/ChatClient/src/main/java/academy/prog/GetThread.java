package academy.prog;

import academy.prog.domain.Message;
import academy.prog.domain.MessageList;
import academy.prog.domain.utils.Reader;
import academy.prog.domain.utils.Utils;
import academy.prog.domain.parsers.JsonParser;
import academy.prog.domain.parsers.Parser;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class GetThread implements Runnable {
    private final String login;
    private boolean newSession = true;
    private Parser parser = new JsonParser();
    private final MessageList msgList = MessageList.getInstance();

    public GetThread(String login) {
        this.login = login;
    }

    @Override
    public void run() {
        try {
            while ( ! Thread.interrupted()) {

                try (InputStream is = openConnection()) {
                    byte[] buf = Reader.responseBodyToArray(is);
                    String strBuf = new String(buf, StandardCharsets.UTF_8);
                    if (strBuf.equals("null")) continue;

                    if (newSession) {
                        @SuppressWarnings("unchecked")
                        List<Message> messages = (List<Message>) parser.parseFrom(strBuf, Utils.getMessageListType());
                        msgList.getList().addAll(messages);
                        msgList.getList().forEach(System.out::println);
                    } else {
                        Message message = (Message) parser.parseFrom(strBuf, Message.class);
                        if (message != null && msgList.add(message)) System.out.println(message);
                    }

                }

                if (newSession) newSession = false;
                Thread.sleep(3000);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private InputStream openConnection() throws IOException {
        URL url = new URL(Utils.getURL() + "/get?login="+login+"&newSession="+newSession);
        HttpURLConnection http = (HttpURLConnection) url.openConnection();
        return http.getInputStream();
    }

}
