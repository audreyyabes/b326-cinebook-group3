package com.joysistvi.cinebookapp.service;

import com.joysistvi.cinebookapp.model.UsersRegistration;

import java.util.List;

public interface UsersRegistrationService {
    List<UsersRegistration> getAllUsers();

    boolean registerUser(String name, String email, String password_hash, String role);

    UsersRegistration login(String email, String password_hash);

    boolean deleteUser(int id);

    boolean registerUser(String name,String email, String password);
}
