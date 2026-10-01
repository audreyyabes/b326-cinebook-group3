package com.joysistvi.cinebookapp.controller;

import com.joysistvi.cinebookapp.model.Booking;
import com.joysistvi.cinebookapp.model.BookingAudit;
import com.joysistvi.cinebookapp.service.BookingService;

import java.util.List;

public class BookingController {

    private final BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    public List<BookingAudit> getBookingAudit() {
        return bookingService.getBookingAudit();
    }

    public boolean confirmBooking(int id) {
        return bookingService.confirmBooking(id);
    }

    public double getConfirmedRevenue() {
        return bookingService.getConfirmedRevenue();
    }

    public double getPendingTotal() {
        return bookingService.getPendingTotal();
    }

    public int getTotalTicketsReserved() {
        return bookingService.getTotalTicketsReserved();
    }

    public List<Booking> getAllBookings() {
        return bookingService.getAllBookings();
    }

    public Booking getBookingById(int id) {
        return bookingService.getBookingById(id);
    }

    public boolean createBooking(Booking booking) {
        return bookingService.createBooking(booking);
    }

    public boolean updateBooking(Booking booking) {
        return bookingService.updateBooking(booking);
    }

    public boolean deleteBooking(int id) {
        return bookingService.deleteBooking(id);
    }
}
