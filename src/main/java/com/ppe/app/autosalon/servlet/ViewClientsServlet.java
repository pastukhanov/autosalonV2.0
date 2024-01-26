package com.ppe.app.autosalon.servlet;

import com.ppe.app.autosalon.service.AutoSalon;
import com.ppe.app.autosalon.service.AutoSalonDb;
import com.ppe.app.autosalon.entity.Customer;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/customers")
public class ViewClientsServlet extends HttpServlet {
    /**
	 * 
	 */
	private AutoSalon autoSalon;

    public ViewClientsServlet() {
        this.autoSalon = new AutoSalonDb();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Customer> customers = autoSalon.getAllCustomers();
        request.setAttribute("customers", customers);
        request.setAttribute("title", "Customers");
        request.setAttribute("pageName", "Customers List:");
        request.setAttribute("contentFile", "viewClients.jsp");

        RequestDispatcher dispatcher = request.getRequestDispatcher("/base.jsp");
        dispatcher.forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        String action = request.getParameter("action");

        if ("delete".equals(action)) {
            try {
                Long clientId = Long.parseLong(request.getParameter("id"));
                autoSalon.deleteCustomer(clientId);
            } catch (NumberFormatException e) {

            }
        }

        response.sendRedirect("clients");
    }
}
