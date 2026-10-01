package com.joysistvi.cinebookapp.controller;


import com.joysistvi.cinebookapp.model.Theater;
import com.joysistvi.cinebookapp.service.TheaterService;

import java.util.List;

public class TheaterController {
    private final TheaterService theaterService;

    public TheaterController(TheaterService theaterService) {
        this.theaterService = theaterService;
    }

    public enum status{
        ACTIVE,
        INACTIVE
    }

    public List<Theater> handleViewAllTheater(){
        return theaterService.getAllTheaters();
    }

    public boolean handleAddTheater(String name, String location){
        return theaterService.addTheater(name, location);
    }
    public boolean handleUpdateTheater(Theater theater) {
        return theaterService.updateTheater(theater);
    }

    public boolean handleDeleteUser(int id) {
        return theaterService.deleteTheater(id);
    }

    public Theater handleReadTheaterById(int id) {
        return theaterService.readTheaterById(id);
    }


}
