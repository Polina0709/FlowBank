package com.paymentapp.controller;

import jakarta.servlet.http.*;

import java.io.IOException;

public class AdminLogoutServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        HttpSession session = req.getSession(false);
        if (session != null) session.invalidate();
        resp.sendRedirect("signin_admin.jsp");
    }
}
