package com.joysistvi.cinebookapp.repository;

import com.joysistvi.cinebookapp.database.DatabaseConnection;
import com.joysistvi.cinebookapp.model.Showtime;
import com.joysistvi.cinebookapp.model.ShowtimeSchedule;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ShowtimeRepoImpl implements ShowtimeRepo {

    private final DatabaseConnection databaseConnection;

    public ShowtimeRepoImpl() {
        databaseConnection = new DatabaseConnection();
    }

    @Override
    public List<ShowtimeSchedule> getShowtimesByMovieId(int movieId) {

        List<ShowtimeSchedule> schedules = new ArrayList<>();

        String sql = """
                SELECT s.id,
                       t.name AS theater_name,
                       s.start_time,
                       s.end_time,
                       s.ticket_price
                FROM showtimes s
                JOIN theaters t ON t.id = s.theater_id
                WHERE s.movie_id = ?
                  AND s.status = 'scheduled'
                ORDER BY s.start_time
                """;

        try (Connection connection = databaseConnection.connect();
                PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, movieId);

            try (ResultSet resultSet = statement.executeQuery()) {

                while (resultSet.next()) {

                    ShowtimeSchedule schedule = new ShowtimeSchedule(
                            resultSet.getInt("id"),
                            resultSet.getString("theater_name"),
                            resultSet.getTimestamp("start_time").toLocalDateTime(),
                            resultSet.getTimestamp("end_time").toLocalDateTime(),
                            resultSet.getDouble("ticket_price"));

                    schedules.add(schedule);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return schedules;
    }

    @Override
    public Showtime getShowtimeById(int id) {

        String sql = """
                SELECT id,
                       movie_id,
                       theater_id,
                       start_time,
                       end_time,
                       ticket_price,
                       status
                FROM showtimes
                WHERE id = ?
                """;

        try (Connection connection = databaseConnection.connect();
                PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, id);

            try (ResultSet resultSet = statement.executeQuery()) {

                if (resultSet.next()) {
                    return new Showtime(
                            resultSet.getInt("id"),
                            resultSet.getInt("movie_id"),
                            resultSet.getInt("theater_id"),
                            resultSet.getTimestamp("start_time").toLocalDateTime(),
                            resultSet.getTimestamp("end_time").toLocalDateTime(),
                            resultSet.getDouble("ticket_price"),
                            resultSet.getString("status"));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public boolean create(Showtime showtime) {
        String sql = """
                INSERT INTO showtimes (movie_id, theater_id, start_time, end_time, ticket_price, status)
                VALUES (?, ?, ?, ?, ?, ?)
                """;

        try (Connection connection = databaseConnection.connect();
                PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, showtime.getMovieId());
            statement.setInt(2, showtime.getTheaterId());
            statement.setTimestamp(3, java.sql.Timestamp.valueOf(showtime.getStartTime()));
            statement.setTimestamp(4, java.sql.Timestamp.valueOf(showtime.getEndTime()));
            statement.setDouble(5, showtime.getTicketPrice());
            statement.setString(6, showtime.getStatus());
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
