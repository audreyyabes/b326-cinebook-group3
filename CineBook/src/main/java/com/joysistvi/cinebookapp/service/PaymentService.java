package com.joysistvi.cinebookapp.service;

import com.joysistvi.cinebookapp.model.Payment;

import java.math.BigDecimal;

public interface PaymentService {

    // Processes a payment for an existing booking and saves it.
    // GCash / Credit Card are marked "paid" right away; Cash / Counter stays "pending".
    Payment processPayment(int bookingId, BigDecimal amount, String paymentMethod, String paymentReference);
}
