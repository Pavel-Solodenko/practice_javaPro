package academy.prog.domain.service;

import academy.prog.domain.UserOnlyLogin;
import academy.prog.domain.commands.Users;

import java.util.List;
import java.util.Scanner;

public class InputOutputInConsole {
    private final Scanner scanner = new Scanner(System.in);

    public String enterLoginProcess() {
        System.out.println("Enter your login: ");
        return scanner.nextLine();
    }

    public String enterTextProcess() {
        return scanner.nextLine();
    }

    public void printEnterMessage() {
        System.out.println("Enter your message: ");
    }

    public static void printCommandsResult(Object obj,Class<?> commandClass) {

        if (commandClass.equals(Users.class)) {
            System.out.println("Online users:");
            ((List<UserOnlyLogin>) obj).forEach(userOnlyLogin -> {
                System.out.println(userOnlyLogin.getLogin());
            });
        }

    }

    public static void printInputError() {
        System.out.println("Incorrect input!");
    }

    @Override
    protected void finalize() {
        scanner.close();
    }

}
