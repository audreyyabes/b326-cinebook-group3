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
    public boolean deleteUser(int id) {
        return id > 0 && usersRegistrationRepo.deleteUser(id);
    }


    @Override
    public UsersRegistration login(String email, String password_hash) {
        return null;
    }

    @Override
    public boolean registerUser(String name, String email, String password_hash) {
        return registerUser(name, email, password_hash, "customer");
    }

    @Override
    public boolean registerUser(String name, String email, String password_hash, String role) {
        if (name == null || name.isBlank() || email == null || email.isBlank()
                || password_hash == null || password_hash.length() < 8
                || role == null || !(role.equalsIgnoreCase("customer") || role.equalsIgnoreCase("admin"))) {
            return false;
        }
        return usersRegistrationRepo.registerUser(name.trim(), email.trim(), password_hash, role.toLowerCase());
    }
}
