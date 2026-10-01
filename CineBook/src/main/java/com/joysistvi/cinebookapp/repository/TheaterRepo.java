package com.joysistvi.cinebookapp.repository;

import com.joysistvi.cinebookapp.model.Theater;

import java.util.List;

public interface TheaterRepo {

    List<Theater> getAllTheaters();
    boolean addTheater(String name, String location);
    boolean updateTheater(Theater theater);
    boolean deleteTheater(int id);
    Theater readTheaterById(int id);

}
