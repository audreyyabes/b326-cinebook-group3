package com.joysistvi.cinebookapp.repository;

import com.joysistvi.cinebookapp.model.Booking;
import com.joysistvi.cinebookapp.model.BookingAudit;

import java.util.List;

public interface BookingRepo {

    List<Booking> getAllBookings();

    Booking getBookingById(int id);

    List<BookingAudit> getBookingAudit();

    boolean createBooking(Booking booking);

    boolean updateBooking(Booking booking);

    boolean deleteBooking(int id);

    boolean confirmBooking(int id);

    double getConfirmedRevenue();

    double getPendingTotal();

    int getTotalTicketsReserved();
}
