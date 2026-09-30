package com.joysistvi.cinebookapp.repository;

import com.joysistvi.cinebookapp.model.Showtime;

import java.util.List;

public interface ShowtimeRepo {

    List<Showtime> findAll();

    List<Showtime> findByMovieId(int movieId);
}