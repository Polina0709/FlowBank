package com.paymentapp.controller;

import com.paymentapp.dao.AdminDAO;
import com.paymentapp.model.Admin;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;

import java.io.IOException;


public class AdminLoginServlet extends HttpServlet {

    private final AdminDAO adminDAO = new AdminDAO();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        Admin admin = adminDAO.findByUsername(username);

        if (admin != null && admin.getPassword().equals(password)) {
            HttpSession session = request.getSession();
            session.setAttribute("admin", admin);
            response.sendRedirect("admin-accounts");
        } else {
            request.setAttribute("error", "Невірні дані адміністратора!");
            request.getRequestDispatcher("signin_admin.jsp").forward(request, response);
        }
    }
}

