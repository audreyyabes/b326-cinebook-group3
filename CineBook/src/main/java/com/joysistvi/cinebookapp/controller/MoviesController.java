package com.joysistvi.cinebookapp.controller;

import com.joysistvi.cinebookapp.model.Movies;
import com.joysistvi.cinebookapp.service.MoviesService;

import java.util.List;

public class MoviesController {

    private final MoviesService moviesService;

    public MoviesController(MoviesService moviesService) {
        this.moviesService = moviesService;
    }

    // Get all movies
    public List<Movies> handleAllMovies() {
        return moviesService.findAll();
    }

    // Get movie by ID
    public Movies handleReadMoviesById(int id) {
        return moviesService.findById(id);
    }

    // Save movie
    public boolean handleSaveMovies(Movies movies) {
        return moviesService.save(movies);
    }

    // Update movie
    public boolean handleUpdateMovies(Movies movies) {
        return moviesService.update(movies);
    }

    // Delete movie
    public boolean handleDeleteMovies(int id) {
        return moviesService.delete(id);
    }
}
