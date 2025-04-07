package com.paymentapp.controller;

import com.paymentapp.dao.AccountDAO;
import com.paymentapp.dao.TransactionDAO;
import com.paymentapp.model.Account;
import com.paymentapp.model.User;
import com.paymentapp.util.ActionLogger;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.math.BigDecimal;

public class TransferServlet extends HttpServlet {

    private final AccountDAO accountDAO = new AccountDAO();
    private final TransactionDAO transactionDAO = new TransactionDAO();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String senderAccountNumber = request.getParameter("senderAccount");
        String receiverAccountNumber = request.getParameter("receiverAccount");
        String amountStr = request.getParameter("amount");

        if (senderAccountNumber == null || receiverAccountNumber == null || amountStr == null) {
            request.setAttribute("error", "Будь ласка, заповніть усі поля.");
            request.getRequestDispatcher("transfer.jsp").forward(request, response);
            return;
        }

        BigDecimal amount = new BigDecimal(amountStr);
        Account sender = accountDAO.findByAccountNumber(senderAccountNumber);
        Account receiver = accountDAO.findByAccountNumber(receiverAccountNumber);

        if (sender == null || receiver == null) {
            request.setAttribute("error", "Один з рахунків не існує.");
        } else if (sender.isBlocked() || receiver.isBlocked()) {
            request.setAttribute("error", "Один з рахунків заблокований.");
        } else {
            boolean success = transactionDAO.transferFunds(sender.getId(), receiver.getId(), amount);

            if (success) {
                request.setAttribute("success", "✅ Переказ успішно виконано.");
                User user = (User) request.getSession().getAttribute("user");
                if (user != null) {
                    ActionLogger.log(user.getId(), "Переказ коштів з " +
                            senderAccountNumber + " на " + receiverAccountNumber +
                            " на суму " + amount + " грн.");
                }
            } else {
                request.setAttribute("error", "❌ Переказ неможливий: недостатньо коштів або помилка.");
                request.setAttribute("error", "❌ Помилка при виконанні переказу.");
            }
        }

        request.getRequestDispatcher("transfer.jsp").forward(request, response);
    }
}

