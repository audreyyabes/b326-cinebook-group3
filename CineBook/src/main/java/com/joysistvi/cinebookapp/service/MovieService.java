package com.joysistvi.cinebookapp.service;

import com.joysistvi.cinebookapp.model.Movie;

import java.util.List;

public interface MovieService {

    List<Movie> getNowShowingMovies();

    Movie getMovieById(int id);
}
