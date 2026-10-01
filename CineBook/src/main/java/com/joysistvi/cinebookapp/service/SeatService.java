package com.joysistvi.cinebookapp.service;

import com.joysistvi.cinebookapp.model.Seat;

import java.util.List;

public interface SeatService {

    List<Seat> getAllSeats();

    List<Seat> getSeatsByTheaterId(int theaterId);
}