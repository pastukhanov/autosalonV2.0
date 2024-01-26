package com.ppe.app.autosalon.test;

import com.ppe.app.autosalon.entity.*;
import com.ppe.app.autosalon.repository.AbstractRepository;
import com.ppe.app.autosalon.repository.Database;
import com.ppe.app.autosalon.repository.Repository;

import java.sql.SQLException;
import java.util.Map;

public class MainTest {

    public static void testCarRepository() {
        Database db = new Database();
        Car car = new Car(11, CarType.VAN, "New", "Brand");
        Repository<Car> carRep = new AbstractRepository<>("car", db) {
            public Car createObject(Map<String, Object> map) {
                return new Car(map);
            }
        };
        carRep.add(car);
        System.out.println("list all: " + carRep.fetchAll());
        carRep.delete(1l);
        System.out.println("delete 2: " + carRep.fetchAll());
        carRep.update(2l, car);
        System.out.println(carRep.fetchAll());
        System.out.println(carRep.findById(2l));
    }

    public static void testCustomerRepository() {
        Database db = new Database();
        Customer c = new Customer("Petr Popov", 21, CustomerGender.MALE);
        Repository<Customer> customerRep = new AbstractRepository<Customer>("customer", db) {
            public Customer createObject(Map<String, Object> map) {
                return new Customer(map);
            }
        };
        System.out.println(customerRep.fetchAll());
        customerRep.add(c);
        customerRep.delete(3l);
        System.out.println(customerRep.fetchAll());
        customerRep.update(2l, c);
        System.out.println(customerRep.fetchAll());
        System.out.println(customerRep.findById(2l));
    }

    public static void testSaleRepository() {
        Database db = new Database();
        Repository<Sale> customerRep = new AbstractRepository<>("sale", db) {
            public Sale createObject(Map<String, Object> map) {
                Long id = getAsLong(map.get("id"));
                Long carId = getAsLong(map.get("car_id"));
                Long customerId = getAsLong(map.get("customer_id"));
                try {
                    Car car = new Car(db.fetchAllInList("car").stream().filter(d->
                        getAsLong(d.getOrDefault("id", -1l))
                                .equals(carId)).findFirst().orElse(null));
                    Customer customer = new Customer(db.fetchAllInList("customer").stream().filter(d->
                                    getAsLong(d.getOrDefault("id", -1l))
                                    .equals(customerId)).findFirst().orElse(null));
                    return new Sale(id, car, customer);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        };
        System.out.println(customerRep.fetchAll());
    }


    public static void main(String[] args) {
        try {
            Database db = new Database();
            db.initDb();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        testCarRepository();
        testCustomerRepository();
        testSaleRepository();
    }
}
