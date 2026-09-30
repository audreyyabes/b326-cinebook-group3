package com.joysistvi.cinebookapp.database;

import org.flywaydb.core.Flyway;
import org.flywaydb.core.api.MigrationInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DatabaseMigration {

    private static final Logger logger = LoggerFactory.getLogger(DatabaseMigration.class);

    public void migrate() {
        Flyway flyway = Flyway.configure()
                .dataSource(DatabaseConnection.getUrl(), DatabaseConnection.getUsername(), DatabaseConnection.getPassword())
                .locations("classpath:db/migration")
                .baselineOnMigrate(true)
                .load();

        MigrationInfo[] pending = flyway.info().pending();
        if (pending.length == 0) {
            logger.info("Tables are already up to date, skipping migration.");
            return;
        }

        flyway.migrate();
        logger.info("Applied {} migration(s).", pending.length);
    }
}
