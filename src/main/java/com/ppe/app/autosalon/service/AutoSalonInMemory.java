package com.ppe.app.autosalon.service;

import com.ppe.app.autosalon.config.Configuration;
import com.ppe.app.autosalon.entity.Car;
import com.ppe.app.autosalon.entity.Customer;
import com.ppe.app.autosalon.entity.Entity;
import com.ppe.app.autosalon.entity.Sale;

import java.util.*;
import java.util.stream.Collectors;


public class AutoSalonInMemory implements AutoSalon {
    private Map<Long, Car> cars = new HashMap<>();
    private Map<Long, Customer> customers = new HashMap<>();
    private Map<Long, Sale> sales = new HashMap<>();

    private static AutoSalonInMemory autoSalonInstance = null;


    public void addCar(Car car) {
        cars.put(car.getId(), car);
    }

    @Override
    public void addCustomer(Customer customer) {

    }

    @Override
    public Customer findCustomerById(Long customerId) {
        return null;
    }

    public AutoSalonInMemory() {
        readCars();
        readCustomers();
    }


    private void readCars() {
        cars.putAll(Configuration.sampleCars);
    }

    private void readCustomers() {
        customers.putAll(Configuration.sampleCustomers);
    }

    @Override
    public List<Car> getAllCars() {
        return (List<Car>) cars.values();
    }

    @Override
    public List<Car> searchCars(String searchText) {
        return cars.entrySet().stream().map(s -> (Car) s)
                .filter(c -> c.toString().contains(searchText))
                .collect(Collectors.toList());
    }

    @Override
    public List<Customer> searchCustomers(String searchText) {
        return customers.entrySet().stream().map(s -> (Customer) s)
                .filter(c -> c.toString().contains(searchText))
                .collect(Collectors.toList());
    }

    @Override
    public List<Customer> getAllCustomers() {
        return customers.entrySet().stream().map(s -> (Customer) s).collect(Collectors.toList());
    }

    @Override
    public List<Sale> getAllSales() {
        return sales.entrySet().stream().map(s -> (Sale) s).collect(Collectors.toList());
    }


    public Boolean deleteCar(Long carId) {
        Car car  = cars.getOrDefault(carId, null);
        if (car != null) {
            cars.remove(carId);
            return true;
        }
        return false;
    }

    public Boolean deleteSale(Long saleId) {
        Sale sale  = sales.getOrDefault(saleId, null);
        if (sale != null) {
            sales.remove(saleId);
            return true;
        }
        return false;
    }

    public Boolean deleteCustomer(Long customerId) {
        Customer customer  = customers.getOrDefault(customerId, null);
        if (customer != null) {
            customers.remove(customerId);
            return true;
        }
        return false;
    }

    @Override
    public void update(Customer customer) {
        customers.put(customer.getId(), customer);
    }

    @Override
    public void update(Car car) {
        cars.put(car.getId(), car);
    }

    @Override
    public boolean deleteEntity(String tableName, Long entityId) {
        switch (tableName) {
            case "sales" -> sales.remove(entityId);
            case "customers" -> customers.remove(entityId);
            case "cars" -> cars.remove(entityId);
            default -> {
                return false;
            }
        }
        return true;
    }

    @Override
    public Entity findEntityById(String tableName, Long id) {
        switch (tableName) {
            case "sales" -> {
                return sales.get(id);
            }
            case "customers" -> {
                return customers.get(id);
            }
            case "cars" -> {
                return cars.get(id);
            }
            default -> {
                return null;
            }
        }
    }

    @Override
    public void updateEntity(String tableName, Entity entity) {
        switch (tableName) {
            case "sales" -> sales.put(entity.getId(), (Sale) entity);
            case "customers" -> customers.put(entity.getId(), (Customer) entity);
            case "cars" -> cars.put(entity.getId(), (Car) entity);
            default -> {
                return;
            }
        }
    }


    public Car findCarById(Long carId) {
        return cars.getOrDefault(carId, null);
    }

    @Override
    public Sale sellCar(Car car, Customer customer) {
        cars.remove(car.getId());
        Sale sale = new Sale(car, customer);
        sales.put(sale.getId(), sale);
        return sale;
    }
}