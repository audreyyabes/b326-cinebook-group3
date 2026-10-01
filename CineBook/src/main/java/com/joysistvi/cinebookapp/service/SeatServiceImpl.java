package com.joysistvi.cinebookapp.service;

import com.joysistvi.cinebookapp.model.Seat;
import com.joysistvi.cinebookapp.repository.SeatRepo;

import java.util.List;

public class SeatServiceImpl implements SeatService {

    private final SeatRepo seatRepo;

    public SeatServiceImpl(SeatRepo seatRepo) {
        this.seatRepo = seatRepo;
    }

    @Override
    public List<Seat> getAllSeats() {
        return seatRepo.findAll();
    }

    @Override
    public List<Seat> getSeatsByTheaterId(int theaterId) {
        return seatRepo.findByTheaterId(theaterId);
    }
}