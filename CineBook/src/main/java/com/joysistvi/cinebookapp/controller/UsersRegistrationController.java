package com.joysistvi.cinebookapp.controller;

import com.joysistvi.cinebookapp.model.UsersRegistration;
import com.joysistvi.cinebookapp.service.UsersRegistrationService;

import java.util.List;

public class UsersRegistrationController {
    private final UsersRegistrationService usersRegistrationService;

    public UsersRegistrationController(UsersRegistrationService usersRegistrationService) {
        this.usersRegistrationService = usersRegistrationService;
    }
    public enum Role {
        CUSTOMER,
        ADMIN
    }

    public List<UsersRegistration> handleViewAllUsers() {
        return usersRegistrationService.getAllUsers();
    }

    public boolean handleRegister(String name,String email, String password_hash) {
        return usersRegistrationService.registerUser(name,email, password_hash);
    }

    public UsersRegistration handleLogin(String username, String password) {
        return usersRegistrationService.login(username, password);
    }

    public boolean handleDeleteUser(int id) {
        return usersRegistrationService.deleteUser(id);
    }
}
