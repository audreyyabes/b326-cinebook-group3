package com.joysistvi.cinebookapp.model;

import java.time.LocalDateTime;

public class Booking {

    private int id;
    private String bookingCode;
    private int userId;
    private int showtimeId;
    private LocalDateTime bookingDate;
    private double totalAmount;
    private String status;

    public Booking(int id,
                   String bookingCode,
                   int userId,
                   int showtimeId,
                   LocalDateTime bookingDate,
                   double totalAmount,
                   String status) {

        this.id = id;
        this.bookingCode = bookingCode;
        this.userId = userId;
        this.showtimeId = showtimeId;
        this.bookingDate = bookingDate;
        this.totalAmount = totalAmount;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBookingCode() {
        return bookingCode;
    }

    public void setBookingCode(String bookingCode) {
        this.bookingCode = bookingCode;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getShowtimeId() {
        return showtimeId;
    }

    public void setShowtimeId(int showtimeId) {
        this.showtimeId = showtimeId;
    }

    public LocalDateTime getBookingDate() {
        return bookingDate;
    }

    public void setBookingDate(LocalDateTime bookingDate) {
        this.bookingDate = bookingDate;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Booking{" +
                "id=" + id +
                ", bookingCode='" + bookingCode + '\'' +
                ", userId=" + userId +
                ", showtimeId=" + showtimeId +
                ", bookingDate=" + bookingDate +
                ", totalAmount=" + totalAmount +
                ", status='" + status + '\'' +
                '}';
    }
}
