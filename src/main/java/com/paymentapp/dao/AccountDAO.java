package com.paymentapp.dao;

import com.paymentapp.model.Account;
import com.paymentapp.util.DBUtil;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static com.paymentapp.dao.UserDAO.logger;

public class AccountDAO {

    public List<Account> findByUserId(int userId) {
        List<Account> accounts = new ArrayList<>();
        String sql = "SELECT * FROM accounts WHERE user_id = ? AND account_number != 'SYSTEM_ACC_0001'";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                accounts.add(extractAccount(rs));
            }

        } catch (SQLException e) {
            logger.error("Помилка при отриманні рахунків користувача ID: " + userId, e);
        }

        return accounts;
    }


    public boolean updateBlockedStatus(int accountId, boolean blocked) {
        String sql = "UPDATE accounts SET is_blocked = ? WHERE id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setBoolean(1, blocked);
            ps.setInt(2, accountId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    private Account extractAccount(ResultSet rs) throws SQLException {
        Account account = new Account();
        account.setId(rs.getInt("id"));
        account.setUserId(rs.getInt("user_id"));
        account.setAccountNumber(rs.getString("account_number"));
        account.setBlocked(rs.getBoolean("is_blocked"));
        account.setBalance(rs.getBigDecimal("balance")); // додано
        return account;
    }

    public boolean updateBalance(int accountId, BigDecimal newBalance) {
        String sql = "UPDATE accounts SET balance = ? WHERE id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setBigDecimal(1, newBalance);
            ps.setInt(2, accountId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            logger.error("Помилка при оновленні балансу рахунку ID: " + accountId, e);
            return false;
        }
    }

    public Account findByAccountNumber(String number) {
        String sql = "SELECT * FROM accounts WHERE account_number = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, number);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return extractAccount(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Account> findAll() {
        List<Account> accounts = new ArrayList<>();
        String sql = "SELECT * FROM accounts WHERE account_number != 'SYSTEM_ACC_0001'";

        try (Connection conn = DBUtil.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                accounts.add(extractAccount(rs));
            }

        } catch (SQLException e) {
            logger.error("Помилка при отриманні всіх рахунків (адмін)", e);
        }

        return accounts;
    }
}

