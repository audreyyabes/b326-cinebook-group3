package com.joysistvi.cinebookapp.service;

import java.util.List;

public interface UserBookingService {

    boolean isCustomer(int userId);

    List<String> getAvailableShowtimes();
    List<String> getSeatsForShowtime(int showtimeId);
    boolean createBooking(int userId, int showtimeId, List<Integer> seatIds, double totalAmount, String paymentMethod);
    List<String> getMyBookings(int userId);

}
