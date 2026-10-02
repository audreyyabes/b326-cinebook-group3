package com.joysistvi.cinebookapp.service;

import com.joysistvi.cinebookapp.model.Movies;

import java.util.List;

public interface MoviesService {

    List<Movies> findAll();

    Movies findById(int id);

    boolean save(Movies movies);

    boolean update(Movies movies);

    boolean delete(int id);
}
