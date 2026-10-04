package com.joysistvi.cinebookapp.service;

import com.joysistvi.cinebookapp.model.BookingSeats;

import java.util.List;

public interface BookingSeatsService {

    boolean createBookingSeats(BookingSeats bookingSeats);

    List<BookingSeats> getAllBookedSeats();

    boolean updateBookingSeats(BookingSeats bookingSeats);

    boolean deleteBookingSeats(int id);

    BookingSeats readBookingSeatsById(int id);
}
