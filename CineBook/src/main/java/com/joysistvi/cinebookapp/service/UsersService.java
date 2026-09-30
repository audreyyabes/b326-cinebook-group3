package com.joysistvi.cinebookapp.service;

import com.joysistvi.cinebookapp.model.Users;

import java.util.Optional;

public interface UsersService {

    Optional<Users> authenticate(String email, String password);

    boolean existsByEmail(String email);

    Users register(String name, String email, String password);

    Users createAdmin(String name, String email, String password);
}
