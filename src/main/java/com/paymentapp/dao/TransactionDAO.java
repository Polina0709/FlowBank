package com.paymentapp.dao;

import com.paymentapp.model.Transaction;
import com.paymentapp.util.DBUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;

import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class TransactionDAO {

    private static final Logger logger = (Logger) LogManager.getLogger(TransactionDAO.class);

    public boolean transferFunds(int senderAccountId, int receiverAccountId, BigDecimal amount) {
        String insertTransactionSQL = "INSERT INTO transactions (sender_account_id, receiver_account_id, amount, created_at) VALUES (?, ?, ?, NOW())";
        String getBalanceSQL = "SELECT balance FROM accounts WHERE id = ?";
        String updateBalanceSQL = "UPDATE accounts SET balance = ? WHERE id = ?";

        try (Connection conn = DBUtil.getConnection()) {
            conn.setAutoCommit(false); // початок транзакції

            boolean isSystemTopUp = senderAccountId == 0;

            // 1. Якщо це не системне поповнення — перевірити баланс і зменшити
            if (!isSystemTopUp) {
                BigDecimal senderBalance;
                try (PreparedStatement ps = conn.prepareStatement(getBalanceSQL)) {
                    ps.setInt(1, senderAccountId);
                    ResultSet rs = ps.executeQuery();
                    if (!rs.next()) {
                        conn.rollback();
                        logger.warn("Рахунок відправника не знайдено: " + senderAccountId);
                        return false;
                    }
                    senderBalance = rs.getBigDecimal("balance");
                }

                if (senderBalance.compareTo(amount) < 0) {
                    conn.rollback();
                    logger.warn("Недостатньо коштів на рахунку ID: " + senderAccountId);
                    return false;
                }

                BigDecimal newSenderBalance = senderBalance.subtract(amount);
                try (PreparedStatement ps = conn.prepareStatement(updateBalanceSQL)) {
                    ps.setBigDecimal(1, newSenderBalance);
                    ps.setInt(2, senderAccountId);
                    ps.executeUpdate();
                }
            }

            // 2. Збільшення балансу отримувача
            BigDecimal receiverBalance;
            try (PreparedStatement ps = conn.prepareStatement(getBalanceSQL)) {
                ps.setInt(1, receiverAccountId);
                ResultSet rs = ps.executeQuery();
                if (!rs.next()) {
                    conn.rollback();
                    logger.warn("Рахунок отримувача не знайдено: " + receiverAccountId);
                    return false;
                }
                receiverBalance = rs.getBigDecimal("balance");
            }

            BigDecimal newReceiverBalance = receiverBalance.add(amount);
            try (PreparedStatement ps = conn.prepareStatement(updateBalanceSQL)) {
                ps.setBigDecimal(1, newReceiverBalance);
                ps.setInt(2, receiverAccountId);
                ps.executeUpdate();
            }

            // 3. Запис транзакції
            try (PreparedStatement ps = conn.prepareStatement(insertTransactionSQL)) {
                if (isSystemTopUp) {
                    ps.setNull(1, Types.INTEGER); // system transfer → sender = NULL
                } else {
                    ps.setInt(1, senderAccountId);
                }
                ps.setInt(2, receiverAccountId);
                ps.setBigDecimal(3, amount);
                ps.executeUpdate();
            }

            conn.commit();
            logger.info("Переказ успішний: від " + senderAccountId + " до " + receiverAccountId + ", сума: " + amount);
            return true;

        } catch (SQLException e) {
            logger.error("Помилка при переказі коштів", e);
            return false;
        }
    }
    public List<Transaction> findAll() {
        List<Transaction> transactions = new ArrayList<>();
        String sql = "SELECT * FROM transactions";
        try (Connection conn = DBUtil.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Transaction t = new Transaction();
                t.setId(rs.getInt("id"));
                t.setSenderAccountId(rs.getInt("sender_account_id"));
                t.setReceiverAccountId(rs.getInt("receiver_account_id"));
                t.setAmount(rs.getBigDecimal("amount"));
                t.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
                transactions.add(t);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return transactions;
    }

    public List<Transaction> findByUserAccountIds(List<Integer> accountIds) {
        List<Transaction> transactions = new ArrayList<>();
        if (accountIds == null || accountIds.isEmpty()) return transactions;

        StringBuilder sql = new StringBuilder("SELECT * FROM transactions WHERE sender_account_id IN (");
        sql.append("?,".repeat(accountIds.size()));
        sql.setLength(sql.length() - 1); // remove last comma
        sql.append(") OR receiver_account_id IN (");
        sql.append("?,".repeat(accountIds.size()));
        sql.setLength(sql.length() - 1);
        sql.append(") ORDER BY created_at DESC");

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql.toString())) {

            int index = 1;
            for (int id : accountIds) ps.setInt(index++, id);
            for (int id : accountIds) ps.setInt(index++, id);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Transaction t = new Transaction();
                t.setId(rs.getInt("id"));
                t.setSenderAccountId(rs.getInt("sender_account_id"));
                t.setReceiverAccountId(rs.getInt("receiver_account_id"));
                t.setAmount(rs.getBigDecimal("amount"));
                t.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
                transactions.add(t);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return transactions;
    }

}

