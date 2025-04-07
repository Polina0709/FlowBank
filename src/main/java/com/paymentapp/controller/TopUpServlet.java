package com.paymentapp.controller;

import com.paymentapp.dao.AccountDAO;
import com.paymentapp.dao.TransactionDAO;
import com.paymentapp.model.Account;
import com.paymentapp.model.User;
import com.paymentapp.util.ActionLogger;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.math.BigDecimal;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class TopUpServlet extends HttpServlet {

    private static final Logger logger = LogManager.getLogger(TopUpServlet.class);

    private final AccountDAO accountDAO = new AccountDAO();
    private final TransactionDAO transactionDAO = new TransactionDAO();

    private static final int SYSTEM_ACCOUNT_ID = 0;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String accountNumber = request.getParameter("accountNumber");
        String amountStr = request.getParameter("amount");

        if (accountNumber == null || amountStr == null) {
            request.setAttribute("error", "Будь ласка, заповніть усі поля.");
            request.getRequestDispatcher("topup.jsp").forward(request, response);
            return;
        }

        BigDecimal amount = new BigDecimal(amountStr);
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            request.setAttribute("error", "Сума має бути більшою за 0.");
            request.getRequestDispatcher("topup.jsp").forward(request, response);
            return;
        }

        Account receiver = accountDAO.findByAccountNumber(accountNumber);

        if (receiver == null) {
            logger.warn("Поповнення: рахунок не знайдено – " + accountNumber);
            request.setAttribute("error", "Рахунок не знайдено.");
        } else if (receiver.isBlocked()) {
            logger.warn("Поповнення: рахунок заблокований – " + accountNumber);
            request.setAttribute("error", "Цей рахунок заблокований.");
        } else {
            boolean success = transactionDAO.transferFunds(SYSTEM_ACCOUNT_ID, receiver.getId(), amount);

            if (success) {
                logger.info("Поповнення успішне на " + amount + " → " + accountNumber);
                request.setAttribute("success", "✅ Поповнення успішно виконано.");

                User user = (User) request.getSession().getAttribute("user");
                if (user != null) {
                    ActionLogger.log(user.getId(), "Поповнення рахунку " + accountNumber + " на " + amount + " грн.");
                }
            } else {
                logger.error("Помилка при поповненні рахунку " + accountNumber);
                request.setAttribute("error", "❌ Помилка при поповненні рахунку.");
            }
        }

        request.getRequestDispatcher("topup.jsp").forward(request, response);
    }
}
