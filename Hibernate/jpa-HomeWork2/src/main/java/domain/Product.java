package domain;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="Products")
@NamedQuery(name = "getProductById",query = "SELECT p FROM Product p WHERE p.id = :id")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "name", nullable = false,length = 15)
    private String productName;

    @Column(length = 500)
    private String description;

    @Column(nullable = false,scale = 2)
    private BigDecimal price;

    @ManyToMany(mappedBy = "productSet")
    private Set<Order> orderSet = new HashSet<>();





    public Product() {}



    public Product(String productName, String description, BigDecimal price) {
        this.productName = productName;
        this.description = description;
        this.price = price;
    }



    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }


    public Set<Order> getOrderSet() {
        return orderSet;
    }

    public void setOrderSet(Set<Order> orderSet) {
        this.orderSet = orderSet;
    }
}
