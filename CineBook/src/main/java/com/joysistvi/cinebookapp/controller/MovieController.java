package com.joysistvi.cinebookapp.controller;

import com.joysistvi.cinebookapp.model.Movie;
import com.joysistvi.cinebookapp.service.MovieService;

import java.util.List;

public class MovieController {

    private final MovieService movieService;

    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    public List<Movie> handleViewNowShowingMovies() {
        return movieService.getNowShowingMovies();
    }

    public Movie handleGetMovieById(int id) {
        return movieService.getMovieById(id);
    }
}
