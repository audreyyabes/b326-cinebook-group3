package com.joysistvi.cinebookapp.database;

import io.github.cdimascio.dotenv.Dotenv;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DatabaseConnection {

    private static final Logger logger = LoggerFactory.getLogger(DatabaseConnection.class);

    private static final Pattern PLACEHOLDER = Pattern.compile("\\$\\{(\\w+)}");

    private static final String URL;
    private static final String USERNAME;
    private static final String PASSWORD;

    static {
        Dotenv dotenv = Dotenv.configure().ignoreIfMissing().load();
        Properties properties = loadProperties();

        URL = resolve(properties.getProperty("db.url"), dotenv);
        USERNAME = resolve(properties.getProperty("db.username"), dotenv);
        PASSWORD = resolve(properties.getProperty("db.password"), dotenv);
    }

    private static Properties loadProperties() {
        Properties properties = new Properties();
        try (InputStream input = DatabaseConnection.class.getClassLoader().getResourceAsStream("application.properties")) {
            if (input == null) {
                throw new IllegalStateException("application.properties not found on classpath");
            }
            properties.load(input);
        } catch (IOException e) {
            throw new IllegalStateException("Failed to load application.properties", e);
        }
        return properties;
    }

    private static String resolve(String value, Dotenv dotenv) {
        if (value == null) {
            return null;
        }
        Matcher matcher = PLACEHOLDER.matcher(value);
        StringBuilder result = new StringBuilder();
        while (matcher.find()) {
            String key = matcher.group(1);
            String replacement = dotenv.get(key, System.getenv(key));
            matcher.appendReplacement(result, Matcher.quoteReplacement(replacement == null ? "" : replacement));
        }
        matcher.appendTail(result);
        return result.toString();
    }

    public Connection connect() throws SQLException {
        return DriverManager.getConnection(URL, USERNAME, PASSWORD);
    }

    public void testConnection() {

        try (Connection connection = connect()) {

            if (connection != null && !connection.isClosed()) {

                logger.info("Database connected successfully.");

            } else {

                logger.error("Database connection failed.");
            }

        } catch (SQLException e) {

            logger.error("Database connection failed.");

            logger.error("Reason: " + e.getMessage());
        }
    }

    public static String getUrl() {
        return URL;
    }

    public static String getUsername() {
        return USERNAME;
    }

    public static String getPassword() {
        return PASSWORD;
    }
}
