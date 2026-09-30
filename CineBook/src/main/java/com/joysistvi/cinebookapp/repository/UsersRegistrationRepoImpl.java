package com.joysistvi.cinebookapp.repository;

import com.joysistvi.cinebookapp.database.DatabaseConnection;
import com.joysistvi.cinebookapp.model.UsersRegistration;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UsersRegistrationRepoImpl implements UsersRegistrationRepo {
    private final DatabaseConnection databaseConnection;

    public UsersRegistrationRepoImpl(DatabaseConnection dbConnection){
        this.databaseConnection = dbConnection;
    }

    @Override
    public List<UsersRegistration> getAllUsers() {
        List<UsersRegistration> users = new ArrayList<>();
        String query = "SELECT * FROM users";

        try (Connection conn = databaseConnection.connect();
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
    public boolean registerUser(String name, String email, String password_hash) {
        String query = "INSERT INTO users (name, email, password_hash) VALUES (?, ?, ?)";

        String hashedPassword = BCrypt.hashpw(password_hash, BCrypt.gensalt());

        try (Connection connection = databaseConnection.connect();
             PreparedStatement prep = connection.prepareStatement(query)) {

            prep.setString(1, name);
            prep.setString(2, email);
            prep.setString(3, hashedPassword);

            int rows = prep.executeUpdate();
            return rows > 0;

        } catch (SQLException e) {
            System.out.println("Register User Error: " + e.getMessage());
        }
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
