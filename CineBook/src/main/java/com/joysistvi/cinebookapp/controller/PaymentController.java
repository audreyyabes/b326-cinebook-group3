package com.joysistvi.cinebookapp.controller;

import com.joysistvi.cinebookapp.model.Payment;
import com.joysistvi.cinebookapp.service.PaymentService;
import com.joysistvi.cinebookapp.service.PaymentServiceImpl;

import java.math.BigDecimal;

public class PaymentController {

    private final PaymentService paymentService;

    public PaymentController() {
        this(new PaymentServiceImpl());
    }

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    public Payment processPayment(int bookingId, BigDecimal amount, String paymentMethod, String paymentReference) {
        return paymentService.processPayment(bookingId, amount, paymentMethod, paymentReference);
    }
}
