package com.joysistvi.cinebookapp.repository;

import com.joysistvi.cinebookapp.model.Seat;

import java.util.List;

public interface SeatRepo {

    List<Seat> findAll();

    List<Seat> findByTheaterId(int theaterId);
}