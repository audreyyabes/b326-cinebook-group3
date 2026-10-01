package com.joysistvi.cinebookapp.service;

import com.joysistvi.cinebookapp.model.Booking;
import com.joysistvi.cinebookapp.model.BookingAudit;
import com.joysistvi.cinebookapp.repository.BookingRepo;

import java.util.List;

public class BookingServiceImpl implements BookingService {

    private final BookingRepo bookingRepo;

    public BookingServiceImpl(BookingRepo bookingRepo) {
        this.bookingRepo = bookingRepo;
    }

    @Override
    public List<Booking> getAllBookings() {
        return bookingRepo.getAllBookings();
    }

    @Override
    public Booking getBookingById(int id) {
        return bookingRepo.getBookingById(id);
    }

    @Override
    public List<BookingAudit> getBookingAudit() {
        return bookingRepo.getBookingAudit();
    }

    @Override
    public boolean createBooking(Booking booking) {
        return bookingRepo.createBooking(booking);
    }

    @Override
    public boolean updateBooking(Booking booking) {
        return bookingRepo.updateBooking(booking);
    }

    @Override
    public boolean deleteBooking(int id) {
        return bookingRepo.deleteBooking(id);
    }

    @Override
    public boolean confirmBooking(int id) {
        return bookingRepo.confirmBooking(id);
    }

    @Override
    public double getConfirmedRevenue() {
        return bookingRepo.getConfirmedRevenue();
    }

    @Override
    public double getPendingTotal() {
        return bookingRepo.getPendingTotal();
    }

    @Override
    public int getTotalTicketsReserved() {
        return bookingRepo.getTotalTicketsReserved();
    }
}