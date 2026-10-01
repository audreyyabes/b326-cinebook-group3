package com.joysistvi.cinebookapp.model;

import java.util.List;

public class UserBooking {

    private int userId;
    private int showtimeId;
    private List<Integer> seatIds;
    private double totalAmount;
    private String paymentMethod;

    public UserBooking() {
    }

    public UserBooking(int userId, int showtimeId, List<Integer> seatIds,
                       double totalAmount, String paymentMethod) {
        this.userId = userId;
        this.showtimeId = showtimeId;
        this.seatIds = seatIds;
        this.totalAmount = totalAmount;
        this.paymentMethod = paymentMethod;
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

    public List<Integer> getSeatIds() {
        return seatIds;
    }

    public void setSeatIds(List<Integer> seatIds) {
        this.seatIds = seatIds;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }
}