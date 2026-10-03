package com.joysistvi.cinebookapp.repository;

import com.joysistvi.cinebookapp.database.DatabaseConnection;
import com.joysistvi.cinebookapp.model.Movies;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MoviesRepoImpl implements MoviesRepo {

    private final DatabaseConnection databaseConnection = new DatabaseConnection();

    @Override
    public List<Movies> findAll() {

        List<Movies> movies = new ArrayList<>();

        String sql = """
                SELECT id, title, genre, rating, duration
                FROM movies
                """;

        try (Connection connection = databaseConnection.connect();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {

                Movies movie = new Movies(
                        resultSet.getInt("id"),
                        resultSet.getString("title"),
                        resultSet.getString("genre"),
                        resultSet.getString("rating"),
                        resultSet.getInt("duration")
                );

                movies.add(movie);
            }

        } catch (SQLException e) {
            System.err.print("Failed to retrieve movie: " + e);
        }

        return movies;
    }

    @Override
    public Movies findById(int id) {

        String sql = """
                SELECT id, title, genre, rating, duration
                FROM movies
                WHERE id = ?
                """;

        try (Connection connection = databaseConnection.connect();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, id);

            try (ResultSet resultSet = statement.executeQuery()) {

                if (resultSet.next()) {

                    return new Movies(
                            resultSet.getInt("id"),
                            resultSet.getString("title"),
                            resultSet.getString("genre"),
                            resultSet.getString("rating"),
                            resultSet.getInt("duration")
                    );
                }
            }

        } catch (SQLException e) {
            System.err.print("Failed to find movie with id: " + id + " ," + e);
        }

        return null;
    }

    @Override
    public void save(Movies movies) {

        String sql = """
                INSERT INTO movies (title, genre, rating, duration)
                VALUES (?, ?, ?, ?)
                """;

        try (Connection connection = databaseConnection.connect();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, movies.getTitle());
            statement.setString(2, movies.getGenre());
            statement.setString(3, movies.getRating());
            statement.setInt(4, movies.getDuration());

            statement.executeUpdate();

        } catch (SQLException e) {
            System.err.print("Failed to save movie: " + e);
        }
    }

    @Override
    public void update(Movies movies) {

        String sql = """
                UPDATE movies
                SET title = ?, genre = ?, rating = ?, duration = ?
                WHERE id = ?
                """;

        try (Connection connection = databaseConnection.connect();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, movies.getTitle());
            statement.setString(2, movies.getGenre());
            statement.setString(3, movies.getRating());
            statement.setInt(4, movies.getDuration());
            statement.setInt(5, movies.getId());

            statement.executeUpdate();

        } catch (SQLException e) {
            System.err.print("Failed to update movie: " + e);
        }
    }

    @Override
    public void delete(int id) {

        String sql = "DELETE FROM movies WHERE id = ?";

        try (Connection connection = databaseConnection.connect();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, id);

            statement.executeUpdate();

        } catch (SQLException e) {

            System.err.print("Failed to delete movie with id: " + id + " ," + e);
        }
    }
}
