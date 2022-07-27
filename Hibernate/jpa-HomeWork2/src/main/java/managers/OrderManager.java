package managers;

import domain.Client;
import domain.Order;
import domain.Product;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Scanner;

public class OrderManager implements Manager {

    private static final OrderManager orderManager = new OrderManager();
    private EntityManager entityManager;
    private Scanner scanner;

    public static OrderManager getInstance() {
        return orderManager;
    }

    private OrderManager() {}


    @Override
    public void add() {

        Order orderToAdd = createOrderWithUserInput();

        addingProductsToOrder(orderToAdd);

        calculateOrderPrice(orderToAdd);

        System.out.print("\nEnter client id -> ");
        long clientId = Long.parseLong(scanner.nextLine());

        Client client = getExistenceClientInDb(clientId);

        if (client == null)
            addingIfClientNotExists(orderToAdd);
        else
            addingIfClientExists(orderToAdd,client);


    }

    @Override
    public void delete(long id) {

        Order orderToDelete = entityManager.getReference(Order.class,id);

        if (orderToDelete == null) {
            System.out.println("Order not found");
            return;
        }

        entityManager.getTransaction().begin();

        try {

            orderToDelete.getClient().removeOrder(orderToDelete);
            orderToDelete.clearProductSet();

            entityManager.remove(orderToDelete);
            entityManager.getTransaction().commit();

        }
        catch (Exception e) {
            entityManager.getTransaction().rollback();
            e.printStackTrace();
        }

    }

    private void addingProductsToOrder(Order orderToAdd) {

        while (true) {

            System.out.print("\nEnter product id -> ");
            long productId = Long.parseLong(scanner.nextLine());

            Product product = getExistenceProductInDb(productId);

            if (product == null) {
                productId = addProductIfNotExists();
                product = entityManager.getReference(Product.class,productId);
            }

            orderToAdd.addProduct(product);

            System.out.print("\nDo you want to continue adding products? (y/n)\n-> ");
            if (scanner.nextLine().equals("n"))
                break;

        }

    }

    private long addProductIfNotExists() {
        System.out.println("Product does not exist in database, please create");

        Product product = ProductManager.getInstance().createProductWithUserInput();

        entityManager.getTransaction().begin();

        try{

            entityManager.persist(product);
            entityManager.getTransaction().commit();
            return product.getId();
        }
        catch (Exception e) {
            entityManager.getTransaction().rollback();
            e.printStackTrace();
            throw new RuntimeException();
        }

    }

    private void addingIfClientExists(Order orderToAdd, Client client) {

        entityManager.getTransaction().begin();

        try {

            client.addOrder(orderToAdd);
            orderToAdd.setClient(client);

            entityManager.persist(orderToAdd);

            entityManager.getTransaction().commit();

        }
        catch (Exception e) {
            entityManager.getTransaction().rollback();
            e.printStackTrace();
        }

    }

    private void addingIfClientNotExists(Order orderToAdd) {
        System.out.println("Client does not exist in database, please create new");

        Client clientToAdd = ClientManager.getInstance().createClientWithUserInput();

        entityManager.getTransaction().begin();

        try {

            orderToAdd.setClient(clientToAdd);
            clientToAdd.addOrder(orderToAdd);

            entityManager.persist(clientToAdd);
            entityManager.persist(orderToAdd);

            entityManager.getTransaction().commit();

        }
        catch (Exception e) {
            entityManager.getTransaction().rollback();
            e.printStackTrace();
        }

    }

    public Order createOrderWithUserInput() {
        Order order = new Order();

        System.out.print("\nEnter note -> ");
        String note = scanner.nextLine();

        if (note.equals(" ") || note.isEmpty())
            note = null;

        order.setNote(note);

        order.setOrderDate(new Date());

        return order;
    }

    public void calculateOrderPrice(Order order) {

        BigDecimal total = BigDecimal.ZERO;

        for (Product currentProduct : order.getProductSet()) {

          total = total.add(currentProduct.getPrice());

        }

        order.setPrice(total);

    }

    private Product getExistenceProductInDb(long id) {

        Product product;

        TypedQuery<Product> productTypedQuery = entityManager.createNamedQuery("getProductById",Product.class);
        productTypedQuery.setParameter("id",id);

        try {
            product = productTypedQuery.getSingleResult();
        }
        catch (NoResultException n) {
            product = null;
        }

        return product;
    }

    private Client getExistenceClientInDb(long id) {

        Client client;

        TypedQuery<Client> clientTypedQuery = entityManager.createNamedQuery("getClientById",Client.class);
        clientTypedQuery.setParameter("id",id);

        try {
            client = clientTypedQuery.getSingleResult();
        }
        catch (NoResultException n) {
            client = null;
        }

        return client;
    }



    public EntityManager getEntityManager() {
        return entityManager;
    }

    @Override
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public Scanner getScanner() {
        return scanner;
    }

    @Override
    public void setScanner(Scanner scanner) {
        this.scanner = scanner;
    }


}
