package com.joysistvi.cinebookapp.service;

import com.joysistvi.cinebookapp.model.Users;
import com.joysistvi.cinebookapp.repository.UsersRepo;

import java.util.List;

public class UsersServiceImpl implements UsersService{
    private final UsersRepo usersRepo;

    public UsersServiceImpl(UsersRepo usersRepo) {
        this.usersRepo = usersRepo;
    }

    @Override
    public List<Users> getAllUsers() {
        return usersRepo.getAllUsers();
    }

    @Override
    public boolean registerUser(String name, String email, String password_hash, String role) {
        return false;
    }

    @Override
    public Users login(String email, String password_hash) {
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
