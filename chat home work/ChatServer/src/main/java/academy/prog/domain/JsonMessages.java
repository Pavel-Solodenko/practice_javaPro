/*
package academy.prog.domain;

import java.util.ArrayList;
import java.util.List;

public class JsonMessages {
    private final List<Message> list = new ArrayList<>();

    public JsonMessages(List<Message> sourceList, int fromIndex,String login) {
        getJsonMessages(sourceList,fromIndex,login);
    }

    private void getJsonMessages(List<Message> sourceList, int fromIndex,String login) {
        for (int i = fromIndex; i < sourceList.size(); i++) {
            System.out.println("Source size: "+sourceList.size()+"formIndex: "+fromIndex);
            Message workMessage = sourceList.get(i);
            if (workMessage.getTo() != null && !workMessage.getTo().equals(login)) {
                continue;
            }
            else {
                list.add(workMessage);
            }
        }
    }
}
*/
