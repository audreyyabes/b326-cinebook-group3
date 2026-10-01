
package com.joysistvi.cinebookapp.service;

import com.joysistvi.cinebookapp.model.Theater;
import com.joysistvi.cinebookapp.repository.TheaterRepo;

import java.util.List;

public class TheaterServiceImpl implements TheaterService{
    private final TheaterRepo theaterRepo;

    public TheaterServiceImpl( TheaterRepo theaterRepo) {
        this.theaterRepo = theaterRepo;
    }

    @Override
    public List<Theater> getAllTheaters() {
        return theaterRepo.getAllTheaters();
    }

    @Override
    public boolean addTheater(String name, String location) {
        return theaterRepo.addTheater(name, location);
    }

    @Override
    public boolean updateTheater(Theater theater) {
        return theaterRepo.updateTheater(theater);
    }

    @Override
    public boolean deleteTheater(int id) {

        if (id <= 0) {
            System.out.println("Invalid Theater ID...");
            return false;
        }
        return theaterRepo.deleteTheater(id);
    }

    @Override
    public Theater readTheaterById(int id) {
        if(id <= 0){
            System.out.println("Invalid ID...");
            return null;
        }
        Theater theater = theaterRepo.readTheaterById(id);
        if (theater == null){
            System.out.println("Theater not found..");
        }
        return theater;
    }
}

