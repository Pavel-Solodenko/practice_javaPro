package academy.prog.domain.utils;

import academy.prog.domain.Message;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class Utils {
    private static final String URL = "http://127.0.0.1";
    private static final int PORT = 8080;
    private static final Type messageListType = new TypeToken<List<Message>>() {}.getType();
    public static Type getMessageListType() {return messageListType;}
    public static String getURL() {
        return URL + ":" + PORT;
    }
}
