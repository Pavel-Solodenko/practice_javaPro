package managers;

import javax.persistence.EntityManager;
import java.util.Scanner;

public interface Manager {
    void add();
    void delete(long id);
    void setScanner(Scanner scanner);
    void setEntityManager(EntityManager entityManager);
}
