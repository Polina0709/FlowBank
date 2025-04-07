package com.paymentapp.controller;

import com.paymentapp.dao.LogDAO;
import com.paymentapp.model.Log;
import com.paymentapp.model.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class LogRestServlet extends HttpServlet {

    private final LogDAO logDAO = new LogDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json; charset=UTF-8");
        response.setCharacterEncoding("UTF-8");

        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("{\"error\": \"Unauthorized\"}");
            return;
        }

        List<Log> logs = logDAO.findByUserId(user.getId());

        PrintWriter out = response.getWriter();
        out.print("[");
        for (int i = 0; i < logs.size(); i++) {
            Log log = logs.get(i);
            out.print("{");
            out.print("\"id\":" + log.getId() + ",");
            out.print("\"action\":\"" + escape(log.getAction()) + "\",");
            out.print("\"timestamp\":\"" + log.getTimestamp() + "\"");
            out.print("}");
            if (i < logs.size() - 1) out.print(",");
        }
        out.print("]");
        out.flush();
    }

    // Helper to escape JSON
    private String escape(String str) {
        return str.replace("\"", "\\\"").replace("\n", "").replace("\r", "");
    }
}
