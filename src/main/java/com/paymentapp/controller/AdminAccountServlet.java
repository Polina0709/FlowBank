package com.paymentapp.controller;

import com.paymentapp.dao.AccountDAO;
import com.paymentapp.model.Account;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.util.List;

public class AdminAccountServlet extends HttpServlet {
    private final AccountDAO accountDAO = new AccountDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Account> accounts = accountDAO.findAll();
        request.setAttribute("accounts", accounts);
        request.getRequestDispatcher("admin_dashboard.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int accountId = Integer.parseInt(request.getParameter("accountId"));
        String action = request.getParameter("action");

        boolean block = "block".equals(action);
        accountDAO.updateBlockedStatus(accountId, block);

        response.sendRedirect("admin-accounts");
    }
}
