package domain;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="Clients")
@NamedQuery(name = "getClientById",query = "SELECT c from Client c where c.id = :id")
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "name", nullable = false,length = 15)
    private String clientName;

    @Column(name = "phone",nullable = false,length = 12)
    private String telephoneNumber;

    @Column(name = "address", length = 50)
    private String homeAddress;

//        @Column(name = "sex")
    @Column(nullable = false)
    private char sex;

    @Column(nullable = false)
    private byte age;

    @OneToMany(mappedBy = "client",cascade = CascadeType.ALL)
    //@Column(name = "or")
    private List<Order> orderList= new ArrayList<>();


    public Client() {}

    public Client(String clientName, String telephoneNumber, String homeAddress,char sex, byte age) {
        this.clientName = clientName;
        this.telephoneNumber = telephoneNumber;
        this.homeAddress = homeAddress;
        this.sex = sex;
        this.age = age;
    }

    @Override
    public String toString() {
        return new StringBuilder("Client: \n")
                .append("id: ").append(id).append("\n")
                .append("Phone number: ").append(telephoneNumber).append("\n")
                .append("Address: ").append(homeAddress).append("\n")
                .toString();
    }

    public void addOrder(Order order) {
        orderList.add(order);
        order.setClient(this);
    }

    public void removeOrder(Order order) {
        orderList.remove(order);
        order.setClient(null);
    }

    public void clearOrderList() {
        orderList.clear();
    }


    public List<Order> getOrderList() {
        return orderList;
    }

    public void setOrderList(List<Order> orderList) {
        this.orderList = orderList;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTelephoneNumber() {
        return telephoneNumber;
    }

    public void setTelephoneNumber(String telephoneNumber) {
        this.telephoneNumber = telephoneNumber;
    }

    public String getHomeAddress() {
        return homeAddress;
    }

    public void setHomeAddress(String homeAddress) {
        this.homeAddress = homeAddress;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public char getSex() {
        return sex;
    }

    public void setSex(char sex) {
        this.sex = sex;
    }


    public byte getAge() {
        return age;
    }

    public void setAge(byte age) {
        this.age = age;
    }
}
