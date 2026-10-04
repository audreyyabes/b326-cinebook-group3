package com.joysistvi.cinebookapp.service;

import com.joysistvi.cinebookapp.model.Seat;

import java.util.List;

public interface SeatMapService {

    List<Seat> getSeats(int theaterId);

    boolean addSeat(int theaterId, String seatRow, int seatNumber);

    boolean removeSeat(int seatId);
}