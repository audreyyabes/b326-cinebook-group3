package com.joysistvi.cinebookapp.service;

import com.joysistvi.cinebookapp.model.Showtime;
import com.joysistvi.cinebookapp.model.ShowtimeSchedule;

import java.util.List;

public interface ShowtimeService {

    List<ShowtimeSchedule> getShowtimesByMovieId(int movieId);

    Showtime getShowtimeById(int id);

    boolean create(Showtime showtime);
}
