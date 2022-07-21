package com.homework.flats;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name="Flats")
public class Flat {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private long id;

    @Column(nullable = true,length = 25)
    private String district;

    @Column(unique = true,nullable = false)
    private String address;

    @Column(nullable = false,precision = 4,scale = 2)
    private BigDecimal area;
//    private float area;


    @Column(name = "rooms",nullable = false )
    private int roomsAmount;

    @Column(nullable = false,precision = 7,scale = 2,columnDefinition = "DECIMAL(7,2)")
    private BigDecimal price;

    public Flat() {}

    public Flat(String district, String address, BigDecimal area, int roomsAmount, BigDecimal price) {
        this.district = district;
        this.address = address;
        this.area = area;
        this.roomsAmount = roomsAmount;
        this.price = price;
    }

    @Override
    public String toString() {
        return new StringBuilder("Flat:\n")
                .append("District: ").append(district).append("\n")
                .append("Address: ").append(address).append("\n")
                .append("Area: ").append(area).append("\n")
                .append("Rooms amount: ").append(roomsAmount).append("\n")
                .append("Price: ").append(price)
                .toString();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public BigDecimal getArea() {
        return area;
    }

    public void setArea(BigDecimal area) {
        this.area = area;
    }

    public int getRoomsAmount() {
        return roomsAmount;
    }

    public void setRoomsAmount(int roomsAmount) {
        this.roomsAmount = roomsAmount;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
