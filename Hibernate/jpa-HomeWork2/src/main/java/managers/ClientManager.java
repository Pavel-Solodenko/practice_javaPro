package managers;

import domain.Client;
import javax.persistence.EntityManager;
import java.util.Scanner;

public class ClientManager implements Manager{

    private static final ClientManager clientManager = new ClientManager();
    private Scanner scanner;
    private EntityManager entityManager;

    public static ClientManager getInstance() {
        return clientManager;
    }

    private ClientManager() {}

    @Override
    public void add() {

        entityManager.getTransaction().begin();
        try{
            Client clientToAdd = createClientWithUserInput();

            entityManager.persist(clientToAdd);
            entityManager.getTransaction().commit();
        }
        catch (Exception e) {
            entityManager.getTransaction().rollback();
            e.printStackTrace();
        }


    }

    @Override
    public void delete(long id) {

        Client clientToDelete = entityManager.getReference(Client.class,id);

        if(clientToDelete == null) {
            System.out.println("Client not found");
            return;
        }

        entityManager.getTransaction().begin();

        try {

            entityManager.remove(clientToDelete);
            entityManager.getTransaction().commit();

        }
        catch (Exception e) {
            entityManager.getTransaction().rollback();
            e.printStackTrace();
        }

    }

    public Client createClientWithUserInput() {

        Client client = new Client();

        System.out.print("\nEnter client name -> ");
        client.setClientName(scanner.nextLine());

        System.out.print("\nEnter client phone number -> ");
        client.setTelephoneNumber(validatePhoneNumber(scanner.nextLine()));

        System.out.print("\nEnter client address -> ");
        String clientHomeAddress = scanner.nextLine();


        if (clientHomeAddress.equals(" ") || clientHomeAddress.isEmpty())
            clientHomeAddress = null;


        client.setHomeAddress(clientHomeAddress);

        System.out.print("\nEnter client sex(m/f) -> ");
        client.setSex(validateSex(scanner.nextLine()));

        System.out.print("\nEnter client age -> ");
        client.setAge(validateAge(scanner.nextLine()));

        return client;
    }

    private String validatePhoneNumber(String phoneNumber) {

        try {

            if (phoneNumber.length() < 12)  //!=
                throw new IllegalArgumentException();

        }
        catch (IllegalArgumentException e) {
            System.out.print("Invalidate phone number!\nTry again- > ");
            return validatePhoneNumber(scanner.nextLine());
        }

        return phoneNumber;
    }

    private char validateSex(String sex) {

        char result;

        try {
            char[] strAsCharArr = sex.toCharArray();

            if (strAsCharArr.length != 1)
                throw new IllegalArgumentException();

            result = strAsCharArr[0];

            if (result != 'm')
                if (result != 'f')
                    throw new IllegalArgumentException();

        }
        catch (IllegalArgumentException e) {
            System.out.print("Invalidate sex!\nTry again -> ");
            return validateSex(scanner.nextLine());
        }

        return result;
    }

    private byte validateAge(String age) {

        byte result = 0;

        try {
            result = Byte.parseByte(age);

            if (result < 0)
                throw new NumberFormatException();

        }
        catch (NumberFormatException e) {
            System.out.print("Invalidate age!\nTry again -> ");
            return validateAge(scanner.nextLine());
        }

        return result;
    }

    public Scanner getScanner() {
        return scanner;
    }

    @Override
    public void setScanner(Scanner scanner) {
        this.scanner = scanner;
    }

    public EntityManager getEntityManager() {
        return entityManager;
    }

    @Override
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }
}
