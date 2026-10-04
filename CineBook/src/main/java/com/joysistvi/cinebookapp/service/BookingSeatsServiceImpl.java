package com.joysistvi.cinebookapp.service;

import com.joysistvi.cinebookapp.model.BookingSeats;
import com.joysistvi.cinebookapp.repository.BookingSeatsRepo;

import java.util.List;

public class BookingSeatsServiceImpl implements BookingSeatsService {

    private final BookingSeatsRepo bookingSeatsRepo;

    public BookingSeatsServiceImpl(BookingSeatsRepo bookingSeatsRepo) {
        this.bookingSeatsRepo = bookingSeatsRepo;
    }

    @Override
    public boolean createBookingSeats(BookingSeats bookingSeats) {
        if (bookingSeats == null) {
            System.out.println("Invalid Booking Seat...");
            return false;
        }
        return bookingSeatsRepo.createBookingSeats(bookingSeats);
    }

    @Override
    public List<BookingSeats> getAllBookedSeats() {
        return bookingSeatsRepo.getAllBookedSeats();
    }

    @Override
    public boolean updateBookingSeats(BookingSeats bookingSeats) {
        return bookingSeatsRepo.updateBookingSeats(bookingSeats);
    }

    @Override
    public boolean deleteBookingSeats(int id) {
        if (id <= 0) {
            System.out.println("Invalid Booking Seat ID...");
            return false;
        }
        return bookingSeatsRepo.deleteBookingSeats(id);
    }

    @Override
    public BookingSeats readBookingSeatsById(int id) {
        if(id <= 0) {
            System.out.println("Invalid ID...");
        }
        return bookingSeatsRepo.readBookingSeatsById(id);
    }
}

