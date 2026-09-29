package com.joysistvi.cinebookapp.repository;

import com.joysistvi.cinebookapp.model.Users;

import java.util.List;

public interface UsersRepo {
    List<Users> getAllUsers();

    boolean registerUser(String name, String email, String password_hash, String role);

    Users login(String email, String password_hash);

    boolean deleteUser(int id);
}
