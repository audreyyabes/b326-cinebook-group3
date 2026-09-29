package com.joysistvi.cinebookapp.service;

import com.joysistvi.cinebookapp.model.Users;

import java.util.List;

public interface UsersService {
    List<Users> getAllUsers();

    boolean registerUser(String name, String email, String password_hash, String role);

    Users login(String email, String password_hash);

    boolean deleteUser(int id);

    boolean registerUser(String name,String email, String password);
}
