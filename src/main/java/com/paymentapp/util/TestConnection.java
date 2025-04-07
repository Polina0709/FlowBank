package com.paymentapp.util;

import java.sql.Connection;
import java.sql.SQLException;

public class TestConnection {
    public static void main(String[] args) {
        try (Connection conn = DBUtil.getConnection()) {
            if (conn != null) {
                System.out.println("✅ Успішне підключення до бази даних!");
            } else {
                System.out.println("❌ Підключення не вдалося.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
