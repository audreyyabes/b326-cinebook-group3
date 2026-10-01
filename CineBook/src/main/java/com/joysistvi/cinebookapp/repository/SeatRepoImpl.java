package com.joysistvi.cinebookapp.repository;

import com.joysistvi.cinebookapp.database.DatabaseConnection;
import com.joysistvi.cinebookapp.model.Seat;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SeatRepoImpl implements SeatRepo {

    private final DatabaseConnection databaseConnection;

    public SeatRepoImpl() {
        this.databaseConnection = new DatabaseConnection();
    }

    @Override
    public List<Seat> findAll() {

        List<Seat> seats = new ArrayList<>();

        String sql = """
                SELECT
                    id,
                    theater_id,
                    seat_code,
                    seat_row,
                    seat_number
                FROM seats
                ORDER BY theater_id, seat_row, seat_number
                """;

        try (
                Connection conn = databaseConnection.connect();
                PreparedStatement stmt = conn.prepareStatement(sql);
                ResultSet rs = stmt.executeQuery()
        ) {

            while (rs.next()) {
                Seat seat = mapResultSet(rs);
                seats.add(seat);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return seats;
    }

    @Override
    public List<Seat> findByTheaterId(int theaterId) {

        List<Seat> seats = new ArrayList<>();

        String sql = """
                SELECT
                    id,
                    theater_id,
                    seat_code,
                    seat_row,
                    seat_number
                FROM seats
                WHERE theater_id = ?
                ORDER BY seat_row, seat_number
                """;

        try (
                Connection conn = databaseConnection.connect();
                PreparedStatement stmt = conn.prepareStatement(sql)
        ) {

            stmt.setInt(1, theaterId);

            try (ResultSet rs = stmt.executeQuery()) {

                while (rs.next()) {
                    Seat seat = mapResultSet(rs);
                    seats.add(seat);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return seats;
    }

    private Seat mapResultSet(ResultSet rs) throws SQLException {

        Seat seat = new Seat();

        seat.setId(rs.getInt("id"));
        seat.setTheaterId(rs.getInt("theater_id"));
        seat.setSeatCode(rs.getString("seat_code"));
        seat.setSeatRow(rs.getString("seat_row"));
        seat.setSeatNumber(rs.getInt("seat_number"));

        return seat;
    }
}