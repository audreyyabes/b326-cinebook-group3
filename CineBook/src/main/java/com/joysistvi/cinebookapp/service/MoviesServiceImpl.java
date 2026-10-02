package com.joysistvi.cinebookapp.service;

import com.joysistvi.cinebookapp.model.Movies;
import com.joysistvi.cinebookapp.repository.MoviesRepo;

import java.util.List;

public class MoviesServiceImpl implements MoviesService {

    private final MoviesRepo moviesRepo;

    public MoviesServiceImpl(MoviesRepo moviesRepo) {
        this.moviesRepo = moviesRepo;
    }

    @Override
    public List<Movies> findAll() {
        return moviesRepo.findAll();
    }

    @Override
    public Movies findById(int id) {
        return moviesRepo.findById(id);
    }

    @Override
    public boolean save(Movies movies) {
        return moviesRepo.save(movies);
    }

    @Override
    public boolean update(Movies movies) {
        return moviesRepo.update(movies);
    }

    @Override
    public boolean delete(int id) {
        return moviesRepo.delete(id);
    }
}
