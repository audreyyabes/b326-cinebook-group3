package com.joysistvi.cinebookapp.repository;

import com.joysistvi.cinebookapp.database.DatabaseConnection;
import com.joysistvi.cinebookapp.model.Movie;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class MovieRepoImpl implements MovieRepo {

    private final DatabaseConnection databaseConnection;

    public MovieRepoImpl() {
        databaseConnection = new DatabaseConnection();
    }

    @Override
    public List<Movie> getNowShowingMovies() {

        List<Movie> movies = new ArrayList<>();

        String sql = """
                SELECT id,
                       title,
                       description,
                       duration_minutes,
                       genre,
                       release_date,
                       rating,
                       status
                FROM movies
                WHERE status = 'active'
                ORDER BY id
                """;

        try (Connection connection = databaseConnection.connect();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                movies.add(mapRow(resultSet));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return movies;
    }

    @Override
    public Movie getMovieById(int id) {

        String sql = """
                SELECT id,
                       title,
                       description,
                       duration_minutes,
                       genre,
                       release_date,
                       rating,
                       status
                FROM movies
                WHERE id = ?
                """;

        try (Connection connection = databaseConnection.connect();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, id);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return mapRow(resultSet);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    private Movie mapRow(ResultSet resultSet) throws SQLException {

        Date releaseDateColumn = resultSet.getDate("release_date");
        LocalDate releaseDate = releaseDateColumn != null ? releaseDateColumn.toLocalDate() : null;

        return new Movie(
                resultSet.getInt("id"),
                resultSet.getString("title"),
                resultSet.getString("description"),
                resultSet.getInt("duration_minutes"),
                resultSet.getString("genre"),
                releaseDate,
                resultSet.getString("rating"),
                resultSet.getString("status")
        );
    }
}
