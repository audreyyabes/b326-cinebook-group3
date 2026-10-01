package com.joysistvi.cinebookapp.model;

public class BookingSeats {
    int id;
    int bookingId;
    int seat_id;
    double price;


    public BookingSeats(int id, int bookingId, int seat_id, double price) {
        this.id = id;
        this.bookingId = bookingId;
        this.seat_id = seat_id;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getBookingId() {
        return bookingId;
    }

    public void setBookingId(int bookingId) {
        this.bookingId = bookingId;
    }

    public int getSeat_id() {
        return seat_id;
    }

    public void setSeat_id(int seat_id) {
        this.seat_id = seat_id;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
