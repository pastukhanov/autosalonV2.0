package com.ppe.app.autosalon.view;

import com.ppe.app.autosalon.entity.*;
import com.ppe.app.autosalon.service.*;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Scanner;

public class ConsoleApp implements ApplicationVew {

    private ApplicationState state;
    private AutoSalon autoSalon;
    private Scanner in = new Scanner(System.in);

    private final String STARTING_MENU_PRINT = "Main menu. Type one of the commands:";
    private final String MENU_LEVEL_1 = "->customers\t->sales\t->cars\t->sell\t->exit";
    private final String MENU_LEVEL_2 = "->list\t->add\t->delete\t->edit\t->back\t->exit";

    public ConsoleApp(AutoSalon autoSalon) {
        this.state = ApplicationState.STARTING_MENU;
        this.autoSalon = autoSalon;
    }

    @Override
    public void start() {
        while (state != ApplicationState.STOPPED) {
            printMenuOptions();
            String cmd = in.nextLine().trim().toLowerCase();
            handleCommand(cmd);
        }
        System.out.println("See you soon!");
    }

    private void handleCommand(String cmd) {
        switch (cmd) {
            case "stop", "exit" -> state = ApplicationState.STOPPED;
            case "customers" -> state = ApplicationState.CUSTOMER_MENU;
            case "sales" -> state = ApplicationState.SALES_MENU;
            case "cars" -> state = ApplicationState.CARS_MENU;
            case "back" -> state = ApplicationState.STARTING_MENU;
            case "list" -> processListCmd();
            case "edit" -> processEditCmd();
            case "add" -> processAddCmd();
            case "delete" -> processDeleteCmd();
            case "sell" -> processSellCmd();
            default -> System.out.println("Cmd is unknown! Try again!");
        }
    }

    private void processListCmd() {
        switch (state) {
            case SALES_MENU -> {
                System.out.println("Sales' list:");
                showSales();
            }
            case CUSTOMER_MENU -> {
                System.out.println("Customer's list:");
                showCustomers();
            }
            case CARS_MENU -> {
                System.out.println("Car's list:");
                showCars();
            }
            default -> System.out.println("The typed command is not correct!");
        }
    }

    private Long inputLong(String prompt) {
        while (true) {
            System.out.println(prompt);
            String input = in.nextLine();
            if (input.equalsIgnoreCase("no")) {
                return -1L;
            } else if (input.equalsIgnoreCase("cancel")) {
                return null;
            }
            try {
                return Long.parseLong(input);
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number or type 'no'.");
            }
        }
    }

    private void processSellCmd() {
        Long carId = inputLong("Input a car id to be sold:");
        if (carId == null) {
            System.out.println("The action is canceled");
            return;
        }
        Long customerId = inputLong("Input a customer id who is going to buy the car or type 'no':");
        if (customerId == null) {
            System.out.println("The action is canceled");
            return;
        }
        Sale sale = sellCar(carId, customerId);
        if (sale == null) {
            System.out.println("Car is not found or is already sold! Check list of cars: ");
        } else {
            System.out.printf("The order is created: %s \n", sale);
        }
    }

    private void processDeleteCmd() {
        switch (state) {
            case SALES_MENU -> processDeleteEntity("sale", "Sale");
            case CUSTOMER_MENU -> processDeleteEntity("customer", "Customer");
            case CARS_MENU -> processDeleteEntity("car", "Car");
            default -> System.out.println("Select one of the program modes (type):\n->sales\n->customers\n->cars");
        }
    }

    private void processDeleteEntity(String tableName, String entityName) {
        Long entityId = inputLong("Type a " + entityName + " id to remove it from db: ");
        boolean deleted = autoSalon.deleteEntity(tableName, entityId);
        System.out.println(deleted);
        if (deleted) {
            System.out.printf("%s with id=%s is deleted from db\n", entityName, entityId);
        } else {
            System.out.printf("%s with id=%s is not found\n", entityName, entityId);
        }
    }

    private void processAddCmd() {
        switch (state) {
            case SALES_MENU -> processSellCmd();
            case CUSTOMER_MENU -> processAddCustomer();
            case CARS_MENU -> processAddCar();
            default -> System.out.println("Select one of the program modes (type):\n->sales\n->customers\n->cars");
        }
    }

    private void processAddCustomer() {
        Customer customer = createNewCustomer();
        if (customer == null) {
            System.out.println("The action is canceled");
            return;
        }
        System.out.printf("The customer is added: %s\n", customer);
    }

    private void processAddCar() {
        Car car = createNewCar();
        if (car == null) {
            System.out.println("The action is canceled");
            return;
        }
        System.out.printf("The car is added: %s\n", car);
    }

    private void processEditCmd() {
        switch (state) {
            case CUSTOMER_MENU -> processEditEntity("customer", "Customer");
            case CARS_MENU -> processEditEntity("car", "Car");
            case SALES_MENU -> {
                System.out.println("You can only delete a sale record, but can not edit it!");
                System.out.println("Do you want to delete a sale record? (Y/n): ");
                String ans = in.nextLine();
                if (ans.toLowerCase().startsWith("y")) {
                    processDeleteCmd();
                }
            }
        }
    }

    private void processEditEntity(String tableName, String entityName) {
        Long id = inputLong("Input a " + entityName + " id to edit:");
        Entity entity = autoSalon.findEntityById(tableName, id);
        if (entity == null) {
            System.out.printf("%s with id=%s is not found\n", tableName, id);
            return;
        }
        resetObjFields(entity);
        autoSalon.updateEntity(tableName, entity);
    }

