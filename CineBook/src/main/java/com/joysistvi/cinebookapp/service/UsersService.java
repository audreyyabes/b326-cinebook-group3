package com.joysistvi.cinebookapp.service;

import com.joysistvi.cinebookapp.model.Users;

import java.util.Optional;

public interface UsersService {

    Optional<Users> authenticate(String email, String password);

    boolean existsByEmail(String email);

    Users createAdmin(String name, String email, String password);

    boolean updateProfile(Users user, String name, String email);

    boolean changePassword(Users user, String currentPassword, String newPassword);
}
