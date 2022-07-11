package academy.prog.domain.commands;

import academy.prog.domain.UserOnlyLogin;
import academy.prog.domain.parsers.JsonParser;
import academy.prog.domain.parsers.Parser;
import academy.prog.domain.utils.OpenConnection;
import academy.prog.domain.utils.Reader;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class Users implements Command{

    private final Parser jsonParser = new JsonParser("");
    private final Type listOfOnlineUsersLogin = new TypeToken<List<UserOnlyLogin>> () {}.getType();

    @Override
    public Object execute() throws IOException{
        List<UserOnlyLogin> onlineUsersLogins = null;
        HttpURLConnection connection = OpenConnection.openConnection("/users","GET",true);
        String json = null;
        try (InputStream is =connection.getInputStream()) {
            json =  new String(Reader.responseBodyToArray(is), StandardCharsets.UTF_8);
        }

        if (json != null) {
            onlineUsersLogins = (List<UserOnlyLogin>) jsonParser.parseFrom(json,listOfOnlineUsersLogin);
        }

        return onlineUsersLogins;
    }


}
