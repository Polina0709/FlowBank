package com.paymentapp.dao;

import com.paymentapp.model.User;
import com.paymentapp.util.DBUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {
    static final Logger logger = LogManager.getLogger(UserDAO.class);

    public User findById(int id) {
        String sql = "SELECT * FROM users WHERE id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                logger.info("Користувача знайдено з ID: " + id);
                return extractUser(rs);
            } else {
                logger.warn("Користувача не знайдено з ID: " + id);
            }

        } catch (SQLException e) {
            logger.error("Помилка при пошуку користувача з ID: " + id, e);
        }
        return null;
    }

    public List<User> findAll() {
        List<User> users = new ArrayList<>();
        String sql = "SELECT * FROM users";

        try (Connection conn = DBUtil.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                users.add(extractUser(rs));
            }

            logger.info("Отримано всіх користувачів. Кількість: " + users.size());

        } catch (SQLException e) {
            logger.error("Помилка при отриманні всіх користувачів", e);
        }

        return users;
    }

    public boolean create(User user) {
        String sql = "INSERT INTO users (full_name, personal_number, email, password, created_at) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, user.getFullName());
            ps.setString(2, user.getPersonalNumber());
            ps.setString(3, user.getEmail());
            ps.setString(4, user.getPassword());
            ps.setTimestamp(5, Timestamp.valueOf(user.getCreatedAt()));

            boolean result = ps.executeUpdate() > 0;

            if (result) {
                logger.info("Користувача успішно створено: " + user.getFullName());
            } else {
                logger.warn("Не вдалося створити користувача: " + user.getFullName());
            }

            return result;

        } catch (SQLException e) {
            logger.error("Помилка при створенні користувача: " + user.getFullName(), e);
        }

        return false;
    }

    private User extractUser(ResultSet rs) throws SQLException {
        User user = new User();
        user.setId(rs.getInt("id"));
        user.setFullName(rs.getString("full_name"));
        user.setPersonalNumber(rs.getString("personal_number"));
        user.setEmail(rs.getString("email"));
        user.setPassword(rs.getString("password"));
        user.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
        return user;
    }
}
