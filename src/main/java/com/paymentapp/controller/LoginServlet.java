package com.paymentapp.controller;

import com.paymentapp.dao.UserDAO;
import com.paymentapp.model.User;
import com.paymentapp.util.ActionLogger;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LoginServlet extends HttpServlet {

    static final Logger logger = LogManager.getLogger(LoginServlet.class);
    private final UserDAO userDAO = new UserDAO();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String fullName = request.getParameter("fullName");
        String password = request.getParameter("password");

        logger.info("Спроба входу користувача з ПІБ: {}", fullName);

        User user = userDAO.findAll().stream()
                .filter(u -> u.getFullName().equalsIgnoreCase(fullName) && u.getPassword().equals(password))
                .findFirst()
                .orElse(null);

        if (user != null) {
            logger.info("Успішний вхід користувача: {}", fullName);

            HttpSession session = request.getSession();
            session.setAttribute("user", user);

            // Запис логів у базу
            ActionLogger.log(user.getId(), "Успішний вхід у систему");

            response.sendRedirect("client-dashboard");
        } else {
            logger.warn("Невдала спроба входу користувача: {}", fullName);

            request.setAttribute("error", "Невірні дані для входу!");
            request.getRequestDispatcher("signin_client.jsp").forward(request, response);
        }
    }
}

