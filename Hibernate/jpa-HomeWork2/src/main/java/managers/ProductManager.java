package managers;

import domain.Product;

import javax.persistence.EntityManager;
import java.math.BigDecimal;
import java.util.Scanner;

public class ProductManager implements Manager{

    private static final ProductManager productManager = new ProductManager();
    private EntityManager entityManager;
    private Scanner scanner;

    private ProductManager() {}

    public static ProductManager getInstance() {
        return productManager;
    }

    @Override
    public void add() {

        entityManager.getTransaction().begin();
        try {
            Product productToAdd = createProductWithUserInput();

            entityManager.persist(productToAdd);
            entityManager.getTransaction().commit();
        }
        catch (Exception e) {
            entityManager.getTransaction().rollback();
            e.printStackTrace();
        }

    }

    @Override
    public void delete(long id) {
        Product productToDelete = entityManager.getReference(Product.class,id);

        if (productToDelete == null) {
            System.out.println("Product not found");
            return;
        }

        entityManager.getTransaction().begin();

        try {

            entityManager.remove(productToDelete);
            entityManager.getTransaction().commit();

        }
        catch (Exception e) {
            entityManager.getTransaction().rollback();
            e.printStackTrace();
        }

    }

    public Product createProductWithUserInput() {
        Product product = new Product();

        System.out.print("\nEnter product name -> ");
        product.setProductName(scanner.nextLine());

        System.out.print("\nEnter product description -> ");
        String productDescription = scanner.nextLine();

        if (productDescription.equals(" ") || productDescription.isEmpty())
            productDescription = null;

        product.setDescription(productDescription);

        System.out.print("\nEnter product price -> ");
        product.setPrice(new BigDecimal(scanner.nextLine()));

        return product;
    }

    @Override
    public void setScanner(Scanner scanner) {
        this.scanner = scanner;
    }

    @Override
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }


}
