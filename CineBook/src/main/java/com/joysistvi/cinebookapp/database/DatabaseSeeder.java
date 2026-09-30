package com.joysistvi.cinebookapp.database;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseSeeder {

    private static final Logger logger = LoggerFactory.getLogger(DatabaseSeeder.class);

    private static final String[] SEED_STATEMENTS = {
            "INSERT INTO theaters (id, name, location, status) VALUES " +
                    "(1, 'Cinema 1', 'CineBook Mall - Quezon City', 'active')," +
                    "(2, 'Cinema 2', 'CineBook Mall - Quezon City', 'active')," +
                    "(3, 'Cinema 3', 'CineBook Mall - Quezon City', 'active')",

            "INSERT INTO movies (id, title, description, duration_minutes, genre, release_date, rating, status) VALUES " +
                    "(1, 'Avengers: Endgame', 'The Avengers face their greatest challenge in an epic battle to save the universe.', 181, 'Action, Adventure', '2019-04-26', 'PG-13', 'active')," +
                    "(2, 'Spider-Man: No Way Home', 'Peter Parker faces new challenges after his identity is revealed.', 148, 'Action, Adventure', '2021-12-17', 'PG-13', 'active')," +
                    "(3, 'Inside Out 2', 'Riley enters her teenage years and experiences new emotions.', 96, 'Animation, Comedy', '2024-06-14', 'PG', 'active')," +
                    "(4, 'The Batman', 'Batman investigates corruption and crime in Gotham City.', 176, 'Action, Crime', '2022-03-04', 'PG-13', 'active')," +
                    "(5, 'Interstellar', 'A team of explorers travels through a wormhole in search of a new home for humanity.', 169, 'Science Fiction, Drama', '2014-11-07', 'PG-13', 'active')",

            "INSERT INTO users (id, name, email, password_hash, role, created_at) VALUES " +
                    "(1, 'Admin User', 'admin@cinebook.com', '$2a$10$exampleadminhash', 'admin', '2026-09-29 13:26:50')," +
                    "(2, 'Audrey Yabes', 'audrey@gmail.com', '$2a$10$examplecustomerhash', 'customer', '2026-09-29 13:26:50')," +
                    "(3, 'John Santos', 'john@gmail.com', '$2a$10$examplejohnhash', 'customer', '2026-09-29 13:26:50')," +
                    "(4, 'Maria Cruz', 'maria@gmail.com', '$2a$10$examplemariahash', 'customer', '2026-09-29 13:26:50')",

            "INSERT INTO seats (id, theater_id, seat_code, seat_row, seat_number) VALUES " +
                    "(1, 1, 'A1', 'A', 1),(2, 1, 'A2', 'A', 2),(3, 1, 'A3', 'A', 3),(4, 1, 'A4', 'A', 4),(5, 1, 'A5', 'A', 5)," +
                    "(6, 1, 'B1', 'B', 1),(7, 1, 'B2', 'B', 2),(8, 1, 'B3', 'B', 3),(9, 1, 'B4', 'B', 4),(10, 1, 'B5', 'B', 5)," +
                    "(11, 1, 'C1', 'C', 1),(12, 1, 'C2', 'C', 2),(13, 1, 'C3', 'C', 3),(14, 1, 'C4', 'C', 4),(15, 1, 'C5', 'C', 5)," +
                    "(16, 2, 'A1', 'A', 1),(17, 2, 'A2', 'A', 2),(18, 2, 'A3', 'A', 3),(19, 2, 'A4', 'A', 4),(20, 2, 'A5', 'A', 5)," +
                    "(21, 2, 'B1', 'B', 1),(22, 2, 'B2', 'B', 2),(23, 2, 'B3', 'B', 3),(24, 2, 'B4', 'B', 4),(25, 2, 'B5', 'B', 5)," +
                    "(26, 2, 'C1', 'C', 1),(27, 2, 'C2', 'C', 2),(28, 2, 'C3', 'C', 3),(29, 2, 'C4', 'C', 4),(30, 2, 'C5', 'C', 5)," +
                    "(31, 3, 'A1', 'A', 1),(32, 3, 'A2', 'A', 2),(33, 3, 'A3', 'A', 3),(34, 3, 'A4', 'A', 4),(35, 3, 'A5', 'A', 5)," +
                    "(36, 3, 'B1', 'B', 1),(37, 3, 'B2', 'B', 2),(38, 3, 'B3', 'B', 3),(39, 3, 'B4', 'B', 4),(40, 3, 'B5', 'B', 5)," +
                    "(41, 3, 'C1', 'C', 1),(42, 3, 'C2', 'C', 2),(43, 3, 'C3', 'C', 3),(44, 3, 'C4', 'C', 4),(45, 3, 'C5', 'C', 5)",

            "INSERT INTO showtimes (id, movie_id, theater_id, start_time, end_time, ticket_price, status) VALUES " +
                    "(1, 1, 1, '2026-10-01 10:00:00', '2026-10-01 13:01:00', 350.00, 'scheduled')," +
                    "(2, 1, 2, '2026-10-01 14:00:00', '2026-10-01 17:01:00', 350.00, 'scheduled')," +
                    "(3, 2, 1, '2026-10-01 16:00:00', '2026-10-01 18:28:00', 300.00, 'scheduled')," +
                    "(4, 2, 3, '2026-10-01 19:00:00', '2026-10-01 21:28:00', 350.00, 'scheduled')," +
                    "(5, 3, 2, '2026-10-02 10:00:00', '2026-10-02 11:36:00', 250.00, 'scheduled')," +
                    "(6, 3, 3, '2026-10-02 13:00:00', '2026-10-02 14:36:00', 250.00, 'scheduled')," +
                    "(7, 4, 1, '2026-10-02 17:00:00', '2026-10-02 19:56:00', 350.00, 'scheduled')," +
                    "(8, 4, 2, '2026-10-02 20:00:00', '2026-10-02 22:56:00', 400.00, 'scheduled')," +
                    "(9, 5, 3, '2026-10-03 15:00:00', '2026-10-03 17:49:00', 350.00, 'scheduled')",

            "INSERT INTO bookings (id, booking_code, user_id, showtime_id, booking_date, total_amount, status) VALUES " +
                    "(1, 'CB-20261001-001', 2, 1, '2026-09-29 02:30:00', 700.00, 'confirmed')," +
                    "(2, 'CB-20261001-002', 3, 3, '2026-09-29 03:00:00', 300.00, 'confirmed')," +
                    "(3, 'CB-20261001-003', 4, 4, '2026-09-29 03:30:00', 700.00, 'pending')," +
                    "(4, 'CB-20261002-001', 2, 5, '2026-09-29 04:00:00', 500.00, 'confirmed')",

            "INSERT INTO booking_seats (id, booking_id, seat_id, price) VALUES " +
                    "(1, 1, 1, 350.00),(2, 1, 2, 350.00),(3, 2, 3, 300.00),(4, 3, 26, 350.00),(5, 3, 27, 350.00),(6, 4, 16, 250.00),(7, 4, 17, 250.00)",

            "INSERT INTO payments (id, booking_id, payment_reference, payment_method, amount, status, paid_at) VALUES " +
                    "(1, 1, 'PAY-20261001-001', 'GCash', 700.00, 'paid', '2026-09-29 10:35:00')," +
                    "(2, 2, 'PAY-20261001-002', 'Credit Card', 300.00, 'paid', '2026-09-29 11:05:00')," +
                    "(3, 3, NULL, 'GCash', 700.00, 'pending', NULL)," +
                    "(4, 4, 'PAY-20261002-001', 'GCash', 500.00, 'paid', '2026-09-29 12:05:00')"
    };

    private final DatabaseConnection databaseConnection = new DatabaseConnection();

    public void seed() {
        try (Connection connection = databaseConnection.connect()) {

            if (!isEmpty(connection)) {
                logger.info("Database already has data, skipping seeding.");
                return;
            }

            connection.setAutoCommit(false);
            try (Statement statement = connection.createStatement()) {
                for (String sql : SEED_STATEMENTS) {
                    statement.executeUpdate(sql);
                }
                connection.commit();
                logger.info("Seeded sample data successfully.");
            } catch (SQLException e) {
                connection.rollback();
                logger.error("Failed to seed sample data.");
                logger.error("Reason: " + e.getMessage());
            }

        } catch (SQLException e) {
            logger.error("Could not check or seed the database.");
            logger.error("Reason: " + e.getMessage());
        }
    }

    private boolean isEmpty(Connection connection) throws SQLException {
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT COUNT(*) FROM users")) {
            return resultSet.next() && resultSet.getInt(1) == 0;
        }
    }
}
