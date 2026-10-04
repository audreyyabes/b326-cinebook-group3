package com.joysistvi.cinebookapp.repository;

import com.joysistvi.cinebookapp.model.Users;

import java.util.Optional;

public interface UsersRepo {

    Optional<Users> findByEmail(String email);

    boolean existsByEmail(String email);

    Users create(Users user);

    boolean updateProfile(int id, String name, String email);

    boolean updatePassword(int id, String passwordHash);
}
