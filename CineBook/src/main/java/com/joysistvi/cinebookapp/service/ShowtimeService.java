package com.joysistvi.cinebookapp.service;

import com.joysistvi.cinebookapp.model.Showtime;

import java.util.List;

public interface ShowtimeService {

    List<Showtime> getAllShowtimes();

    List<Showtime> getShowtimesByMovieId(int movieId);
}