package com.joysistvi.cinebookapp.controller;

import com.joysistvi.cinebookapp.model.Showtime;
import com.joysistvi.cinebookapp.model.ShowtimeSchedule;
import com.joysistvi.cinebookapp.service.ShowtimeService;

import java.util.List;

public class ShowtimeController {

    private final ShowtimeService showtimeService;

    public ShowtimeController(ShowtimeService showtimeService) {
        this.showtimeService = showtimeService;
    }

    public List<ShowtimeSchedule> handleViewShowtimesByMovieId(int movieId) {
        return showtimeService.getShowtimesByMovieId(movieId);
    }

    public Showtime handleGetShowtimeById(int id) {
        return showtimeService.getShowtimeById(id);
    }

    public boolean handleCreateShowtime(Showtime showtime) {
        return showtimeService.create(showtime);
    }
}
