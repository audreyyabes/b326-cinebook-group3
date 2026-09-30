package com.joysistvi.cinebookapp.repository;

import com.joysistvi.cinebookapp.config.DBConnection;
import com.joysistvi.cinebookapp.model.Showtime;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ShowtimeRepoImpl extends DBConnection implements ShowtimeRepo {

    @Override
    public List<Showtime> findAll() {

        List<Showtime> showtimes = new ArrayList<>();

        String sql = """
                SELECT
                    s.id,
                    s.movie_id,
                    s.theater_id,
                    s.start_time,
                    s.end_time,
                    s.ticket_price,
                    s.status,
                    m.title AS movie_title,
                    m.genre,
                    m.rating,
                    m.duration_minutes,
                    t.name AS theater_name
                FROM showtimes s
                INNER JOIN movies m ON s.movie_id = m.id
                INNER JOIN theaters t ON s.theater_id = t.id
                WHERE s.status = 'scheduled'
                AND m.status = 'active'
                AND t.status = 'active'
                ORDER BY s.start_time
                """;

        try (
                Connection conn = connect();
                PreparedStatement stmt = conn.prepareStatement(sql);
                ResultSet rs = stmt.executeQuery()
        ) {

            while (rs.next()) {

                Showtime showtime = new Showtime();

                showtime.setId(rs.getInt("id"));
                showtime.setMovieId(rs.getInt("movie_id"));
                showtime.setTheaterId(rs.getInt("theater_id"));

                showtime.setStartTime(
                        rs.getTimestamp("start_time").toLocalDateTime()
                );

                showtime.setEndTime(
                        rs.getTimestamp("end_time").toLocalDateTime()
                );

                showtime.setTicketPrice(
                        rs.getDouble("ticket_price")
                );

                showtime.setStatus(
                        rs.getString("status")
                );

                showtime.setMovieTitle(
                        rs.getString("movie_title")
                );

                showtime.setGenre(
                        rs.getString("genre")
                );

                showtime.setRating(
                        rs.getString("rating")
                );

                showtime.setDurationMinutes(
                        rs.getInt("duration_minutes")
                );

                showtime.setTheaterName(
                        rs.getString("theater_name")
                );

                showtimes.add(showtime);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return showtimes;
    }

    @Override
    public List<Showtime> findByMovieId(int movieId) {

        List<Showtime> showtimes = new ArrayList<>();

        String sql = """
                SELECT
                    s.id,
                    s.movie_id,
                    s.theater_id,
                    s.start_time,
                    s.end_time,
                    s.ticket_price,
                    s.status,
                    m.title AS movie_title,
                    m.genre,
                    m.rating,
                    m.duration_minutes,
                    t.name AS theater_name
                FROM showtimes s
                INNER JOIN movies m ON s.movie_id = m.id
                INNER JOIN theaters t ON s.theater_id = t.id
                WHERE s.movie_id = ?
                AND s.status = 'scheduled'
                AND m.status = 'active'
                AND t.status = 'active'
                ORDER BY s.start_time
                """;

        try (
                Connection conn = connect();
                PreparedStatement stmt = conn.prepareStatement(sql)
        ) {

            stmt.setInt(1, movieId);

            try (ResultSet rs = stmt.executeQuery()) {

                while (rs.next()) {

                    Showtime showtime = new Showtime();

                    showtime.setId(rs.getInt("id"));
                    showtime.setMovieId(rs.getInt("movie_id"));
                    showtime.setTheaterId(rs.getInt("theater_id"));

                    showtime.setStartTime(
                            rs.getTimestamp("start_time").toLocalDateTime()
                    );

                    showtime.setEndTime(
                            rs.getTimestamp("end_time").toLocalDateTime()
                    );

                    showtime.setTicketPrice(
                            rs.getDouble("ticket_price")
                    );

                    showtime.setStatus(
                            rs.getString("status")
                    );

                    showtime.setMovieTitle(
                            rs.getString("movie_title")
                    );

                    showtime.setGenre(
                            rs.getString("genre")
                    );

                    showtime.setRating(
                            rs.getString("rating")
                    );

                    showtime.setDurationMinutes(
                            rs.getInt("duration_minutes")
                    );

                    showtime.setTheaterName(
                            rs.getString("theater_name")
                    );

                    showtimes.add(showtime);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return showtimes;
    }
}