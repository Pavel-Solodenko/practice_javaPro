package academy.prog;

import academy.prog.domain.*;
import academy.prog.domain.lists.ServerMessageList;
import academy.prog.domain.lists.UserList;
import academy.prog.domain.parsers.JsonParser;
import academy.prog.domain.parsers.Parser;
import academy.prog.service.ConcreteUserMessageHistoryManager;
import academy.prog.service.UserMessageHistoryManager;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class AddServlet extends HttpServlet {

	private final String usrToNotExist = "no user found by this login, message will be sent if that user will be registered on server";
	private ServerMessageList msgList = ServerMessageList.getInstance();
    private Parser parser = new JsonParser();
	private UserList userList = UserList.getInstance();
	private UserMessageHistoryManager usrMsgManager = new ConcreteUserMessageHistoryManager();

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		byte[] buf = Reader.requestBodyToArray(req.getInputStream());
        String bufStr = new String(buf, StandardCharsets.UTF_8);

        Message msg =  (Message) parser.parseFrom(bufStr,Message.class);

		if (msg != null) {
			msgList.add(msg);

			UserOnlyLogin from = msg.getFrom();
			UserOnlyLogin to = msg.getTo();
			usrMsgManager.add(userList,from,msg);

			if (!usrMsgManager.add(userList,to,msg) && !to.getLogin().equals("null")) {
				//no user found by this login, message will be sent if that user will be registered on server
				resp.getOutputStream().write(usrToNotExist.getBytes(StandardCharsets.UTF_8));
			}

        }
		else
			resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
	}

}
