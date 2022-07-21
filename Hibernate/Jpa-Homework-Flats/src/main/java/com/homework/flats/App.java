package com.homework.flats;

import javax.persistence.*;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Scanner;

public class App {

    static EntityManagerFactory entityManagerFactory;
    static EntityManager entityManager;

    public static void main(String[] args) {
        createConnectionToDB();

        addFlatToDB();
        viewAllFlats();
        viewByDistrict(null);
        viewByAddress("Timoshenka,20");
        viewByMoreThanArea(20.01);
        viewByLessThenPrice(2000.001);
        viewByRoomsAmount(2);
    }

    private static void viewAllFlats() {

        final Query query = entityManager.createQuery(
                "SELECT x FROM Flat x", Flat.class
        );

        @SuppressWarnings("unchecked")
        List<Flat> flats = (List<Flat>) query.getResultList();

        printResultList(flats);

    }

    private static void viewByRoomsAmount(int roomsAmount) {

        final Query query = entityManager.createQuery(
                "SELECT x FROM Flat x WHERE x.roomsAmount = :roomsAmount",Flat.class
        );

        query.setParameter("roomsAmount",roomsAmount);

        @SuppressWarnings("unchecked")
        List<Flat> flats = (List<Flat>) query.getResultList();

        printResultList(flats);

    }

    private static void viewByLessThenPrice(double price) {

        BigDecimal parameterPrice = new BigDecimal(price);
        parameterPrice = parameterPrice.setScale(2,RoundingMode.HALF_UP);

        final Query query = entityManager.createQuery(
                "SELECT x FROM Flat x WHERE x.price <= :price",Flat.class
        );

        query.setParameter("price",parameterPrice);

        @SuppressWarnings("unchecked")
        List<Flat> flats = (List<Flat>) query.getResultList();

        printResultList(flats);

    }

    private static void viewByMoreThanArea(double area) {

        BigDecimal parameterArea = new BigDecimal(area);
        parameterArea = parameterArea.setScale(2, RoundingMode.HALF_UP);

        final Query query = entityManager.createQuery(
                "SELECT x FROM Flat x WHERE x.area >= :area",Flat.class
        );

        query.setParameter("area",parameterArea);

        @SuppressWarnings("unchecked")
        List<Flat> flats = (List<Flat>) query.getResultList();

        printResultList(flats);

    }

    private static void viewByAddress(String address) {

        final Query query = entityManager.createQuery(
                "SELECT x FROM Flat x WHERE x.address=:address",Flat.class
        );

        query.setParameter("address",address);

        @SuppressWarnings("unchecked")
        List<Flat> flats = (List<Flat>) query.getResultList();

        printResultList(flats);

    }

    private static void viewByDistrict(String district) {
        final Query query;

        if (district != null) {

             query= entityManager.createQuery(
                    "SELECT x FROM Flat x WHERE x.district = :district",Flat.class
            );

            query.setParameter("district",district);

        }
        else {

            query = entityManager.createQuery(
              "SELECT x FROM Flat x WHERE x.district IS NULL",Flat.class
            );

        }

        @SuppressWarnings("unchecked")
        List<Flat> flats = (List<Flat>) query.getResultList();

        printResultList(flats);

    }

    private static void addFlatToDB() {

        entityManager.getTransaction().begin();

        try {

            Flat toAdd = userInputForCreateFlatRecord();
            entityManager.persist(toAdd);
            entityManager.getTransaction().commit();

            System.out.println("Successful added with id - "+toAdd.getId());

        }
        catch (Exception e) {
            entityManager.getTransaction().rollback();
        }

    }

    private static Flat userInputForCreateFlatRecord() {

        final Scanner scanner = new Scanner(System.in);

        Flat resultToAdd = new Flat();

        Field[] fieldsToFill = Flat.class.getDeclaredFields();
        for (Field field : fieldsToFill) {
            field.setAccessible(true);

            if (field.getName().equals("id")) continue;

            System.out.println("Enter "+field.getName());
            Object input = scanner.next();

            try {
                field.set(resultToAdd,input);
            } catch (IllegalAccessException | IllegalArgumentException e) {
                   Object toSet = parseFormats(field,input);

                   try {
                       field.set(resultToAdd,toSet);
                   }
                   catch (Exception ex) {
                       scanner.close();
                       ex.printStackTrace();
                   }

            }

        }

        scanner.close();
        return resultToAdd;
    }

    private static Object parseFormats(Field field,Object input) {

        String typeName = field.getType().getName();

        switch (typeName) {

            case "float" -> {
                String toParse = (String) input;
                return Float.parseFloat(toParse.replace(",", "."));
            }

            case "int" -> {
                return Integer.parseInt((String) input);
            }

            case "java.math.BigDecimal" -> {
                String toParse = (String) input;
                return new BigDecimal(toParse.replace(",", "."));
            }

            default -> {
                System.out.println("Incorrect input format!");
                throw new IllegalArgumentException();
            }

        }

    }

    private static void createConnectionToDB() {

        try {
            entityManagerFactory = Persistence.createEntityManagerFactory("FlatDB");
            entityManager = entityManagerFactory.createEntityManager();
        }
        catch (Exception e) {
            e.printStackTrace();
        }

    }

    private static void printResultList(List<?> list) {

        if (list.isEmpty())
            System.out.println("Nothing to show");
        else
            list.forEach(System.out::println);

    }

}
