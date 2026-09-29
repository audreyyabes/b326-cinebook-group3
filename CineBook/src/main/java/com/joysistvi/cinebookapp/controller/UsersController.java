package com.joysistvi.cinebookapp.controller;

import com.joysistvi.cinebookapp.model.Users;
import com.joysistvi.cinebookapp.service.UsersService;

import java.util.List;

public class UsersController {
    private final UsersService usersService;

    public UsersController(UsersService usersService) {
        this.usersService = usersService;
    }

    public List<Users> handleViewAllUsers() {
        return usersService.getAllUsers();
    }

    public boolean handleRegister(String username,String email, String password) {
        return usersService.registerUser(username,email, password);
    }

    public Users handleLogin(String username, String password) {
        return usersService.login(username, password);
    }

    public boolean handleDeleteUser(int id) {
        return usersService.deleteUser(id);
    }
}
