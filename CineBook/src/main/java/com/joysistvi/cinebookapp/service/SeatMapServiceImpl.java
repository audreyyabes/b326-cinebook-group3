package com.joysistvi.cinebookapp.service;

import com.joysistvi.cinebookapp.model.Seat;
import com.joysistvi.cinebookapp.repository.SeatMapRepo;

import java.util.List;

public class SeatMapServiceImpl implements SeatMapService {

    private final SeatMapRepo seatMapRepo;

    public SeatMapServiceImpl(SeatMapRepo seatMapRepo) {
        this.seatMapRepo = seatMapRepo;
    }

    @Override
    public List<Seat> getSeats(int theaterId) {
        if (theaterId <= 0) {
            return List.of();
        }
        return seatMapRepo.findByTheaterId(theaterId);
    }

    @Override
    public boolean addSeat(int theaterId, String seatRow, int seatNumber) {
        if (theaterId <= 0 || seatRow == null || !seatRow.matches("[A-Z]{1,2}") || seatNumber <= 0) {
            return false;
        }
        String code = seatRow + seatNumber;
        boolean exists = seatMapRepo.findByTheaterId(theaterId).stream()
                .anyMatch(seat -> seat.getSeatCode().equalsIgnoreCase(code));
        return !exists && seatMapRepo.addSeat(new Seat(0, theaterId, code, seatRow, seatNumber));
    }

    @Override
    public boolean removeSeat(int seatId) {
        return seatId > 0 && seatMapRepo.deleteSeat(seatId);
    }
}