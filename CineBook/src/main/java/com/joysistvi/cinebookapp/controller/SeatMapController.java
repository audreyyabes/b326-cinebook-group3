package com.joysistvi.cinebookapp.controller;

import com.joysistvi.cinebookapp.model.Seat;
import com.joysistvi.cinebookapp.service.SeatMapService;

import java.util.List;

public class SeatMapController {

    private final SeatMapService seatMapService;

    public SeatMapController(SeatMapService seatMapService) {
        this.seatMapService = seatMapService;
    }

    public List<Seat> getSeats(int theaterId) {
        return seatMapService.getSeats(theaterId);
    }

    public boolean addSeat(int theaterId, String seatRow, int seatNumber) {
        return seatMapService.addSeat(theaterId, seatRow, seatNumber);
    }

    public boolean removeSeat(int seatId) {
        return seatMapService.removeSeat(seatId);
    }
}