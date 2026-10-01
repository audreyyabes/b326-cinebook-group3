package com.joysistvi.cinebookapp.repository;

import com.joysistvi.cinebookapp.model.Showtime;
import com.joysistvi.cinebookapp.model.ShowtimeSchedule;

import java.util.List;

public interface ShowtimeRepo {

    List<ShowtimeSchedule> getShowtimesByMovieId(int movieId);

    Showtime getShowtimeById(int id);
}
