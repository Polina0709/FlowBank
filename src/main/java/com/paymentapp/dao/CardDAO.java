package com.paymentapp.dao;

import com.paymentapp.model.Card;
import com.paymentapp.util.DBUtil;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class CardDAO {

    public List<Card> findByAccountId(int accountId) {
        List<Card> cards = new ArrayList<>();
        String sql = "SELECT * FROM cards WHERE account_id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, accountId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Card card = new Card();
                card.setId(rs.getInt("id"));
                card.setAccountId(rs.getInt("account_id"));
                card.setCardNumber(rs.getString("card_number"));
                card.setExpirationDate(rs.getDate("expiration_date").toLocalDate());
                card.setCvv(rs.getString("cvv"));
                cards.add(card);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cards;
    }

}
