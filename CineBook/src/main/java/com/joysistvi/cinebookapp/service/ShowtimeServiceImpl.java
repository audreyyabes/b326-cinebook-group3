package com.joysistvi.cinebookapp.service;

import com.joysistvi.cinebookapp.model.Showtime;
import com.joysistvi.cinebookapp.repository.ShowtimeRepo;

import java.util.List;

public class ShowtimeServiceImpl implements ShowtimeService {

    private final ShowtimeRepo showtimeRepo;

    public ShowtimeServiceImpl(ShowtimeRepo showtimeRepo) {
        this.showtimeRepo = showtimeRepo;
    }

    @Override
    public List<Showtime> getAllShowtimes() {
        return showtimeRepo.findAll();
    }

    @Override
    public List<Showtime> getShowtimesByMovieId(int movieId) {
        return showtimeRepo.findByMovieId(movieId);
    }
}