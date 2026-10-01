package com.joysistvi.cinebookapp.service;

import com.joysistvi.cinebookapp.model.Payment;
import com.joysistvi.cinebookapp.repository.PaymentRepo;
import com.joysistvi.cinebookapp.repository.PaymentRepoImpl;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepo paymentRepo;

    public PaymentServiceImpl() {
        this(new PaymentRepoImpl());
    }

    public PaymentServiceImpl(PaymentRepo paymentRepo) {
        this.paymentRepo = paymentRepo;
    }

    @Override
    public Payment processPayment(int bookingId, BigDecimal amount, String paymentMethod, String paymentReference) {
        boolean paidNow = isPaidImmediately(paymentMethod);

        Payment payment = new Payment();
        payment.setBookingId(bookingId);
        payment.setPaymentReference(paymentReference);
        payment.setPaymentMethod(paymentMethod);
        payment.setAmount(amount);
        payment.setStatus(paidNow ? "paid" : "pending");
        payment.setPaidAt(paidNow ? LocalDateTime.now() : null);

        paymentRepo.create(payment);

        return payment;
    }

    // GCash and Credit Card are treated as paid right away.
    // Cash / Counter stays pending until the customer pays at the counter.
    private boolean isPaidImmediately(String paymentMethod) {
        return "GCash".equalsIgnoreCase(paymentMethod) || "Credit Card".equalsIgnoreCase(paymentMethod);
    }
}
