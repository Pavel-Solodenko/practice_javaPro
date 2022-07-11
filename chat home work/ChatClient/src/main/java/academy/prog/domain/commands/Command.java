package academy.prog.domain.commands;

import java.io.IOException;

public interface Command {
    Object execute() throws IOException;
}
