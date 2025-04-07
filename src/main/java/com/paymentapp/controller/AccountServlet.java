package com.paymentapp.controller;

import com.paymentapp.dao.AccountDAO;
import com.paymentapp.model.User;
import com.paymentapp.model.Account;
import com.paymentapp.util.ActionLogger;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.util.List;

import static com.paymentapp.controller.LoginServlet.logger;

public class AccountServlet extends HttpServlet {
    private final AccountDAO accountDAO = new AccountDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        User user = (User) request.getSession().getAttribute("user");

        if (user == null) {
            response.sendRedirect("signin_client.jsp");
            return;
        }

        List<Account> accounts = accountDAO.findByUserId(user.getId());
        request.setAttribute("accounts", accounts);
        request.getRequestDispatcher("accounts.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        int accountId = Integer.parseInt(request.getParameter("accountId"));
        String action = request.getParameter("action");

        //  Дозволено тільки блокування клієнтом
        if (!"block".equals(action)) {
            logger.warn("Невірна дія з боку клієнта: " + action);
            response.sendRedirect("account");
            return;
        }

        boolean success = accountDAO.updateBlockedStatus(accountId, true); // тільки блокування

        if (success) {
            User user = (User) request.getSession().getAttribute("user");
            if (user != null) {
                ActionLogger.log(user.getId(), "Клієнт заблокував рахунок ID: " + accountId);
            }
        }

        response.sendRedirect("account");
    }
}

