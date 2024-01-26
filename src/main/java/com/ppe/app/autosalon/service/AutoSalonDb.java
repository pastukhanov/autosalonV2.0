package com.ppe.app.autosalon.service;

import com.ppe.app.autosalon.entity.*;
import com.ppe.app.autosalon.repository.AbstractRepository;
import com.ppe.app.autosalon.repository.Database;
import com.ppe.app.autosalon.repository.Repository;
import com.ppe.app.autosalon.repository.RepositoryObjectCreator;


import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class AutoSalonDb implements AutoSalon {

    private Database db;
    private Repository<Car> cars;
    private Repository<Customer> customers;
    private Repository<Sale> sales;


    public AutoSalonDb(Database db) {
        this.db = db;
        initializeRepositories();
    }

    public AutoSalonDb() {
        Database db;
        try {
            this.db = new Database();
            this.db.initDb();
            initializeRepositories();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void initializeRepositories() {
        this.cars = createRepository("car", Car::new);
        this.customers = createRepository("customer", Customer::new);
        this.sales = createSalesRepository();
        initializeCounts();
    }

    private <T extends Entity> AbstractRepository<T> createRepository(String tableName, RepositoryObjectCreator<T> creator) {
        return new AbstractRepository<>(tableName, db) {
            @Override
            public T createObject(Map<String, Object> map) {
                return creator.createObject(map);
            }
        };
    }

    private Repository<Sale> createSalesRepository() {
        return new AbstractRepository<>("sale", db) {
            @Override
            public Sale createObject(Map<String, Object> map) {
                Long id = getAsLong(map.get("id"));
                Long carId = getAsLong(map.get("car_id"));
                CarType carType = CarType.valueOf((String) map.getOrDefault("car_type", "UNKNOWN"));
                String carModel = (String) map.getOrDefault("car_model", "UNKNOWN");
                String carBrand = (String) map.getOrDefault("car_brand", "UNKNOWN");
                Car car = new Car(carId, carType, carModel, carBrand);
                Customer customer = findCustomerById(getAsLong(map.get("customer_id")));
                return new Sale(id, car, customer);
            }
        };
    }

    private void initializeCounts() {
        Car.setCarsCount(cars.getLastId());
        Customer.setCustomersCount(customers.getLastId());
        Sale.setSalesCount(sales.getLastId());
    }

    @Override
    public Customer findCustomerById(Long customerId) {
        return customers.findById(customerId);
    }

    @Override
    public List<Car> getAllCars() {
        return cars.fetchAll();
    }

    @Override
    public List<Car> searchCars(String searchText) {
        return getAllCars().stream()
                .filter(car -> car.toString().toLowerCase().contains(searchText.toLowerCase()))
                .collect(Collectors.toList());
    }

    @Override
    public List<Customer> searchCustomers(String searchText) {
        return getAllCustomers().stream()
                .filter(customer -> customer.toString().toLowerCase().contains(searchText.toLowerCase()))
                .collect(Collectors.toList());
    }

    @Override
    public List<Customer> getAllCustomers() {
        return customers.fetchAll();
    }

    @Override
    public List<Sale> getAllSales() {
        return sales.fetchAll();
    }

    @Override
    public Boolean deleteCar(Long carId) {
        return deleteEntity(cars, carId);
    }

    @Override
    public Boolean deleteSale(Long saleId) {
        return deleteEntity(sales, saleId);
    }

    @Override
    public Boolean deleteCustomer(Long customerId) {
        List<Sale> salesToDel = getAllSales().stream()
                .filter(s -> s.getCustomer().getId().equals(customerId))
                .collect(Collectors.toList());
        for (Sale sale : salesToDel) {
            deleteEntity(sales, sale.getId());
        }
        return deleteEntity(customers, customerId);
    }

    private <T> Boolean deleteEntity(Repository<T> repository, Long entityId) {
        T entity = repository.findById(entityId);
        if (entity != null) {
            repository.delete(entityId);
            updateEntityCounts();
            return true;
        }
        return false;
    }

    private void updateEntityCounts() {
        Car.setCarsCount(cars.getLastId());
        Customer.setCustomersCount(customers.getLastId());
        Sale.setSalesCount(sales.getLastId());
    }

    @Override
    public void update(Customer customer) {
        customers.update(customer.getId(), customer);
    }

    @Override
    public void update(Car car) {
        cars.update(car.getId(), car);
    }

    @Override
    public boolean deleteEntity(String tableName, Long entityId) {
        switch (tableName) {
            case "sale" -> {
                return deleteEntity(sales, entityId);
            }
            case "customer" -> {
                return deleteEntity(customers, entityId);
            }
            case "car" -> {
                return deleteEntity(cars, entityId);
            }
            default -> {
                return false;
            }
        }
    }

    @Override
    public Entity findEntityById(String tableName, Long id) {
        switch (tableName) {
            case "sale" -> {
                return sales.findById(id);
            }
            case "customer" -> {
                return customers.findById(id);
            }
            case "car" -> {
                return cars.findById(id);
            }
            default -> {
                return null;
            }
        }
    }


    @Override
    public void updateEntity(String tableName, Entity entity) {
        switch (tableName) {
            case "customer" -> update((Customer) entity);
            case "car" -> update((Car) entity);
        }
    }

    @Override
    public Car findCarById(Long carId) {
        return cars.findById(carId);
    }

    @Override
    public Sale sellCar(Car car, Customer customer) {
        Sale sale = new Sale(car, customer);
        sales.add(sale);
        deleteCar(car.getId());
        return sale;
    }

    @Override
    public void addCustomer(Customer customer) {
        customers.add(customer);
    }

    @Override
    public void addCar(Car car) {
        cars.add(car);
    }
}


