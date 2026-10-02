package com.joysistvi.cinebookapp.repository;


import com.joysistvi.cinebookapp.model.BookingSeats;

import java.util.List;

public interface BookingSeatsRepo {
    List<BookingSeats> getAllBookedSeats();

    boolean updateBookingSeats(BookingSeats bookingSeats);

    boolean deleteBookingSeats(int id);

    BookingSeats readBookingSeatsById(int id);
}
