package com.joysistvi.cinebookapp;

import com.joysistvi.cinebookapp.database.DatabaseBootstrap;
import com.joysistvi.cinebookapp.database.DatabaseConnection;
import com.joysistvi.cinebookapp.database.DatabaseMigration;
import com.joysistvi.cinebookapp.database.DatabaseSeeder;

public class App {

    public static void main(String[] args) {

        DatabaseBootstrap bootstrap = new DatabaseBootstrap(); // Create database upon running the application
        DatabaseMigration migration = new DatabaseMigration(); // Create tables upon running the application
        DatabaseConnection databaseConnection = new DatabaseConnection();
        DatabaseSeeder seeder = new DatabaseSeeder(); // Populate sample data if the database is empty

        bootstrap.createDatabaseIfNotExists();
        migration.migrate();
        databaseConnection.testConnection();
        seeder.seed();
    }
}
