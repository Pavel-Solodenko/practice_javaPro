package academy.prog;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import academy.prog.domain.*;
import academy.prog.domain.lists.ServerMessageList;
import academy.prog.domain.lists.UserList;
import academy.prog.domain.parsers.JsonParser;
import academy.prog.domain.parsers.Parser;
import academy.prog.service.*;
import academy.prog.threads.OnlineTimeWatcher;
import jakarta.servlet.http.*;

public class GetListServlet extends HttpServlet {
	
	private ServerMessageList msgList = ServerMessageList.getInstance();
	private Parser parser = new JsonParser("");
	private UserMessageHistoryManager usrMsgManager = new ConcreteUserMessageHistoryManager();
	private UserList userList = UserList.getInstance();
	private MessageManager msgManager = new ConcreteMessageManager();
	private LastRequestTimeManager lastRequestTimeManager = new ConcreteLastRequestTimeManager();
	private final Thread watcher = new Thread(OnlineTimeWatcher.getInstance());

    @Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {

		String login = req.getParameter("login");
		boolean newSession = Boolean.parseBoolean(req.getParameter("newSession"));

		if (login == null) {
			resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			return;
		}

		////

		lastRequestTimeManager.processRequestDate(login,new Date());

		if (!watcher.isAlive()) {
			watcher.setDaemon(true);
			watcher.start();
		}

		////

		resp.setContentType("application/json");

		String json = null;

		if (newSession == true) {
			List<Message> messagesToSent = msgManager.get(msgList,login);
			messagesToSent.addAll(usrMsgManager.get(userList,login));
			messagesToSent.sort(Comparator.comparing(Message::getDate));
			json = parser.parseTo(messagesToSent);
		}

		else {
			Message workMessage = msgList.getRecent();
			if (workMessage != null && ((workMessage.getTo().getLogin().equals(login)) ||
					(workMessage.getTo().getLogin().equals("null")) ||
					(workMessage.getFrom().getLogin().equals(login)))  )
				json = parser.parseTo(workMessage);
		}

		if (json != null) {
			resp.getOutputStream().write(json.getBytes(StandardCharsets.UTF_8));
		}
	}
}
