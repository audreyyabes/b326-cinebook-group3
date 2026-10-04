package com.joysistvi.cinebookapp.controller;

import com.joysistvi.cinebookapp.model.BookingSeats;
import com.joysistvi.cinebookapp.service.BookingSeatsService;

import java.util.List;

public class BookingSeatsController {

    private final BookingSeatsService bookingSeatsService;

    public BookingSeatsController(BookingSeatsService bookingSeatsService) {
        this.bookingSeatsService = bookingSeatsService;
    }
    public boolean handleCreateBookingSeats(BookingSeats bookingSeats) {
        return bookingSeatsService.createBookingSeats(bookingSeats);
    }

    public List<BookingSeats> handleAllBookingSeats(){
        return bookingSeatsService.getAllBookedSeats();
    }
    public boolean handleUpdateBookingSeats(BookingSeats bookingSeats ) {
        return bookingSeatsService.updateBookingSeats(bookingSeats);
    }

    public boolean handleBookingSeats(int id) {
        return bookingSeatsService.deleteBookingSeats(id);
    }

    public BookingSeats handleReadBookingSeatsById(int id) {
        return bookingSeatsService.readBookingSeatsById(id);
    }

}
