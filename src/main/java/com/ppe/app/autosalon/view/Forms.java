package com.ppe.app.autosalon.view;

import com.ppe.app.autosalon.entity.Car;
import com.ppe.app.autosalon.entity.CarType;
import com.ppe.app.autosalon.entity.Customer;
import com.ppe.app.autosalon.entity.CustomerGender;
import com.ppe.app.autosalon.service.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.List;
import java.util.function.Consumer;


public class Forms {
    JFrame frame;
    AutoSalon autoSalon;

    public Forms(JFrame frame, AutoSalon autoSalon) {
        this.frame = frame;
        this.autoSalon = autoSalon;
    }

    public JDialog getCarForm(Long id, CarType carType, String model, String brand, boolean isEditMode) {
        String dialogTitle = isEditMode ? "Edit Car" : "Add New Car";
        JDialog addCarDialog = createDialog(dialogTitle, new GridLayout(4, 2));

        JComboBox<CarType> carTypeComboBox = addField(addCarDialog, "Car Type:",
                new JComboBox<>(CarType.values()), comboBox -> {
            if (carType != null) {
                comboBox.setSelectedItem(carType);
            }
        });

        JTextField modelField = addField(addCarDialog, "Model:", new JTextField(model != null ? model : ""));
        JTextField brandField = addField(addCarDialog, "Brand:", new JTextField(brand != null ? brand : ""));

        addButton(addCarDialog, isEditMode ? "Edit Car" : "Add Car",
                e -> saveCar(id, carTypeComboBox, modelField.getText(), brandField.getText(), isEditMode));

        return addCarDialog;
    }


    private void saveCar(Long id,
                         JComboBox<CarType> carTypeComboBox,
                         String model,
                         String brand,
                         boolean isEditMode) {
        CarType carType = (CarType) carTypeComboBox.getSelectedItem();
        if (isEditMode) {
            Car car = autoSalon.findCarById(id);
            car.setBrand(brand);
            car.setModel(model);
            car.setType(carType);
            autoSalon.update(car);
        } else {
            Car car = new Car(carType, model, brand);
            autoSalon.addCar(car);
        }
    }

    public JDialog getAddCustomerForm(Long id,
                                      String name,
                                      Integer age,
                                      CustomerGender gender,
                                      boolean isEditMode) {
        String dialogTitle = isEditMode ? "Edit Customer" : "Add New Customer";
        JDialog addCustomerDialog = createDialog(dialogTitle, new GridLayout(5, 2));

        JTextField nameField = addField(addCustomerDialog, "Name:",
                new JTextField(name != null ? name : ""));
        JTextField ageField = addField(addCustomerDialog, "Age:",
                new JTextField(age != null ? age.toString() : ""));

        JComboBox<String> genderComboBox = addField(addCustomerDialog, "Gender:",
                new JComboBox<>(new String[]{"Male", "Female"}), comboBox -> {
            if (gender != null) {
                comboBox.setSelectedItem(gender == CustomerGender.MALE ? "Male" : "Female");
            }
        });

        addButton(addCustomerDialog,
                isEditMode ? "Edit Customer" : "Add Customer",
                e -> saveCustomer(id, nameField.getText(), ageField.getText(),
                        (String) genderComboBox.getSelectedItem(), isEditMode));

        return addCustomerDialog;
    }

