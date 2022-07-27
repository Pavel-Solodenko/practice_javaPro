package app;

import managers.ClientManager;
import managers.Manager;
import managers.OrderManager;
import managers.ProductManager;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.Scanner;

public class App {

    private static EntityManager entityManager;
    private static Manager manager;
    private final static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {

        createConnection();



        System.out.println("Greeting!");

        while(true) {

            System.out.println("1: add client");
            System.out.println("2: add product");
            System.out.println("3: add order");
            System.out.println("1.1: delete client");
            System.out.println("2.1: delete product");
            System.out.println("3.1: delete order");
            System.out.print("-> ");

            String option = scanner.nextLine();
            switch (option) {
                case "1" -> {
                    manager = ClientManager.getInstance();

                    manager.add();

                    System.out.println("\nComplete!");
                }

                case "2" -> {
                    manager = ProductManager.getInstance();

                    manager.add();

                    System.out.println("\nComplete!");
                }

                case "3" -> {
                    manager = OrderManager.getInstance();

                    manager.add();

                    System.out.println("\nComplete!");
                }

                case "1.1" -> {
                    manager = ClientManager.getInstance();

                    System.out.print("\nEnter id to delete -> ");
                    manager.delete(Long.parseLong(scanner.nextLine()));

                    System.out.println("Complete!");
                }

                case "2.1" -> {
                    manager = ProductManager.getInstance();

                    System.out.print("\nEnter id to delete -> ");
                    manager.delete(Long.parseLong(scanner.nextLine()));

                    System.out.println("Complete!");
                }

                case "3.1" -> {
                    manager = OrderManager.getInstance();

                    System.out.print("\nEnter id to delete -> ");
                    manager.delete(Long.parseLong(scanner.nextLine()));

                    System.out.println("Complete!");
                }

                default -> {
                    System.out.println("Exit...");
                    scanner.close();
                    System.exit(0);
                }
                //end of switch
            }


        }


    }

    private static void createConnection() {

        try {
            EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("ClientsDB");
            entityManager = entityManagerFactory.createEntityManager();
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        initManagers();

    }

    private static void initManagers() {

        manager = ClientManager.getInstance();
        manager.setScanner(scanner);
        manager.setEntityManager(entityManager);

        manager = ProductManager.getInstance();
        manager.setEntityManager(entityManager);
        manager.setScanner(scanner);

        manager = OrderManager.getInstance();
        manager.setScanner(scanner);
        manager.setEntityManager(entityManager);

        manager = null;
    }

}
