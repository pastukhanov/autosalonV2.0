package com.ppe.app.autosalon.view;

import com.ppe.app.autosalon.entity.Car;
import com.ppe.app.autosalon.entity.Customer;
import com.ppe.app.autosalon.service.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

import java.util.List;
import java.util.function.Function;


public class FrameApp implements ApplicationVew {

    private ApplicationState state;
    private AutoSalon autoSalon;
    private JFrame frame;
    private CardLayout cardLayout;
    private JPanel cardPanel;

    // Main menu buttons
    private JButton customersButton;
    private JButton salesButton;
    private JButton carsButton;
    private JButton sellButton;

    // Sub menu buttons and components
    private JButton listButton;
    private JButton addButton;
    private JButton deleteButton;
    private JButton editButton;
    private JButton backButton;


    private DefaultTableModel tableModel;
    private JTable table;
    private JScrollPane scrollPane;

    public FrameApp(AutoSalon autoSalon) {
        this.autoSalon = autoSalon;
    }

    private void initializeComponents() {
        frame = new JFrame("AutoSalon Management System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 400);

        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);


        JPanel mainMenuPanel = new JPanel();
        customersButton = new JButton("Customers");
        salesButton = new JButton("Sales");
        carsButton = new JButton("Cars");
        sellButton = new JButton("Sell");

        mainMenuPanel.add(customersButton);
        mainMenuPanel.add(carsButton);
        mainMenuPanel.add(salesButton);
        mainMenuPanel.add(sellButton);


        JPanel subMenuPanel = new JPanel();
        listButton = new JButton("List");
        addButton = new JButton("Add");
        deleteButton = new JButton("Delete");
        editButton = new JButton("Edit");
        backButton = new JButton("Back");

        subMenuPanel.add(listButton);
        subMenuPanel.add(addButton);
        subMenuPanel.add(deleteButton);
        subMenuPanel.add(editButton);
        subMenuPanel.add(backButton);

        cardPanel.add(mainMenuPanel, "Main Menu");
        cardPanel.add(subMenuPanel, "Sub Menu");

        String[] columnNames = {""}; // Adjust column names as needed
        tableModel = new DefaultTableModel(columnNames, 0); // 0 indicates start with no rows
        table = new JTable(tableModel);
        scrollPane = new JScrollPane(table);
        table.setFillsViewportHeight(true);

        frame.add(cardPanel, BorderLayout.NORTH);
        frame.add(scrollPane, BorderLayout.CENTER);


        customersButton.addActionListener(e -> switchToSubMenu(ApplicationState.CUSTOMER_MENU));
        salesButton.addActionListener(e -> switchToSubMenu(ApplicationState.SALES_MENU));
        carsButton.addActionListener(e -> switchToSubMenu(ApplicationState.CARS_MENU));
        sellButton.addActionListener(e -> processSell());

        listButton.addActionListener(e -> processList());
        addButton.addActionListener(e -> processAdd());
        deleteButton.addActionListener(e -> processDelete());
        editButton.addActionListener(e -> processEdit());
        backButton.addActionListener(e -> {
            state = ApplicationState.STARTING_MENU;
            processList();
            cardLayout.show(cardPanel, "Main Menu");
        });

        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
    }

    private void switchToSubMenu(ApplicationState state) {
        this.state = state;
        cardLayout.show(cardPanel, "Sub Menu");
        processList();
    }

    private void processSell() {
        switchToSubMenu(ApplicationState.SALES_MENU);
        showAddSaleForm();
        processList();
    }

    private void processList() {
        tableModel.setRowCount(0);
        updateColumnNamesForState(state);

        switch (state) {
            case STARTING_MENU -> populateTable(List.of(new Pair(1, "Cars",
                                    "Cars Available for Sale"),
                    new Pair(2,"Customers", "Info About All Customers"
                            ), new Pair(3, "Sales", "Sales Information")),
                    tbl -> new Object[]{tbl.id, tbl.name, tbl.description});
            case CARS_MENU -> populateTable(autoSalon.getAllCars(),
                    car -> new Object[]{car.getId(), car.getType(), car.getModel(), car.getBrand()});
            case CUSTOMER_MENU -> populateTable(autoSalon.getAllCustomers(),
                    customer -> new Object[]{
                            customer.getId(), customer.getName(),
                            customer.getAge(), customer.getGender()});
            case SALES_MENU -> populateTable(autoSalon.getAllSales(),
                    sale -> new Object[]{
                            sale.getId(), sale.getCar().getBrand(),
                            sale.getCar().getModel(), sale.getCustomer().getName(),
                            sale.getCustomer().getId()});
        }
    }

