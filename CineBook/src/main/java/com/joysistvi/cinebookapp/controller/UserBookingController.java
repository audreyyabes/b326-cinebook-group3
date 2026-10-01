package com.joysistvi.cinebookapp.controller;

import com.joysistvi.cinebookapp.service.UserBookingService;

import java.util.List;

public class UserBookingController {

    private final UserBookingService userBookingService;

    public UserBookingController(UserBookingService userBookingService) {
        this.userBookingService = userBookingService;
    }

    public boolean isCustomer(int userId) {
        return userBookingService.isCustomer(userId);
    }

    public List<String> getAvailableShowtimes() {
        return userBookingService.getAvailableShowtimes();
    }

    public List<String> getSeatsForShowtime(int showtimeId) {
        return userBookingService.getSeatsForShowtime(showtimeId);
    }

    public boolean createBooking(
            int userId,
            int showtimeId,
            List<Integer> seatIds,
            double totalAmount,
            String paymentMethod) {

        return userBookingService.createBooking(
                userId,
                showtimeId,
                seatIds,
                totalAmount,
                paymentMethod
        );
    }

    public List<String> getMyBookings(int userId) {
        return userBookingService.getMyBookings(userId);
    }
}
