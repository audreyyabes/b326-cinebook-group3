package com.joysistvi.cinebookapp.repository;

import com.joysistvi.cinebookapp.model.Movie;

import java.util.List;

public interface MovieRepo {

    List<Movie> getNowShowingMovies();

    Movie getMovieById(int id);
}