    private void updateColumnNamesForState(ApplicationState state) {
        switch (state) {
            case STARTING_MENU -> setColumnNames(new String[]{"Table ID", "Table name", "Description"});
            case CARS_MENU -> setColumnNames(new String[]{"Car ID", "Type", "Model", "Brand"});
            case CUSTOMER_MENU -> setColumnNames(new String[]{"Customer ID", "Name", "Age", "Gender"});
            case SALES_MENU -> setColumnNames(new String[]{
                    "Sale ID", "Car Brand", "Car Model", "Customer Name", "Customer ID"});
        }
    }

    private void setColumnNames(String[] columnNames) {
        tableModel.setColumnIdentifiers(columnNames);
    }

    private <T> void populateTable(List<T> list, Function<T, Object[]> mapper) {
        for (T item : list) {
            tableModel.addRow(mapper.apply(item));
        }
    }

    private void processAdd() {
        switch (state) {
            case CARS_MENU:
                showAddCarForm();
                break;
            case CUSTOMER_MENU:
                showAddCustomerForm();
                break;
            case SALES_MENU:
                showAddSaleForm();
                break;
            default:
                JOptionPane.showMessageDialog(frame, "Invalid state");
        }
        processList();
    }

    private void showAddCarForm() {
        JDialog addCarDialog = new Forms(frame, autoSalon).getCarForm(
                null, null, null, null, false);
        addCarDialog.setVisible(true);
    }

    private void showEditCarForm() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(frame, "Please select a row to edit.");
            return;
        }

        Long id = (Long) tableModel.getValueAt(selectedRow, 0);

        Car car = autoSalon.findCarById(id);
        JDialog addCarDialog = new Forms(frame, autoSalon).getCarForm(
                car.getId(), car.getType(), car.getModel(), car.getBrand(), true);
        addCarDialog.setVisible(true);
    }


    private void showAddCustomerForm() {
        JDialog addCustomerDialog =  new Forms(frame, autoSalon).getAddCustomerForm(
                null,
                null,
                null,
                null,
                false
        );
        addCustomerDialog.setVisible(true);
    }

    private void showEditCustomerForm() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(frame,
                    "Please select a row to edit.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        Long id = (Long) tableModel.getValueAt(selectedRow, 0);

        Customer customer = autoSalon.findCustomerById(id);

        JDialog addCustomerDialog =  new Forms(frame, autoSalon).getAddCustomerForm(
                customer.getId(),
                customer.getName(),
                customer.getAge(),
                customer.getGender(),
                true
        );
        addCustomerDialog.setVisible(true);
    }

    private void processDelete() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(frame, "Please select a row to delete.");
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(
                frame,
                "Are you sure you want to delete this entry?",
                "Delete Confirmation",
                JOptionPane.YES_NO_OPTION
        );

        if (confirm == JOptionPane.YES_OPTION) {
            Long id = (Long) tableModel.getValueAt(selectedRow, 0);
            try {
                switch (state) {
                    case CARS_MENU:
                        autoSalon.deleteCar(id);
                        break;
                    case CUSTOMER_MENU:
                        autoSalon.deleteCustomer(id);
                        break;
                    case SALES_MENU:
                        autoSalon.deleteSale(id);
                        break;
                    default:
                        throw new IllegalStateException("Unhandled state: " + state);
                }
                tableModel.removeRow(selectedRow);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(frame, "Error deleting entry: " + e.getMessage());
            }
        }
    }

    private void processEdit() {
        switch (state) {
            case CARS_MENU:
                showEditCarForm();
                break;
            case CUSTOMER_MENU:
                showEditCustomerForm();
                break;
            case SALES_MENU:
                showEditSaleForm();
                break;
            default:
                System.out.println("Invalid state");
        }
        processList();
    }

    private void showEditSaleForm() {
        int confirm = JOptionPane.showConfirmDialog(
                frame,
                "Edit function is not available for sales. " +
                        "You can only delete the selected sale." +
                        "\nDo you want to delete the selected sale?",
                "Delete Confirmation",
                JOptionPane.YES_NO_OPTION
        );
        if (confirm == JOptionPane.YES_OPTION) {
            int selectedRow = table.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(frame, "Please select a row to delete.");
                return;
            }
            Long id = (Long) tableModel.getValueAt(selectedRow, 0);
            autoSalon.deleteSale(id);
        }
    }

    private void showAddSaleForm() {
        JDialog addSaleDialog = new Forms(frame, autoSalon).getAddSaleForm();
        addSaleDialog.setVisible(true);
    }

    @Override
    public void start() {
        SwingUtilities.invokeLater(() -> {
            initializeComponents();
            state = ApplicationState.STARTING_MENU;
            processList();
        });
    }

    record Pair (Integer id, String name, String description) { }
}