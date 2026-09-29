package com.joysistvi.cinebookapp.cliview;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class UsersView {
    private static final String URL = "JDBC:mysql://localhost:3306/cinebook_db";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "";

    public Connection connect() throws SQLException {
        return DriverManager.getConnection(URL,USERNAME, PASSWORD);
    }
}
