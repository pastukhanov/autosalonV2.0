package com.ppe.app.autosalon.servlet;

import com.ppe.app.autosalon.entity.Car;
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


@WebServlet("/cars")
public class ViewCarsServlet extends HttpServlet {
    /**
     *
     */
    private AutoSalon autoSalon;

    public ViewCarsServlet() {
        this.autoSalon = new AutoSalonDb();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Car> cars = autoSalon.getAllCars();
        request.setAttribute("cars", cars);

        request.setAttribute("title", "Cars");
        request.setAttribute("pageName", "Cars List:");
        request.setAttribute("contentFile", "viewCars.jsp");

        RequestDispatcher dispatcher = request.getRequestDispatcher("/base.jsp");

        dispatcher.forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        String action = request.getParameter("action");

        if ("delete".equals(action)) {
            try {
                Long carId = Long.parseLong(request.getParameter("carId"));
                autoSalon.deleteCar(carId);
            } catch (NumberFormatException e) {

            }
        }
        response.sendRedirect("cars");
    }



}
