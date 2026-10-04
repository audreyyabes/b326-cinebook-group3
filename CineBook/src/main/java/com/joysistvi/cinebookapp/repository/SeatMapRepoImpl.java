package com.joysistvi.cinebookapp.repository;

import com.joysistvi.cinebookapp.database.DatabaseConnection;
import com.joysistvi.cinebookapp.model.Seat;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SeatMapRepoImpl implements SeatMapRepo {

    private final DatabaseConnection databaseConnection;

    public SeatMapRepoImpl(DatabaseConnection databaseConnection) {
        this.databaseConnection = databaseConnection;
    }

    @Override
    public List<Seat> findByTheaterId(int theaterId) {
        List<Seat> seats = new ArrayList<>();
        String sql = """
                SELECT id, theater_id, seat_code, seat_row, seat_number
                FROM seats
                WHERE theater_id = ?
                ORDER BY seat_row, seat_number
                """;

        try (Connection connection = databaseConnection.connect();
                PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, theaterId);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    seats.add(new Seat(resultSet.getInt("id"), resultSet.getInt("theater_id"),
                            resultSet.getString("seat_code"), resultSet.getString("seat_row"),
                            resultSet.getInt("seat_number")));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to load theater seat map", e);
        }
        return seats;
    }

    @Override
    public boolean addSeat(Seat seat) {
        String sql = "INSERT INTO seats (theater_id, seat_code, seat_row, seat_number) VALUES (?, ?, ?, ?)";
        try (Connection connection = databaseConnection.connect();
                PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, seat.getTheaterId());
            statement.setString(2, seat.getSeatCode());
            statement.setString(3, seat.getSeatRow());
            statement.setInt(4, seat.getSeatNumber());
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Failed to add seat to theater map", e);
        }
    }

    @Override
    public boolean deleteSeat(int seatId) {
        String sql = "DELETE FROM seats WHERE id = ?";
        try (Connection connection = databaseConnection.connect();
                PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, seatId);
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Seat could not be removed; it may be referenced by a booking", e);
        }
    }
}