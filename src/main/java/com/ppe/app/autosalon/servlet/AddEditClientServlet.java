package com.ppe.app.autosalon.servlet;

import com.ppe.app.autosalon.entity.Car;
import com.ppe.app.autosalon.entity.CarType;
import com.ppe.app.autosalon.entity.Customer;
import com.ppe.app.autosalon.entity.CustomerGender;
import com.ppe.app.autosalon.service.AutoSalon;
import com.ppe.app.autosalon.service.AutoSalonDb;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@WebServlet("/add-customer")
public class AddEditClientServlet extends HttpServlet {
    /**
     *
     */
    private AutoSalon autoSalon;

    public AddEditClientServlet() {
        this.autoSalon = new AutoSalonDb();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Customer customer;
        if(request.getParameter("id") != null){
            Long clientId = Long.parseLong(request.getParameter("id"));
            customer = autoSalon.findCustomerById(clientId);
            request.setAttribute("title", "Edit Customer");
            request.setAttribute("pageName", "Edit Customer");
            request.setAttribute("action", "Edit");

        } else {
            customer = new Customer();
            request.setAttribute("title", "Add Customer");
            request.setAttribute("pageName", "Add Customer");
            request.setAttribute("action", "Add");
        }
        request.setAttribute("customer", customer);
        request.setAttribute("contentFile", "editClient.jsp");
        RequestDispatcher dispatcher = request.getRequestDispatcher("/base.jsp");
        dispatcher.forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        String action = request.getParameter("action");

        Customer customer;

        Long id = Long.valueOf(request.getParameter("id"));
        CustomerGender gender =  CustomerGender.valueOf(request.getParameter("gender"));
        String name = request.getParameter("name");
        Integer age = Integer.valueOf(request.getParameter("age"));

        if (action.equals("Add")) {
            customer = new Customer(id, name, age, gender);
            autoSalon.addCustomer(customer);
        } else if (action.equals("Edit")) {

            customer = autoSalon.findCustomerById(id);
            customer.setAge(age);
            customer.setName(name);
            customer.setGender(gender);
            autoSalon.update(customer);
        }

        response.sendRedirect("customers");
    }

}
