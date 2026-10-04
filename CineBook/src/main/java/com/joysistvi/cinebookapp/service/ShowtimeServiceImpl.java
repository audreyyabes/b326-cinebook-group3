package com.joysistvi.cinebookapp.service;

import com.joysistvi.cinebookapp.model.Showtime;
import com.joysistvi.cinebookapp.model.ShowtimeSchedule;
import com.joysistvi.cinebookapp.repository.ShowtimeRepo;

import java.util.List;

public class ShowtimeServiceImpl implements ShowtimeService {

    private final ShowtimeRepo showtimeRepo;

    public ShowtimeServiceImpl(ShowtimeRepo showtimeRepo) {
        this.showtimeRepo = showtimeRepo;
    }

    @Override
    public List<ShowtimeSchedule> getShowtimesByMovieId(int movieId) {

        if (movieId <= 0) {
            System.out.println("Invalid Movie ID...");
            return List.of();
        }

        return showtimeRepo.getShowtimesByMovieId(movieId);
    }

    @Override
    public Showtime getShowtimeById(int id) {

        if (id <= 0) {
            System.out.println("Invalid Showtime ID...");
            return null;
        }

        return showtimeRepo.getShowtimeById(id);
    }

    @Override
    public boolean create(Showtime showtime) {
        if (showtime == null || showtime.getMovieId() <= 0 || showtime.getTheaterId() <= 0
                || showtime.getStartTime() == null || showtime.getEndTime() == null
                || !showtime.getEndTime().isAfter(showtime.getStartTime())
                || showtime.getTicketPrice() <= 0) {
            return false;
        }
        return showtimeRepo.create(showtime);
    }
}
