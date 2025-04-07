package com.paymentapp.dao;

import com.paymentapp.model.Log;
import com.paymentapp.util.DBUtil;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class LogDAO {

    public void logAction(int userId, String action) {
        String sql = "INSERT INTO logs (user_id, action, timestamp) VALUES (?, ?, ?)";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, userId);
            ps.setString(2, action);
            ps.setTimestamp(3, Timestamp.valueOf(LocalDateTime.now()));
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Log> findByUserId(int userId) {
        List<Log> logs = new ArrayList<>();
        String sql = "SELECT * FROM logs WHERE user_id = ? ORDER BY timestamp DESC";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Log log = new Log();
                log.setId(rs.getInt("id"));
                log.setUserId(rs.getInt("user_id"));
                log.setAction(rs.getString("action"));
                log.setTimestamp(rs.getTimestamp("timestamp").toLocalDateTime());
                logs.add(log);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return logs;
    }

}

