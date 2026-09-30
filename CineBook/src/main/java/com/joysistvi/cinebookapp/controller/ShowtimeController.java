package com.joysistvi.cinebookapp.controller;

import com.joysistvi.cinebookapp.model.Showtime;
import com.joysistvi.cinebookapp.service.ShowtimeService;

import java.util.List;

public class ShowtimeController {

    private final ShowtimeService showtimeService;

    public ShowtimeController(ShowtimeService showtimeService) {
        this.showtimeService = showtimeService;
    }

    public List<Showtime> getAllShowtimes() {
        return showtimeService.getAllShowtimes();
    }

    public List<Showtime> getShowtimesByMovieId(int movieId) {
        return showtimeService.getShowtimesByMovieId(movieId);
    }
}