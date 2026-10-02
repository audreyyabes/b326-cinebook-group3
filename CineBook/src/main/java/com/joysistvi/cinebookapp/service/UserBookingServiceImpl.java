package com.joysistvi.cinebookapp.service;

import com.joysistvi.cinebookapp.repository.UserBookingRepo;
import com.joysistvi.cinebookapp.repository.UserBookingRepoImpl;

import java.util.List;

public class UserBookingServiceImpl implements UserBookingService {

    private final UserBookingRepo userBookingRepo;

    public UserBookingServiceImpl() {
        userBookingRepo = new UserBookingRepoImpl();
    }

    @Override
    public boolean isCustomer(int userId) {
        return userBookingRepo.isCustomer(userId);
    }

    @Override
    public List<String> getAvailableShowtimes() {
        return userBookingRepo.getAvailableShowtimes();
    }

    @Override
    public List<String> getSeatsForShowtime(int showtimeId) {
        return userBookingRepo.getSeatsForShowtime(showtimeId);
    }

    @Override
    public boolean createBooking(
            int userId,
            int showtimeId,
            List<Integer> seatIds,
            double totalAmount,
            String paymentMethod
    ) {

        if (userId <= 0) {
            return false;
        }

        if (showtimeId <= 0) {
            return false;
        }

        if (seatIds == null || seatIds.isEmpty()) {
            return false;
        }

        if (totalAmount <= 0) {
            return false;
        }

        if (paymentMethod == null ||
                paymentMethod.trim().isEmpty()) {
            return false;
        }

        if (!isCustomer(userId)) {
            return false;
        }

        return userBookingRepo.createBooking(
                userId,
                showtimeId,
                seatIds,
                totalAmount,
                paymentMethod
        );
    }

    @Override
    public List<String> getMyBookings(int userId) {

        if (userId <= 0) {
            return List.of();
        }

        return userBookingRepo.getMyBookings(userId);
    }
}
