package com.joysistvi.cinebookapp.database;

import com.joysistvi.cinebookapp.service.UsersService;
import com.joysistvi.cinebookapp.service.UsersServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AdminAccountInitializer {

    private static final Logger logger = LoggerFactory.getLogger(AdminAccountInitializer.class);

    private static final String ADMIN_NAME = "Admin User";
    private static final String ADMIN_EMAIL = "admin@cinebook.com";
    private static final String ADMIN_DEFAULT_PASSWORD = "Admin@12345";

    private final UsersService usersService;

    public AdminAccountInitializer() {
        this(new UsersServiceImpl());
    }

    public AdminAccountInitializer(UsersService usersService) {
        this.usersService = usersService;
    }

    public void run() {
        if (usersService.existsByEmail(ADMIN_EMAIL)) {
            logger.info("Admin account already exists ({}), skipping creation.", ADMIN_EMAIL);
            return;
        }

        usersService.createAdmin(ADMIN_NAME, ADMIN_EMAIL, ADMIN_DEFAULT_PASSWORD);
        logger.info("Admin account created: {}", ADMIN_EMAIL);
    }
}
