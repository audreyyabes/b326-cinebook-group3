package com.joysistvi.cinebookapp.repository;

import com.joysistvi.cinebookapp.database.DatabaseConnection;
import com.joysistvi.cinebookapp.model.Payment;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;

public class PaymentRepoImpl implements PaymentRepo {

    private final DatabaseConnection databaseConnection = new DatabaseConnection();

    @Override
    public void create(Payment payment) {
        String sql = "INSERT INTO payments (booking_id, payment_reference, payment_method, amount, status, paid_at) "
                + "VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection connection = databaseConnection.connect();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, payment.getBookingId());
            statement.setString(2, payment.getPaymentReference());
            statement.setString(3, payment.getPaymentMethod());
            statement.setBigDecimal(4, payment.getAmount());
            statement.setString(5, payment.getStatus());

            if (payment.getPaidAt() != null) {
                statement.setTimestamp(6, Timestamp.valueOf(payment.getPaidAt()));
            } else {
                statement.setNull(6, Types.TIMESTAMP);
            }

            statement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Failed to create payment", e);
        }
    }
}
