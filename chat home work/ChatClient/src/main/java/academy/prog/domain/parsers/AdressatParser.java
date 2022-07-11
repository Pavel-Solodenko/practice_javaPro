package academy.prog.domain.parsers;

import academy.prog.domain.Message;
import academy.prog.domain.UserOnlyLogin;

import java.lang.reflect.Type;

public class AdressatParser implements Parser{
    @Override
    public String parseTo(Object inputObj) {
        return null;
    }

    @Override
    public Object parseFrom(String text, Class<?> objClass) {
        if (objClass != Message.class) return null;
        Message result = new Message();
        if (text.charAt(0) == '@' && text.contains(" ")) {
            StringBuilder sb = new StringBuilder(text);
            result.setTo(new UserOnlyLogin(sb.substring(1,sb.indexOf(" "))));
            result.setText(sb.substring(sb.indexOf(" ")+1));
            return result;
        }

        return null;
    }

    @Override
    public Object parseFrom(String text, Type type) {
        return null;
    }
}
