package com.joysistvi.cinebookapp.repository;

import com.joysistvi.cinebookapp.database.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UserBookingRepoImpl implements UserBookingRepo {

    private final DatabaseConnection databaseConnection;

    public UserBookingRepoImpl() {
        databaseConnection = new DatabaseConnection();
    }

    @Override
    public boolean isCustomer(int userId) {

        String sql = """
                SELECT id
                FROM users
                WHERE id = ?
                AND role = 'customer'
                """;

        try (Connection connection = databaseConnection.connect();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, userId);

            try (ResultSet resultSet = statement.executeQuery()) {

                return resultSet.next();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public List<String> getAvailableShowtimes() {

        List<String> showtimes = new ArrayList<>();

        String sql = """
                SELECT
                    s.id AS showtime_id,
                    m.title,
                    t.name AS theater_name,
                    s.start_time,
                    s.ticket_price
                FROM showtimes s

                JOIN movies m
                    ON s.movie_id = m.id

                JOIN theaters t
                    ON s.theater_id = t.id

                WHERE s.status = 'scheduled'
                AND m.status = 'active'
                AND t.status = 'active'

                ORDER BY s.start_time
                """;

        try (Connection connection = databaseConnection.connect();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {

                String showtime = resultSet.getInt("showtime_id")
                                + " | "
                                + resultSet.getString("title")
                                + " | "
                                + resultSet.getString("theater_name")
                                + " | "
                                + resultSet.getTimestamp("start_time")
                                + " | ₱"
                                + resultSet.getDouble("ticket_price");

                showtimes.add(showtime);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return showtimes;
    }

    @Override
    public List<String> getSeatsForShowtime(int showtimeId) {

        List<String> seats = new ArrayList<>();

        String sql = """
                SELECT
                    s.id,
                    s.seat_code,

                    CASE
                        WHEN EXISTS (
                            SELECT 1
                            FROM booking_seats bs

                            JOIN bookings b
                                ON bs.booking_id = b.id

                            WHERE bs.seat_id = s.id
                            AND b.showtime_id = ?
                            AND b.status IN ('pending', 'confirmed')
                        )
                        THEN 'BOOKED'
                        ELSE 'AVAILABLE'
                    END AS seat_status

                FROM seats s

                JOIN showtimes st
                    ON s.theater_id = st.theater_id

                WHERE st.id = ?

                ORDER BY s.seat_row, s.seat_number
                """;

        try (Connection connection = databaseConnection.connect();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, showtimeId);
            statement.setInt(2, showtimeId);

            try (ResultSet resultSet = statement.executeQuery()) {

                while (resultSet.next()) {

                    String seat = resultSet.getInt("id")
                                    + " | "
                                    + resultSet.getString("seat_code")
                                    + " | "
                                    + resultSet.getString("seat_status");

                    seats.add(seat);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return seats;
    }

    @Override
    public boolean createBooking(
            int userId,
            int showtimeId,
            List<Integer> seatIds,
            double totalAmount,
            String paymentMethod
    ) {

        String bookingSql = """
                INSERT INTO bookings
                (
                    booking_code,
                    user_id,
                    showtime_id,
                    total_amount,
                    status
                )
                VALUES (?, ?, ?, ?, 'pending')
                """;

        String seatSql = """
                INSERT INTO booking_seats
                (
                    booking_id,
                    seat_id,
                    price
                )
                VALUES (?, ?, ?)
                """;

        String paymentSql = """
                INSERT INTO payments
                (
                    booking_id,
                    payment_reference,
                    payment_method,
                    amount,
                    status
                )
                VALUES (?, ?, ?, ?, 'pending')
                """;

        Connection connection = null;

        try {

            connection = databaseConnection.connect();

            connection.setAutoCommit(false);

            int bookingId;

            try (PreparedStatement statement =
                         connection.prepareStatement(
                                 bookingSql,
                                 Statement.RETURN_GENERATED_KEYS
                         )) {

                String bookingCode =
                        "CB-"
                                + System.currentTimeMillis();

                statement.setString(1, bookingCode);
                statement.setInt(2, userId);
                statement.setInt(3, showtimeId);
                statement.setDouble(4, totalAmount);

                statement.executeUpdate();

                try (ResultSet keys =
                             statement.getGeneratedKeys()) {

                    if (!keys.next()) {

                        connection.rollback();
                        return false;
                    }

                    bookingId = keys.getInt(1);
                }
            }

            double seatPrice = totalAmount / seatIds.size();

            try (PreparedStatement statement = connection.prepareStatement(seatSql)) {

                for (Integer seatId : seatIds) {

                    statement.setInt(1, bookingId);
                    statement.setInt(2, seatId);
                    statement.setDouble(3, seatPrice);

                    statement.addBatch();
                }

                statement.executeBatch();
            }

            try (PreparedStatement statement = connection.prepareStatement(paymentSql)) {

                String paymentReference =
                        "PAY-"
                                + System.currentTimeMillis();

                statement.setInt(1, bookingId);
                statement.setString(2, paymentReference);
                statement.setString(3, paymentMethod);
                statement.setDouble(4, totalAmount);

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
    public List<String> getMyBookings(int userId) {

        List<String> bookings = new ArrayList<>();

        String sql = """
                SELECT
                    b.booking_code,
                    m.title,
                    st.start_time,
                    t.name AS theater_name,
                    b.total_amount,
                    b.status

                FROM bookings b

                JOIN showtimes st
                    ON b.showtime_id = st.id

                JOIN movies m
                    ON st.movie_id = m.id

                JOIN theaters t
                    ON st.theater_id = t.id

                WHERE b.user_id = ?

                ORDER BY b.booking_date DESC
                """;

        try (Connection connection = databaseConnection.connect();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, userId);

            try (ResultSet resultSet = statement.executeQuery()) {

                while (resultSet.next()) {

                    String booking =
                            resultSet.getString("booking_code")
                                    + " | "
                                    + resultSet.getString("title")
                                    + " | "
                                    + resultSet.getTimestamp("start_time")
                                    + " | "
                                    + resultSet.getString("theater_name")
                                    + " | ₱"
                                    + resultSet.getDouble("total_amount")
                                    + " | "
                                    + resultSet.getString("status");

                    bookings.add(booking);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return bookings;
    }
}
