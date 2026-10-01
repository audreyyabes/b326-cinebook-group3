package com.joysistvi.cinebookapp.repository;

import com.joysistvi.cinebookapp.database.DatabaseConnection;
import com.joysistvi.cinebookapp.model.Booking;
import com.joysistvi.cinebookapp.model.BookingAudit;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class BookingRepoImpl implements BookingRepo {

    private final DatabaseConnection databaseConnection;

    public BookingRepoImpl() {
        databaseConnection = new DatabaseConnection();
    }

    @Override
    public List<Booking> getAllBookings() {

        List<Booking> bookings = new ArrayList<>();

        String sql = """
                SELECT id,
                       booking_code,
                       user_id,
                       showtime_id,
                       booking_date,
                       total_amount,
                       status
                FROM bookings
                ORDER BY id
                """;

        try (Connection connection = databaseConnection.connect();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {

                Timestamp timestamp =
                        resultSet.getTimestamp("booking_date");

                Booking booking = new Booking(
                        resultSet.getInt("id"),
                        resultSet.getString("booking_code"),
                        resultSet.getInt("user_id"),
                        resultSet.getInt("showtime_id"),
                        timestamp != null
                                ? timestamp.toLocalDateTime()
                                : null,
                        resultSet.getDouble("total_amount"),
                        resultSet.getString("status")
                );

                bookings.add(booking);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return bookings;
    }

    @Override
    public Booking getBookingById(int id) {

        String sql = """
                SELECT id,
                       booking_code,
                       user_id,
                       showtime_id,
                       booking_date,
                       total_amount,
                       status
                FROM bookings
                WHERE id = ?
                """;

        try (Connection connection = databaseConnection.connect();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, id);

            try (ResultSet resultSet = statement.executeQuery()) {

                if (resultSet.next()) {

                    Timestamp timestamp =
                            resultSet.getTimestamp("booking_date");

                    return new Booking(
                            resultSet.getInt("id"),
                            resultSet.getString("booking_code"),
                            resultSet.getInt("user_id"),
                            resultSet.getInt("showtime_id"),
                            timestamp != null
                                    ? timestamp.toLocalDateTime()
                                    : null,
                            resultSet.getDouble("total_amount"),
                            resultSet.getString("status")
                    );
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public List<BookingAudit> getBookingAudit() {

        List<BookingAudit> audits = new ArrayList<>();

        String sql = """
        SELECT
            b.id,
            b.booking_code,
            u.name AS customer_name,
            b.total_amount,
            b.status AS booking_status,
            p.payment_method,
            p.status AS payment_status,

            (
                SELECT COUNT(*)
                FROM booking_seats bs
                WHERE bs.booking_id = b.id
            ) AS seat_count

        FROM bookings b

        LEFT JOIN users u
            ON b.user_id = u.id

        LEFT JOIN payments p
            ON b.id = p.booking_id

        ORDER BY b.id
        """;

        try (Connection connection = databaseConnection.connect();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {

                BookingAudit audit = new BookingAudit(
                        resultSet.getInt("id"),
                        resultSet.getString("booking_code"),
                        resultSet.getString("customer_name"),
                        resultSet.getDouble("total_amount"),
                        resultSet.getString("booking_status"),
                        resultSet.getString("payment_method"),
                        resultSet.getString("payment_status"),
                        resultSet.getInt("seat_count")
                );

                audits.add(audit);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return audits;
    }

    @Override
    public boolean createBooking(Booking booking) {

        String sql = """
                INSERT INTO bookings
                (
                    booking_code,
                    user_id,
                    showtime_id,
                    booking_date,
                    total_amount,
                    status
                )
                VALUES (?, ?, ?, ?, ?, ?)
                """;

        try (Connection connection = databaseConnection.connect();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, booking.getBookingCode());
            statement.setInt(2, booking.getUserId());
            statement.setInt(3, booking.getShowtimeId());

            if (booking.getBookingDate() != null) {
                statement.setTimestamp(4, Timestamp.valueOf(booking.getBookingDate())
                );
            } else {
                statement.setTimestamp(4, new Timestamp(System.currentTimeMillis())
                );
            }

            statement.setDouble(5, booking.getTotalAmount());

            statement.setString(6, booking.getStatus());

            return statement.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public boolean updateBooking(Booking booking) {

        String sql = """
                UPDATE bookings
                SET booking_code = ?,
                    user_id = ?,
                    showtime_id = ?,
                    booking_date = ?,
                    total_amount = ?,
                    status = ?
                WHERE id = ?
                """;

        try (Connection connection = databaseConnection.connect();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, booking.getBookingCode());
            statement.setInt(2, booking.getUserId());
            statement.setInt(3, booking.getShowtimeId());

            statement.setTimestamp(4, Timestamp.valueOf(booking.getBookingDate())
            );

            statement.setDouble(5, booking.getTotalAmount());

            statement.setString(6, booking.getStatus());

            statement.setInt(7, booking.getId());

            return statement.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public boolean deleteBooking(int id) {

        String sql = "DELETE FROM bookings WHERE id = ?";

        try (Connection connection = databaseConnection.connect();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, id);

            return statement.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public boolean confirmBooking(int id) {

        String bookingSql = """
                UPDATE bookings
                SET status = 'confirmed'
                WHERE id = ?
                AND status = 'pending'
                """;

        String paymentSql = """
                UPDATE payments
                SET status = 'paid',
                    paid_at = NOW()
                WHERE booking_id = ?
                AND status = 'pending'
                """;

        Connection connection = null;

        try {

            connection = databaseConnection.connect();

            connection.setAutoCommit(false);

            try (PreparedStatement statement = connection.prepareStatement(bookingSql)) {

                statement.setInt(1, id);

                int updated = statement.executeUpdate();

                if (updated == 0) {
                    connection.rollback();
                    return false;
                }
            }

            try (PreparedStatement statement = connection.prepareStatement(paymentSql)) {

                statement.setInt(1, id);
                statement.executeUpdate();
            }

            connection.commit();

            return true;

        } catch (SQLException e) {

            if (connection != null) {
                try {
                    connection.rollback();
                } catch (SQLException rollbackError) {
                    rollbackError.printStackTrace();
                }
            }

            e.printStackTrace();

        } finally {

            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException closeError) {
                    closeError.printStackTrace();
                }
            }
        }

        return false;
    }

    @Override
    public double getConfirmedRevenue() {

        String sql = """
                SELECT COALESCE(
                    SUM(total_amount), 0
                )
                FROM bookings
                WHERE status = 'confirmed'
                """;

        try (Connection connection = databaseConnection.connect();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            if (resultSet.next()) {
                return resultSet.getDouble(1);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return 0;
    }

    @Override
    public double getPendingTotal() {

        String sql = """
                SELECT COALESCE(
                    SUM(total_amount), 0
                )
                FROM bookings
                WHERE status = 'pending'
                """;

        try (Connection connection = databaseConnection.connect();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            if (resultSet.next()) {
                return resultSet.getDouble(1);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return 0;
    }

    @Override
    public int getTotalTicketsReserved() {

        String sql = """
                SELECT COUNT(*)
                FROM booking_seats
                """;

        try (Connection connection = databaseConnection.connect();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            if (resultSet.next()) {
                return resultSet.getInt(1);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return 0;
    }
}
