package com.joysistvi.cinebookapp.repository;

import com.joysistvi.cinebookapp.database.DatabaseConnection;
import com.joysistvi.cinebookapp.model.Theater;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TheaterRepoImpl implements TheaterRepo {
    private final DatabaseConnection databaseConnection;

    public TheaterRepoImpl(DatabaseConnection databaseConnection) {
        this.databaseConnection = databaseConnection;
    }

    @Override
    public List<Theater> getAllTheaters() {
        List<Theater> theaters = new ArrayList<>();
        String query = "SELECT * FROM theaters";

        try (Connection conn = databaseConnection.connect();
             PreparedStatement prep = conn.prepareStatement(query);
             ResultSet res = prep.executeQuery()) {

            while (res.next()) {
                theaters.add(new Theater(
                        res.getInt("id"),
                        res.getString("name"),
                        res.getString("location"),
                        res.getString("status")
                ));
            }
        } catch (Exception e) {
            System.out.println("Get all theater: " + e.getMessage());
        }
        return theaters;
    }

    @Override
    public boolean addTheater(String name, String location) {

        String query = "INSERT INTO theaters (name, location, status) VALUES (?,?,'active')";
        try (Connection connection = databaseConnection.connect();
             PreparedStatement prep = connection.prepareStatement(query)) {

            prep.setString(1, name);
            prep.setString(2, location);

            int rows = prep.executeUpdate();
            return rows > 0;

        } catch (SQLException e) {
            System.out.println("Register Theater Error: " + e.getMessage());
        }
        return false;
    }


    @Override
    public boolean updateTheater(Theater theater) {
        String query = "UPDATE theaters SET name = ?, location = ?, status = ? WHERE id = ?";

        try (Connection connection = databaseConnection.connect();
             PreparedStatement prep = connection.prepareStatement(query)) {


            prep.setString(1, theater.getName());
            prep.setString(2, theater.getLocation());
            prep.setString(3, theater.getStatus());
            prep.setInt(4,theater.getId());


            int rows = prep.executeUpdate();
            return rows > 0;

        } catch (SQLException e) {
            System.out.println("Update Theater Error: " + e.getMessage());
        }
        return false;
    }

    @Override
    public boolean deleteTheater(int id) {
        String query= "DELETE FROM theaters WHERE id = ?";

        try (Connection connection = databaseConnection.connect();
             PreparedStatement prep = connection.prepareStatement(query)) {
            prep.setInt(1, id);

            int rowsAffected = prep.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            System.err.println("Delete Theater Error: " + e.getMessage());
        }
        return false;
    }

    @Override
    public Theater readTheaterById(int id) {
        String query = "SELECT id, name, location, status FROM theaters WHERE id = ?";
        try (Connection connection = databaseConnection.connect();
             PreparedStatement prep = connection.prepareStatement(query)) {

            prep.setInt(1, id);
            ResultSet res = prep.executeQuery();

            if (res.next()) {
                return new Theater(
                        res.getInt("id"),
                        res.getString("name"),
                        res.getString("location"),
                        res.getString("status")
                );
            }
        } catch (SQLException e) {
            System.out.println("Get all Theater by ID: " + e.getMessage());
        }
        return null;
    }
}

