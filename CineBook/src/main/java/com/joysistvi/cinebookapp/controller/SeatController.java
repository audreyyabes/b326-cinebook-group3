package com.joysistvi.cinebookapp.controller;

import com.joysistvi.cinebookapp.model.Seat;
import com.joysistvi.cinebookapp.service.SeatService;

import java.util.List;

public class SeatController {

    private final SeatService seatService;

    public SeatController(SeatService seatService) {
        this.seatService = seatService;
    }

    public List<Seat> getAllSeats() {
        return seatService.getAllSeats();
    }

    public List<Seat> getSeatsByTheaterId(int theaterId) {
        return seatService.getSeatsByTheaterId(theaterId);
    }
}