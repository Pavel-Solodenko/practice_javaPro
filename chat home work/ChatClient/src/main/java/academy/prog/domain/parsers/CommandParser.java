package academy.prog.domain.parsers;

import academy.prog.domain.commands.Command;
import academy.prog.domain.commands.Users;
import academy.prog.domain.service.InputOutputInConsole;

import java.lang.reflect.Type;

public class CommandParser implements Parser{

    @Override
    public String parseTo(Object inputObj) {
        return null;
    }

    @Override
    public Object parseFrom(String text, Class<?> objClass) {
        if (objClass != Command.class) return null;

        Command command = null;
        if (text.charAt(0) == '/') {
            text = text.substring(1);
            switch (text) {
                case "users":
                    command = new Users();
                    break;
                default:
                    InputOutputInConsole.printInputError();
            }
        }

        return command;
    }

    @Override
    public Object parseFrom(String text, Type type) {
        return null;
    }
}