    private void saveCustomer(Long id,
                              String name,
                              String ageString,
                              String genderString,
                              boolean isEditMode) {
        try {
            int age = Integer.parseInt(ageString);
            CustomerGender gender = genderString.toLowerCase().startsWith("m") ? CustomerGender.MALE :
                    CustomerGender.FEMALE;

            if (isEditMode) {
                Customer customer = autoSalon.findCustomerById(id);
                customer.setName(name);
                customer.setAge(age);
                customer.setGender(gender);
                autoSalon.update(customer);
            } else {
                Customer customer = new Customer(name, age, gender);
                autoSalon.addCustomer(customer);
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(frame, "Please enter a valid age (integer value).",
                    "Invalid Age",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    public JDialog getAddSaleForm() {
        JDialog addSaleDialog = createDialog("Add Sale", BoxLayout.Y_AXIS);

        JTextField carModelField = createNonEditableTextField();
        JTextField carBrandField = createNonEditableTextField();
        JTextField carIdField = createNonEditableTextField();

        JTextField carSearchField = new JTextField();
        DefaultListModel<Car> carListModel = new DefaultListModel<>();
        JScrollPane scrollPane = setupCarSearchField(carSearchField,
                carListModel, carModelField, carBrandField, carIdField);

        addComponentPair(addSaleDialog, "Search Car:", carSearchField);
        addComponentPair(addSaleDialog, "Found Cars:", scrollPane);

        addComponentPair(addSaleDialog, "Car Model:", carModelField);
        addComponentPair(addSaleDialog, "Car Brand:", carBrandField);

        JTextField customerNameField = new JTextField();
        JTextField ageField = new JTextField();
        JComboBox<String> genderComboBox = new JComboBox<>(new String[]{"Male", "Female"});

        addComponentPair(addSaleDialog, "Customer Name:", customerNameField);
        addComponentPair(addSaleDialog, "Age:", ageField);
        addComponentPair(addSaleDialog, "Gender:", genderComboBox);

        addSaleButtonAndActions(addSaleDialog, customerNameField, ageField, genderComboBox, carIdField);

        addSaleDialog.pack();
        return addSaleDialog;
    }

    private JScrollPane setupCarSearchField(JTextField searchField,
                                            DefaultListModel<Car> listModel,
                                            JTextField... carFields) {
        searchField.addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent e) {
                SwingUtilities.invokeLater(() -> {
                    String searchText = searchField.getText().toLowerCase();
                    listModel.clear();

                    List<Car> cars = autoSalon.searchCars(searchText);
                    if (cars.isEmpty()) {
                    } else {
                        cars.forEach(car -> {
                            listModel.addElement(car);
                        });
                    }
                });
            }
        });

        JList<Car> carList = new JList<>(listModel);
        carList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        carList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                Car car = carList.getSelectedValue();
                if (car != null) {
                    updateCarFields(car, carFields);
                }
            }
        });

        return new JScrollPane(carList);
    }

    private void updateCarFields(Car car, JTextField... fields) {
        if (fields.length >= 3) {
            fields[0].setText(car.getModel());
            fields[1].setText(car.getBrand());
            fields[2].setText(String.valueOf(car.getId()));
        }
    }

    private void addComponentPair(Container container, String label, JComponent component) {
        container.add(new JLabel(label));
        container.add(component);
    }

    private void addSaleButtonAndActions(JDialog dialog,
                                         JTextField nameField,
                                         JTextField ageField,
                                         JComboBox<String> genderComboBox,
                                         JTextField carIdField) {
        JButton addSaleButton = new JButton("Add Sale");
        addSaleButton.addActionListener(e -> handleAddSaleAction(
                dialog, nameField, ageField, genderComboBox, carIdField));
        dialog.add(addSaleButton);

        JButton cancelSaleButton = new JButton("Cancel");
        cancelSaleButton.addActionListener(e -> dialog.dispose());
        dialog.add(cancelSaleButton);
    }

    private void handleAddSaleAction(JDialog dialog,
                                     JTextField nameField,
                                     JTextField ageField,
                                     JComboBox<String> genderComboBox,
                                     JTextField carIdField) {
        try {
            Customer customer = createCustomerFromFields(nameField, ageField, genderComboBox);
            if (autoSalon.searchCustomers(customer.toString()).isEmpty()){
                autoSalon.addCustomer(customer);
            } else {
                customer = autoSalon.searchCustomers(customer.toString()).stream().findFirst().get();
            }

            Car car = autoSalon.findCarById(Long.parseLong(carIdField.getText()));
            autoSalon.sellCar(car, customer);
            dialog.dispose();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(dialog,
                    "Please check:\n(1) input age is valid (integer value)\n" +
                            "(2) a car is selected!",
                    "Invalid Age or Car is not chosen",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private Customer createCustomerFromFields(JTextField nameField,
                                              JTextField ageField,
                                              JComboBox<String> genderComboBox) {
        String name = nameField.getText();
        int age = Integer.parseInt(ageField.getText());
        CustomerGender gender = CustomerGender.valueOf(genderComboBox.getSelectedItem().toString().toUpperCase());
        return new Customer(name, age, gender);
    }

    private JTextField createNonEditableTextField() {
        JTextField textField = new JTextField();
        textField.setEditable(false);
        return textField;
    }
    private JDialog createDialog(String title, LayoutManager layout) {
        JDialog dialog = new JDialog(frame, title, true);
        dialog.setLayout(layout);
        dialog.setSize(300, 200);
        dialog.setLocationRelativeTo(frame);
        return dialog;
    }

    private JDialog createDialog(String title, int axis) {
        JDialog dialog = new JDialog(frame, title, true);
        dialog.setLayout(new BoxLayout(dialog.getContentPane(), axis));
        dialog.setLocationRelativeTo(frame);
        return dialog;
    }

    private <T extends JComponent> T addField(JDialog dialog, String label, T component, Consumer<T> initializer) {
        dialog.add(new JLabel(label));
        initializer.accept(component);
        dialog.add(component);
        return component;
    }

    private <T extends JComponent> T addField(JDialog dialog, String label, T component) {
        return addField(dialog, label, component, c -> {});
    }

    private void addButton(JDialog dialog, String label, ActionListener action) {
        JButton button = new JButton(label);
        button.addActionListener(e -> {
            action.actionPerformed(e);
            dialog.dispose();
        });
        dialog.add(button);
        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(e -> dialog.dispose());
        dialog.add(cancelButton);
    }
}
