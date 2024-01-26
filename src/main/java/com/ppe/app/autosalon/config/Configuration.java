package com.ppe.app.autosalon.config;


import com.ppe.app.autosalon.entity.Car;
import com.ppe.app.autosalon.entity.CarType;
import com.ppe.app.autosalon.entity.Customer;
import com.ppe.app.autosalon.entity.CustomerGender;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Configuration {
    public static final int numberOfCustomers = 5;
    public static final long randomState = 42l;

    public static Map<Long, Car> sampleCars = new HashMap<>();

    public static Map<Long, Customer> sampleCustomers = new HashMap<>();

    static {
        sampleCars.put(1L, new Car(CarType.TRUCK, "F-150", "Ford"));
        sampleCars.put(2L, new Car(CarType.SUV, "Model X", "Tesla"));
        sampleCars.put(3L, new Car(CarType.SEDAN, "Accord", "Honda"));
        sampleCars.put(4L, new Car(CarType.VAN, "Sienna", "Toyota"));
        sampleCars.put(5L, new Car(CarType.PASSENGER_CAR, "Civic", "Honda"));
        sampleCars.put(6L, new Car(CarType.TRUCK, "Silverado", "Chevrolet"));
        sampleCars.put(7L, new Car(CarType.SUV, "Rav4", "Toyota"));
        sampleCars.put(8L, new Car(CarType.SEDAN, "Camry", "Toyota"));
        sampleCars.put(9L, new Car(CarType.VAN, "Odyssey", "Honda"));
        sampleCars.put(10L, new Car(CarType.PASSENGER_CAR, "Mustang", "Ford"));

        String[] names = {"John Doe", "Jane Doe", "Michael Smith", "Anna Johnson",
                "Chris Davis", "Emma Thompson", "Olivia Brown", "William Jones",
                "Isabella Taylor", "Sophia Davis", "Charlotte Smith", "Mia Johnson",
                "Lucas Johnson", "Amelia Jones", "Harper Davis",
                "Evelyn Davis", "Abigail Johnson", "Elijah Jones",
                "Isaiah Smith", "Liam Davis"};

        Random random = new Random();
        random.setSeed(randomState);
        for (int i = 0; i < numberOfCustomers; i++) {
            String name = names[random.nextInt(names.length)];
            int age = random.nextInt(52) + 18;  // random age between 18 and 70
            CustomerGender gender = random.nextBoolean() ? CustomerGender.MALE : CustomerGender.FEMALE;
            Customer customer = new Customer(name, age, gender);
            sampleCustomers.put(customer.getId(), customer);
        }
    }
}
