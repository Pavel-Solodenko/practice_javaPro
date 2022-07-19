package ua.kiev.prog.case2;

import ua.kiev.prog.shared.Client;
import ua.kiev.prog.shared.ConnectionFactory;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws SQLException {
        try (Connection conn = ConnectionFactory.getConnection())
        {
            // remove this
            try {
                try (Statement st = conn.createStatement()) {
//                    st.execute("DROP TABLE IF EXISTS Clients");
                    //st.execute("CREATE TABLE Clients (id INT NOT NULL AUTO_INCREMENT PRIMARY KEY, name VARCHAR(20) NOT NULL, age INT)");
                }
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }

            ClientDAOImpl2 dao = new ClientDAOImpl2(conn, "Clients");
//            dao.createTable(Client.class);

            Client c = new Client("test", 100);
            dao.add(c);
            int id = c.getId();
            System.out.println(id);

//            System.exit(0);

//            List<Client> list = dao.getAll(Client.class);
//            for (Client cli : list)
//                System.out.println(cli);

//            list.get(0).setAge(55);
//            dao.update(list.get(0));
            Scanner sc = new Scanner(System.in);

           while (true) {

               System.out.println("Enter name");
               System.out.print("-> ");
               String name = sc.nextLine();
               System.out.println("Enter age");
               System.out.print("-> ");
               String age = sc.nextLine();

//               List<Client> list = dao.getAll(Client.class, name, age);
            List<Client> list = dao.getAll(Client.class, Integer.parseInt(age));
//               List<Client> list = dao.getAll(Client.class,name);

               if (list.isEmpty())
                   System.out.println("Nothing to show...");
               else
               list.forEach(System.out::println);

               System.out.println("\nDo you want to continue?(n\\y)");
               System.out.print("-> ");
               if (sc.nextLine().equals("n"))
                   break;
           }

           sc.close();

//            dao.delete(list.get(0));
        }
    }
}
