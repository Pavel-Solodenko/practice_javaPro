package academy.prog.domain.lists;

import academy.prog.domain.Message;

import java.util.LinkedList;
import java.util.List;

public class ServerMessageList {
	private static final ServerMessageList msgList = new ServerMessageList();

	private final List<Message> list = new LinkedList<>();
	
	public static ServerMessageList getInstance() {
		return msgList;
	}
  
  	private ServerMessageList() {}
	
	public synchronized void add(Message m) {
		list.add(m);
	}

	public List<Message> getList() {
		return list;
	}

	public synchronized Message getRecent() {
		if (list.isEmpty()) return null;
		return list.get(list.size()-1);
	}
}
