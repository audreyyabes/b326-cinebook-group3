package com.joysistvi.cinebookapp.service;

import com.joysistvi.cinebookapp.model.Movie;
import com.joysistvi.cinebookapp.repository.MovieRepo;

import java.util.List;

public class MovieServiceImpl implements MovieService {

    private final MovieRepo movieRepo;

    public MovieServiceImpl(MovieRepo movieRepo) {
        this.movieRepo = movieRepo;
    }

    @Override
    public List<Movie> getNowShowingMovies() {
        return movieRepo.getNowShowingMovies();
    }

    @Override
    public Movie getMovieById(int id) {

        if (id <= 0) {
            System.out.println("Invalid Movie ID...");
            return null;
        }

        return movieRepo.getMovieById(id);
    }
}
