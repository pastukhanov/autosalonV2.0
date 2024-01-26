package com.ppe.app.autosalon.servlet;

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


@WebServlet("/sales")
public class ViewSalesServlet extends HttpServlet {
    /**
     *
     */
    private AutoSalon autoSalon;

    public ViewSalesServlet() {
        this.autoSalon = new AutoSalonDb();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Sale> sales = autoSalon.getAllSales();
        request.setAttribute("sales", sales);

        request.setAttribute("title", "Sales");
        request.setAttribute("pageName", "Sales List:");
        request.setAttribute("contentFile", "viewSales.jsp");

        RequestDispatcher dispatcher = request.getRequestDispatcher("/base.jsp");

        dispatcher.forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        String action = request.getParameter("action");

        if ("delete".equals(action)) {
            try {
                Long saleId = Long.parseLong(request.getParameter("id"));
                autoSalon.deleteSale(saleId);
            } catch (NumberFormatException e) {

            }
        }

        response.sendRedirect("sales");
    }
}
