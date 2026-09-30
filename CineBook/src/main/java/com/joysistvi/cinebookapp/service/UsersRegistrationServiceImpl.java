package com.joysistvi.cinebookapp.service;

import com.joysistvi.cinebookapp.model.UsersRegistration;
import com.joysistvi.cinebookapp.repository.UsersRegistrationRepo;

import java.util.List;

public class UsersRegistrationServiceImpl implements UsersRegistrationService {
    private final UsersRegistrationRepo usersRegistrationRepo;

    public UsersRegistrationServiceImpl(UsersRegistrationRepo usersRegistrationRepo) {
        this.usersRegistrationRepo = usersRegistrationRepo;
    }

    @Override
    public List<UsersRegistration> getAllUsers() {
        return usersRegistrationRepo.getAllUsers();
    }

    @Override
    public boolean registerUser(String name, String email, String password_hash, String role) {
        return false;
    }

    @Override
    public UsersRegistration login(String email, String password_hash) {
        return null;
    }

    @Override
    public boolean deleteUser(int id) {
        return false;
    }

    @Override
    public boolean registerUser(String name, String email, String password) {
        return false;
    }
}
