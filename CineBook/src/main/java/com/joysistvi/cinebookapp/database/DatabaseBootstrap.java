package com.joysistvi.cinebookapp.database;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseBootstrap {

    private static final Logger logger = LoggerFactory.getLogger(DatabaseBootstrap.class);

    public void createDatabaseIfNotExists() {
        String url = DatabaseConnection.getUrl();
        int schemeEnd = url.indexOf("://") + 3;
        int pathStart = url.indexOf('/', schemeEnd);
        if (pathStart == -1) {
            return;
        }

        String serverUrl = url.substring(0, pathStart) + "/";
        String dbName = url.substring(pathStart + 1).split("\\?")[0];

        try (Connection connection = DriverManager.getConnection(serverUrl, DatabaseConnection.getUsername(), DatabaseConnection.getPassword());
             Statement statement = connection.createStatement()) {

            statement.executeUpdate(
                    "CREATE DATABASE IF NOT EXISTS `" + dbName + "` CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci");
            logger.info("Database '{}' is ready.", dbName);

        } catch (SQLException e) {
            logger.error("Failed to ensure database '{}' exists.", dbName);
            logger.error("Reason: " + e.getMessage());
            throw new IllegalStateException("Failed to ensure database exists", e);
        }
    }
}
