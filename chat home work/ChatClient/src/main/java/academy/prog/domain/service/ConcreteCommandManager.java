package academy.prog.domain.service;

import academy.prog.domain.commands.Command;
import academy.prog.domain.parsers.CommandParser;
import academy.prog.domain.parsers.Parser;

import java.io.IOException;

public class ConcreteCommandManager implements CommandManager{
    private final Parser commandParser = new CommandParser();

    @Override
    public void execute(String textCommand) {
        Object result = null;
        Command command = (Command) commandParser.parseFrom(textCommand,Command.class);

        if (command != null) {

            try {
                result = command.execute();
            }

            catch (IOException e) {
                e.printStackTrace();
            }

        }

//        System.out.println("result: "+result);

        if (result != null) {
            InputOutputInConsole.printCommandsResult(result,command.getClass());
        }

    }

}
