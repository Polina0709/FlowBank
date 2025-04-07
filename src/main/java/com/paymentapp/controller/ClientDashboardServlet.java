package com.paymentapp.controller;

import com.paymentapp.dao.AccountDAO;
import com.paymentapp.dao.CardDAO;
import com.paymentapp.model.Account;
import com.paymentapp.model.Card;
import com.paymentapp.model.User;
import com.paymentapp.dao.TransactionDAO;
import com.paymentapp.model.Transaction;


import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ClientDashboardServlet extends HttpServlet {

    private final AccountDAO accountDAO = new AccountDAO();
    private final CardDAO cardDAO = new CardDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User user = (User) request.getSession().getAttribute("user");

        if (user == null) {
            response.sendRedirect("signin_client.jsp");
            return;
        }

        List<Account> accounts = accountDAO.findByUserId(user.getId());
        List<Card> cards = new ArrayList<>();

        for (Account acc : accounts) {
            cards.addAll(cardDAO.findByAccountId(acc.getId()));
        }

        request.setAttribute("cards", cards);

        TransactionDAO transactionDAO = new TransactionDAO();

// збір ID рахунків
        List<Integer> accountIds = new ArrayList<>();
        for (Account acc : accounts) {
            accountIds.add(acc.getId());
        }

// отримуємо історію транзакцій
        List<Transaction> transactions = transactionDAO.findByUserAccountIds(accountIds);

        request.setAttribute("transactions", transactions);

        request.getRequestDispatcher("client_dashboard.jsp").forward(request, response);
    }
}
