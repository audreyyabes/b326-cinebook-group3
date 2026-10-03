package com.joysistvi.cinebookapp.repository;

import com.joysistvi.cinebookapp.model.Movies;

import java.util.List;

public interface MoviesRepo {

    List<Movies> findAll();
    Movies findById(int id);
    boolean save(Movies movies);
    boolean update(Movies movies);
    boolean delete(int id);
}
