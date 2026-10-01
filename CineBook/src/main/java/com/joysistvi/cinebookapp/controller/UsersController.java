package com.joysistvi.cinebookapp.controller;

import com.joysistvi.cinebookapp.model.Users;
import com.joysistvi.cinebookapp.service.UsersService;
import com.joysistvi.cinebookapp.service.UsersServiceImpl;

import java.util.Optional;

public class UsersController {

    private final UsersService usersService;

    public UsersController() {
        this(new UsersServiceImpl());
    }

    public UsersController(UsersService usersService) {
        this.usersService = usersService;
    }

    public Optional<Users> login(String email, String password) {
        return usersService.authenticate(email, password);
    }
}
