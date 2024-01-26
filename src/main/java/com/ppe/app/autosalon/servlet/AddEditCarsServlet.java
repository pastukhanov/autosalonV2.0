package com.ppe.app.autosalon.servlet;

import com.ppe.app.autosalon.entity.Car;
import com.ppe.app.autosalon.entity.CarType;
import com.ppe.app.autosalon.service.AutoSalon;
import com.ppe.app.autosalon.service.AutoSalonDb;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@WebServlet("/add-car")
public class AddEditCarsServlet extends HttpServlet {
    /**
     *
     */
    private AutoSalon autoSalon;

    public AddEditCarsServlet() {
        this.autoSalon = new AutoSalonDb();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Car car;
        if(request.getParameter("id") != null){
            Long carId = Long.parseLong(request.getParameter("id"));
            car = autoSalon.findCarById(carId);
            request.setAttribute("title", "Edit Car");
            request.setAttribute("pageName", "Edit Car");
            request.setAttribute("action", "Edit");
        }
        else {
            car = new Car();
            request.setAttribute("title", "Add Car");
            request.setAttribute("pageName", "Add Car");
            request.setAttribute("action", "Add");
        }

        request.setAttribute("contentFile", "editCar.jsp");
        request.setAttribute("car", car);

        RequestDispatcher dispatcher = request.getRequestDispatcher("/base.jsp");
        dispatcher.forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        String action = request.getParameter("action");

        Long carId = Long.parseLong(request.getParameter("id"));
        CarType carType =  CarType.valueOf(request.getParameter("carType"));
        String model = request.getParameter("model");
        String brand = request.getParameter("brand");

        if (action.equals("Add")) {
            Car car = new Car(carType, model, brand);
            autoSalon.addCar(car);
        }
        else if (action.equals("Edit")){
            Car car = autoSalon.findCarById(carId);
            car.setType(carType);
            car.setModel(model);
            car.setBrand(brand);
            autoSalon.update(car);
        }

        response.sendRedirect("cars");
    }

}
