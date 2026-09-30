package com.joysistvi.cinebookapp.repository;

import com.joysistvi.cinebookapp.config.DBConnection;
import com.joysistvi.cinebookapp.model.UsersRegistration;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class UsersRegistrationRepoImpl implements UsersRegistrationRepo {
    private final DBConnection dbConnection;

    public UsersRegistrationRepoImpl(DBConnection dbConnection){
        this.dbConnection = dbConnection;
    }

    @Override
    public List<UsersRegistration> getAllUsers() {
        List<UsersRegistration> users = new ArrayList<>();
        String query = "SELECT * FROM users";

        try (Connection conn = dbConnection.connect();
             PreparedStatement prep = conn.prepareStatement(query);
             ResultSet res = prep.executeQuery()){

            while (res.next()){
                users.add(new UsersRegistration(
                        res.getInt("id"),
                        res.getString("name"),
                        res.getString("email"),
                        res.getString("password_hash"),
                        res.getString("role")
                ));
            }
        } catch (Exception e) {
            System.out.println("Get all user: " + e.getMessage());
        }
        return users;
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
}
