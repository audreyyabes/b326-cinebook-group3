package com.joysistvi.cinebookapp;

import com.joysistvi.cinebookapp.database.DatabaseBootstrap;
import com.joysistvi.cinebookapp.database.DatabaseConnection;
import com.joysistvi.cinebookapp.database.DatabaseMigration;

public class App {

    public static void main(String[] args) {

        DatabaseBootstrap bootstrap = new DatabaseBootstrap(); // Create database upon running the application
        DatabaseMigration migration = new DatabaseMigration(); // Create tables upon running the application
        DatabaseConnection databaseConnection = new DatabaseConnection();

        bootstrap.createDatabaseIfNotExists();
        migration.migrate();
        databaseConnection.testConnection();
    }
}
