package com.joysistvi.cinebookapp.repository;

import com.joysistvi.cinebookapp.database.DatabaseConnection;
import com.joysistvi.cinebookapp.model.BookingSeats;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BookingSeatsRepoImpl implements BookingSeatsRepo {
    private final DatabaseConnection databaseConnection;

    public BookingSeatsRepoImpl(DatabaseConnection databaseConnection) {
        this.databaseConnection = databaseConnection;
    }

    @Override
    public boolean createBookingSeats(BookingSeats bookingSeats) {
        String query = "INSERT INTO booking_seats (booking_id, seat_id, price) VALUES (?, ?, ?)";
        try (Connection connection = databaseConnection.connect();
             PreparedStatement prep = connection.prepareStatement(query)) {

            prep.setInt(1, bookingSeats.getBookingId());
            prep.setInt(2, bookingSeats.getSeat_id());
            prep.setDouble(3, bookingSeats.getPrice());

            int rows = prep.executeUpdate();
            return rows > 0;

        } catch (SQLException e) {
            System.out.println("Create Booking Seats Error: " + e.getMessage());
        }
        return false;
    }

    @Override
    public List<BookingSeats> getAllBookedSeats() {
        List<BookingSeats> bookingSeats = new ArrayList<>();
        String query = "SELECT * FROM booking_seats";

        try (Connection conn = databaseConnection.connect();
             PreparedStatement prep = conn.prepareStatement(query);
             ResultSet res = prep.executeQuery()) {

            while (res.next()) {
                bookingSeats.add(new BookingSeats(
                        res.getInt( "id"),
                        res.getInt("booking_id"),
                        res.getInt("seat_id"),
                        res.getDouble("price")
                ));
            }
        } catch (Exception e) {
            System.out.println("Get all Booking Seats: " + e.getMessage());
        }
        return bookingSeats;
    }

    @Override
    public boolean updateBookingSeats(BookingSeats bookingSeats) {
        String query = "UPDATE booking_seats SET price = ? WHERE id = ?";
        try (Connection connection = databaseConnection.connect();
             PreparedStatement prep = connection.prepareStatement(query)) {

            prep.setDouble(1, bookingSeats.getPrice());

            int rows = prep.executeUpdate();
            return rows > 0;

        } catch (SQLException e) {
            System.out.println("Update Booking seats Error: " + e.getMessage());
        }
        return false;
    }

    @Override
    public boolean deleteBookingSeats(int id) {
        String query = "DELETE booking_seats WHERE id = ?";
        try (Connection connection = databaseConnection.connect();
             PreparedStatement prep = connection.prepareStatement(query)) {

            prep.setInt(1, id);

            int rows = prep.executeUpdate();
            return rows > 0;

        } catch (SQLException e) {
            System.out.println("Delete Booking seats Error: " + e.getMessage());
        }
        return false;
    }

    @Override
    public BookingSeats readBookingSeatsById(int id) {
        String query = "SELECT * FROM booking_seats WHERE id = ?";
        try (Connection connection = databaseConnection.connect();
             PreparedStatement prep = connection.prepareStatement(query)) {

            prep.setInt(1, id);
            ResultSet res = prep.executeQuery();

            if (res.next()) {
                return new BookingSeats(
                        res.getInt("id"),
                        res.getInt("booking_Id"),
                        res.getInt("seat_Id"),
                        res.getDouble("price")

                );
            }
        } catch (SQLException e) {
            System.out.println("Get all Booking seats by ID: " + e.getMessage());
        }
        return null;
    }

}

