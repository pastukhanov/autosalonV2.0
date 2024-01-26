package com.ppe.app.autosalon.service;

import com.ppe.app.autosalon.entity.Car;
import com.ppe.app.autosalon.entity.Customer;
import com.ppe.app.autosalon.entity.Entity;
import com.ppe.app.autosalon.entity.Sale;

import java.util.List;

public interface AutoSalon {


    void addCar(Car car);

    void addCustomer(Customer customer);

    Customer findCustomerById(Long customerId);

    Car findCarById(Long carId);

     Sale sellCar(Car car, Customer customer);

    List<Car> getAllCars();

    List<Car> searchCars(String searchText);
    List<Customer> searchCustomers(String searchText);

    List<Customer> getAllCustomers();

    List<Sale> getAllSales();

    Boolean deleteCar(Long carId);

    Boolean deleteSale(Long saleId);

    Boolean deleteCustomer(Long customerId);

    void update(Customer customer);

    void update(Car car) ;


    boolean deleteEntity(String tableName, Long entityId);

    Entity findEntityById(String tableName, Long id);

    void updateEntity(String tableName, Entity entity);
}
