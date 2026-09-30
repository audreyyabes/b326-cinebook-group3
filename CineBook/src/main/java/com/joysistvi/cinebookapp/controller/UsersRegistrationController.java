package com.joysistvi.cinebookapp.controller;

import com.joysistvi.cinebookapp.model.UsersRegistration;
import com.joysistvi.cinebookapp.service.UsersRegistrationService;

import java.util.List;

public class UsersRegistrationController {
    private final UsersRegistrationService usersRegistrationService;

    public UsersRegistrationController(UsersRegistrationService usersRegistrationService) {
        this.usersRegistrationService = usersRegistrationService;
    }

    public List<UsersRegistration> handleViewAllUsers() {
        return usersRegistrationService.getAllUsers();
    }

    public boolean handleRegister(String username,String email, String password) {
        return usersRegistrationService.registerUser(username,email, password);
    }

    public UsersRegistration handleLogin(String username, String password) {
        return usersRegistrationService.login(username, password);
    }

    public boolean handleDeleteUser(int id) {
        return usersRegistrationService.deleteUser(id);
    }
}
