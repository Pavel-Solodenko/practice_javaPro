package academy.prog;

import academy.prog.domain.Message;
import academy.prog.domain.service.*;

public class Main {
	private static final InputOutputInConsole interaction= new InputOutputInConsole();
	private static final MessageManager msgManager = new ConcreteMessageManager();
    private static final CommandManager commandManager = new ConcreteCommandManager();

	public static void main(String[] args) {
		final String login = interaction.enterLoginProcess();

		Thread th = new Thread(new GetThread(login));
		th.setDaemon(true);
		th.start();

		interaction.printEnterMessage();
		while (true) {
			String text = interaction.enterTextProcess();
			if (text.isEmpty()) break;

            Message message = msgManager.createMessage(text,login);

            if (message != null) msgManager.sentMessage(message);
            else commandManager.execute(text);
		}

	}

}
