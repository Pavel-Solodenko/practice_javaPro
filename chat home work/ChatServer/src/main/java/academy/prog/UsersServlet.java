package academy.prog;

import academy.prog.domain.UserOnlyLogin;
import academy.prog.domain.lists.UserList;
import academy.prog.domain.parsers.JsonParser;
import academy.prog.domain.parsers.Parser;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class UsersServlet extends HttpServlet {
    private final UserList userList = UserList.getInstance();
    private Parser parser = new JsonParser("");

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        List<UserOnlyLogin> result = new ArrayList<>();

        for (UserOnlyLogin currentUser : userList.getUserList()) {
            if (currentUser.isStatus()) {
                result.add(currentUser);
            }
        }

        String json = parser.parseTo(result);

        try (OutputStream os = response.getOutputStream()) {
            os.write(json.getBytes(StandardCharsets.UTF_8));
        }

    }

}
