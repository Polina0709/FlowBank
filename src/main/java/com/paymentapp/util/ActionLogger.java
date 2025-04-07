package com.paymentapp.util;

import com.paymentapp.dao.LogDAO;

public class ActionLogger {
    private static final LogDAO logDAO = new LogDAO();

    public static void log(int userId, String action) {
        logDAO.logAction(userId, action);
    }
}