    private void resetObjFields(Object obj) {
        Field[] fields = obj.getClass().getDeclaredFields();
        for (Field field : fields) {
            if (!java.lang.reflect.Modifier.isStatic(field.getModifiers()) &&
                    !field.getName().toLowerCase().contains("id")) {
                try {
                    field.setAccessible(true);
                    System.out.printf("Input new value for the field %s (current value: %s, type: %s):\n",
                            field.getName(),
                            field.get(obj),
                            field.getType().getSimpleName());
                    String inputString = in.nextLine();
                    if (inputString.isEmpty()) {
                        field.setAccessible(false);
                        continue;
                    }
                    Object value = convertStringToType(inputString, field.getType());
                    field.set(obj, value);
                } catch (IllegalArgumentException | IllegalAccessException e) {
                    System.out.printf("You typed inappropriate value: %s\nValue hasn't changed!\n", e.getMessage());
                } finally {
                    field.setAccessible(false);
                }
            }
        }
    }

    private Object convertStringToType(String inputString, Class<?> type) {
        if (type == String.class) {
            return inputString;
        } else if (type == int.class || type == Integer.class) {
            return Integer.parseInt(inputString);
        } else if (type == long.class || type == Long.class) {
            return Long.parseLong(inputString);
        } else if (type == double.class || type == Double.class) {
            return Double.parseDouble(inputString);
        } else if (type == float.class || type == Float.class) {
            return Float.parseFloat(inputString);
        } else if (type == boolean.class || type == Boolean.class) {
            return Boolean.parseBoolean(inputString);
        } else if (type.isEnum()) {
            return Enum.valueOf((Class<Enum>) type, inputString);
        } else {
            throw new IllegalArgumentException("Type " + type + " is not supported");
        }
    }


    private void printMenuOptions() {
        switch (state) {
            case SALES_MENU -> System.out.println("Sales menu:");
            case CUSTOMER_MENU -> System.out.println("Customer menu:");
            case CARS_MENU -> System.out.println("Cars menu:");
            default -> System.out.println(STARTING_MENU_PRINT);
        }
        printMenuCmds();
    }

    private void printMenuCmds() {
        switch (state) {
            case SALES_MENU, CUSTOMER_MENU, CARS_MENU -> System.out.println(MENU_LEVEL_2);
            default -> System.out.println(MENU_LEVEL_1);
        }
    }

    private Sale sellCar(Long carId, Long customerId) {
        Car car = autoSalon.findCarById(carId);
        if (car == null) {
            return null;
        }
        Customer customer = autoSalon.findCustomerById(customerId);
        if (customer == null) {
            customer = createNewCustomer();
        }
        return autoSalon.sellCar(car, customer);
    }

    private Customer createNewCustomer() {
        try {
            System.out.println("Input customer name: ");
            String customerName = in.nextLine();
            System.out.println(customerName);
            if (customerName.equals("cancel")) return null;
            System.out.println("Input customer age: ");
            String ageString = in.nextLine();
            if (ageString.equals("cancel")) return null;
            int age = Integer.parseInt(ageString);
            System.out.println("Input customer gender M/F:");
            String genderString = in.nextLine();
            if (genderString.equals("cancel")) return null;
            CustomerGender gender = genderString.toLowerCase().startsWith("m") ?
                    CustomerGender.MALE:
                    CustomerGender.FEMALE;
            Customer customer = new Customer(customerName, age, gender);
            autoSalon.addCustomer(customer);
            return customer;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private Car createNewCar() {
        System.out.println("New car creation mode:");
        CarType carType = getCarTypeFromUser();
        if (carType == null) return null;
        System.out.println("Input a car model");
        String model = in.nextLine();
        if (model == "cancel") return null;
        System.out.println("Input a car brand");
        String brand = in.nextLine();
        if (brand == "cancel") return null;
        Car car = new Car(carType, model, brand);
        autoSalon.addCar(car);
        return car;
    }

    private CarType getCarTypeFromUser() {
        System.out.println("Select a car type:");
        System.out.println("""
                1)     TRUCK,
                2)     SUV,
                3)     SEDAN,
                4)     VAN,
                5)     PASSENGER_CAR
                """);
        while(true) {
            String inputText = in.nextLine();
            try {
                int input = Integer.parseInt(inputText);
                if (0<=input && input <=5)
                    return CarType.values()[input-1];
                System.out.println("Please enter a number between 1 and 5.");
            } catch (NumberFormatException e) {
                if (inputText.toLowerCase().equals("cancel")) {
                    return null;
                }
                System.out.println("Please enter a valid number or type 'no'.");
            }
        }
    }

    public void showCustomers() {
        List<Customer> customers = autoSalon.getAllCustomers();
        if (customers.isEmpty()) {
            System.out.println("Customers table is empty!");
            return;
        }
        for (Customer customer : customers) {
            System.out.println(customer);
        }
    }

    public void showSales() {
        List<Sale> sales = autoSalon.getAllSales();
        if (sales.isEmpty()) {
            System.out.println("Sales table is empty!");
            return ;
        }
        for (Sale sale : sales) {
            System.out.println(sale);
        }
    }

    public void showCars() {
        List<Car> cars = autoSalon.getAllCars();
        if (cars.isEmpty()) {
            System.out.println("Cars table is empty!");
            return ;
        }
        for (Car car : cars) {
            System.out.println(car);
        }
    }
}
