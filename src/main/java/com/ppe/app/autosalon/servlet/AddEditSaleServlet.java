package com.ppe.app.autosalon.servlet;

import com.google.gson.Gson;
import com.ppe.app.autosalon.entity.Car;
import com.ppe.app.autosalon.entity.Customer;
import com.ppe.app.autosalon.entity.CustomerGender;
import com.ppe.app.autosalon.entity.Sale;
import com.ppe.app.autosalon.service.AutoSalon;
import com.ppe.app.autosalon.service.AutoSalonDb;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;


@WebServlet("/add-sale")
public class AddEditSaleServlet extends HttpServlet {
    /**
     *
     */
    private AutoSalon autoSalon;

    public AddEditSaleServlet() {
        this.autoSalon = new AutoSalonDb();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        request.setAttribute("title", "Add Sale");
        request.setAttribute("pageName", "Add Sale");
        request.setAttribute("action", "Add");

        List<Object> cars = autoSalon.getAllCars()
                                .stream().map(car -> car.toMap())
                .collect(Collectors.toList());

        String carsJson = new Gson().toJson(cars);

        request.setAttribute("cars", carsJson);
        request.setAttribute("contentFile", "addSale.jsp");
        response.setContentType("text/html; charset=UTF-8");
        RequestDispatcher dispatcher = request.getRequestDispatcher("/base.jsp");
        dispatcher.forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        CustomerGender gender =  CustomerGender.valueOf(request.getParameter("gender"));
        String name = request.getParameter("name");
        Integer age = Integer.valueOf(request.getParameter("age"));


        Customer customer = new Customer(name, age, gender);

        Long carId = Long.valueOf(request.getParameter("carId"));

        if (autoSalon.searchCustomers(customer.toString()).isEmpty()){
            autoSalon.addCustomer(customer);
        } else {
            customer = autoSalon.searchCustomers(customer.toString()).stream().findFirst().get();
        }

        Car car = autoSalon.findCarById(carId);
        autoSalon.sellCar(car, customer);

        response.sendRedirect("sales");
    }

}
