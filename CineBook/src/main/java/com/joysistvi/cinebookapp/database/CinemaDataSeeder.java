package com.joysistvi.cinebookapp.database;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class CinemaDataSeeder {

    private static final Logger logger = LoggerFactory.getLogger(CinemaDataSeeder.class);
    private static final String[][] MOVIES = {
            {"Avengers: Endgame", "Action, Adventure", "PG-13", "181"},
            {"Spider-Man: No Way Home", "Action, Adventure", "PG-13", "148"},
            {"Inside Out 2", "Animation, Comedy", "PG", "96"},
            {"The Batman", "Action, Crime", "PG-13", "176"},
            {"Interstellar", "Science Fiction, Drama", "PG-13", "169"}
    };
    private static final String[][] THEATERS = {
            {"Cinema 1", "CineBook Mall - Quezon City"},
            {"Cinema 2", "CineBook Mall - Quezon City"}
    };
    private static final String[] SEAT_ROWS = {"A", "B", "C"};
    private static final int SEATS_PER_ROW = 5;

    private final DatabaseConnection databaseConnection;

    public CinemaDataSeeder() {
        this(new DatabaseConnection());
    }

    public CinemaDataSeeder(DatabaseConnection databaseConnection) {
        this.databaseConnection = databaseConnection;
    }

    public void run() {
        try (Connection connection = databaseConnection.connect()) {
            connection.setAutoCommit(false);
            seedMovies(connection);
            List<Integer> theaterIds = new ArrayList<>();
            for (String[] theater : THEATERS) {
                int theaterId = ensureTheater(connection, theater[0], theater[1]);
                theaterIds.add(theaterId);
                ensureSeats(connection, theaterId);
            }
            seedShowtimes(connection, theaterIds);
            connection.commit();
            logger.info("Sample movies, theaters, seat maps, and showtimes are ready.");
        } catch (SQLException e) {
            throw new IllegalStateException("Failed to seed sample cinema data", e);
        }
    }

    private void seedMovies(Connection connection) throws SQLException {
        String existsSql = "SELECT 1 FROM movies WHERE title = ? LIMIT 1";
        String insertSql = """
                INSERT INTO movies (title, genre, rating, duration_minutes, status)
                VALUES (?, ?, ?, ?, 'active')
                """;

        for (String[] movie : MOVIES) {
            try (PreparedStatement exists = connection.prepareStatement(existsSql)) {
                exists.setString(1, movie[0]);
                try (ResultSet result = exists.executeQuery()) {
                    if (result.next()) continue;
                }
            }
            try (PreparedStatement insert = connection.prepareStatement(insertSql)) {
                insert.setString(1, movie[0]);
                insert.setString(2, movie[1]);
                insert.setString(3, movie[2]);
                insert.setInt(4, Integer.parseInt(movie[3]));
                insert.executeUpdate();
            }
        }
    }

    private int ensureTheater(Connection connection, String name, String location) throws SQLException {
        String findSql = "SELECT id FROM theaters WHERE name = ? LIMIT 1";
        try (PreparedStatement find = connection.prepareStatement(findSql)) {
            find.setString(1, name);
            try (ResultSet result = find.executeQuery()) {
                if (result.next()) return result.getInt("id");
            }
        }

        String insertSql = "INSERT INTO theaters (name, location, status) VALUES (?, ?, 'active')";
        try (PreparedStatement insert = connection.prepareStatement(insertSql,
                PreparedStatement.RETURN_GENERATED_KEYS)) {
            insert.setString(1, name);
            insert.setString(2, location);
            insert.executeUpdate();
            try (ResultSet keys = insert.getGeneratedKeys()) {
                if (keys.next()) return keys.getInt(1);
            }
        }
        throw new SQLException("Could not retrieve ID for theater " + name);
    }

    private void ensureSeats(Connection connection, int theaterId) throws SQLException {
        String existsSql = "SELECT 1 FROM seats WHERE theater_id = ? AND seat_code = ? LIMIT 1";
        String insertSql = """
                INSERT INTO seats (theater_id, seat_code, seat_row, seat_number)
                VALUES (?, ?, ?, ?)
                """;

        for (String row : SEAT_ROWS) {
            for (int number = 1; number <= SEATS_PER_ROW; number++) {
                String seatCode = row + number;
                boolean exists;
                try (PreparedStatement find = connection.prepareStatement(existsSql)) {
                    find.setInt(1, theaterId);
                    find.setString(2, seatCode);
                    try (ResultSet result = find.executeQuery()) {
                        exists = result.next();
                    }
                }
                if (exists) continue;
                try (PreparedStatement insert = connection.prepareStatement(insertSql)) {
                    insert.setInt(1, theaterId);
                    insert.setString(2, seatCode);
                    insert.setString(3, row);
                    insert.setInt(4, number);
                    insert.executeUpdate();
                }
            }
        }
    }

    private void seedShowtimes(Connection connection, List<Integer> theaterIds) throws SQLException {
        String findMovieSql = "SELECT id, duration_minutes FROM movies WHERE title = ? AND status = 'active' LIMIT 1";
        String findTheaterSql = "SELECT status FROM theaters WHERE id = ?";
        String existsSql = """
                SELECT 1 FROM showtimes
                WHERE movie_id = ? AND theater_id = ? AND status = 'scheduled'
                LIMIT 1
                """;
        String insertSql = """
                INSERT INTO showtimes (movie_id, theater_id, start_time, end_time, ticket_price, status)
                VALUES (?, ?, ?, ?, ?, 'scheduled')
                """;

        for (int movieIndex = 0; movieIndex < MOVIES.length; movieIndex++) {
            int movieId;
            int duration;
            try (PreparedStatement movieQuery = connection.prepareStatement(findMovieSql)) {
                movieQuery.setString(1, MOVIES[movieIndex][0]);
                try (ResultSet movie = movieQuery.executeQuery()) {
                    if (!movie.next()) continue;
                    movieId = movie.getInt("id");
                    duration = movie.getInt("duration_minutes");
                }
            }

            for (int theaterIndex = 0; theaterIndex < theaterIds.size(); theaterIndex++) {
                int theaterId = theaterIds.get(theaterIndex);
                try (PreparedStatement theaterQuery = connection.prepareStatement(findTheaterSql)) {
                    theaterQuery.setInt(1, theaterId);
                    try (ResultSet theater = theaterQuery.executeQuery()) {
                        if (!theater.next() || !"active".equalsIgnoreCase(theater.getString("status"))) continue;
                    }
                }

                try (PreparedStatement exists = connection.prepareStatement(existsSql)) {
                    exists.setInt(1, movieId);
                    exists.setInt(2, theaterId);
                    try (ResultSet result = exists.executeQuery()) {
                        if (result.next()) continue;
                    }
                }

                int dayOffset = 1 + movieIndex / 3;
                int hour = 9 + (movieIndex % 3) * 4 + theaterIndex * 2;
                LocalDateTime start = LocalDate.now().plusDays(dayOffset).atTime(hour, 0);
                LocalDateTime end = start.plusMinutes(duration);
                try (PreparedStatement insert = connection.prepareStatement(insertSql)) {
                    insert.setInt(1, movieId);
                    insert.setInt(2, theaterId);
                    insert.setTimestamp(3, Timestamp.valueOf(start));
                    insert.setTimestamp(4, Timestamp.valueOf(end));
                    insert.setDouble(5, 350.00);
                    insert.executeUpdate();
                }
            }
        }
    }
}
