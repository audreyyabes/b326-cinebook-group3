package com.joysistvi.cinebookapp.model;

public class BookingAudit {

    private int id;
    private String bookingCode;
    private String customerName;
    private double totalAmount;
    private String bookingStatus;
    private String paymentMethod;
    private String paymentStatus;
    private int seatCount;

    public BookingAudit(int id,
                        String bookingCode,
                        String customerName,
                        double totalAmount,
                        String bookingStatus,
                        String paymentMethod,
                        String paymentStatus,
                        int seatCount) {

        this.id = id;
        this.bookingCode = bookingCode;
        this.customerName = customerName;
        this.totalAmount = totalAmount;
        this.bookingStatus = bookingStatus;
        this.paymentMethod = paymentMethod;
        this.paymentStatus = paymentStatus;
        this.seatCount = seatCount;
    }

    public int getId() {
        return id;
    }

    public String getBookingCode() {
        return bookingCode;
    }

    public String getCustomerName() {
        return customerName;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public String getBookingStatus() {
        return bookingStatus;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public int getSeatCount() {
        return seatCount;
    }

    public String getPaymentDetails() {

        if (paymentMethod == null) {
            return paymentStatus;
        }

        return paymentStatus + " (" + paymentMethod + ")";
    }
}
