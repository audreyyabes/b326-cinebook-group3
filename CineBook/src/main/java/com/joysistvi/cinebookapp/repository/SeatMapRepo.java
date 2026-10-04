package com.joysistvi.cinebookapp.repository;

import com.joysistvi.cinebookapp.model.Seat;

import java.util.List;

public interface SeatMapRepo {

    List<Seat> findByTheaterId(int theaterId);

    boolean addSeat(Seat seat);

    boolean deleteSeat(int seatId);
}