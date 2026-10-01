package com.joysistvi.cinebookapp.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Payment {

    private int id;
    private int bookingId;
    private String paymentReference;
    private String paymentMethod;
    private BigDecimal amount;
    private String status;
    private LocalDateTime paidAt;

    public Payment() {
    }

    public Payment(int id, int bookingId, String paymentReference, String paymentMethod, BigDecimal amount,
                    String status, LocalDateTime paidAt) {
        this.id = id;
        this.bookingId = bookingId;
        this.paymentReference = paymentReference;
        this.paymentMethod = paymentMethod;
        this.amount = amount;
        this.status = status;
        this.paidAt = paidAt;
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

    public String getPaymentReference() {
        return paymentReference;
    }

    public void setPaymentReference(String paymentReference) {
        this.paymentReference = paymentReference;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getPaidAt() {
        return paidAt;
    }

    public void setPaidAt(LocalDateTime paidAt) {
        this.paidAt = paidAt;
    }
}
