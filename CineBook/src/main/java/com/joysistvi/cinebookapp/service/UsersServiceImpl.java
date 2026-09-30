package com.joysistvi.cinebookapp.service;

import com.joysistvi.cinebookapp.model.Users;
import com.joysistvi.cinebookapp.repository.UsersRepo;
import com.joysistvi.cinebookapp.repository.UsersRepoImpl;
import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

public class UsersServiceImpl implements UsersService {

    private static final Logger logger = LoggerFactory.getLogger(UsersServiceImpl.class);

    private static final int ARGON2_ITERATIONS = 2;
    private static final int ARGON2_MEMORY_KB = 65536;
    private static final int ARGON2_PARALLELISM = 1;

    private final UsersRepo usersRepo;
    private final Argon2 argon2;

    public UsersServiceImpl() {
        this(new UsersRepoImpl());
    }

    public UsersServiceImpl(UsersRepo usersRepo) {
        this.usersRepo = usersRepo;
        this.argon2 = Argon2Factory.create();
    }

    @Override
    public Optional<Users> authenticate(String email, String password) {
        return usersRepo.findByEmail(email)
                .filter(user -> verify(user.getPasswordHash(), password));
    }

    @Override
    public boolean existsByEmail(String email) {
        return usersRepo.existsByEmail(email);
    }

    @Override
    public Users register(String name, String email, String password) {
        if (usersRepo.existsByEmail(email)) {
            throw new EmailAlreadyExistsException(email);
        }
        return createUser(name, email, password, "customer");
    }

    @Override
    public Users createAdmin(String name, String email, String password) {
        return createUser(name, email, password, "admin");
    }

    private Users createUser(String name, String email, String password, String role) {
        char[] passwordChars = password.toCharArray();
        try {
            String passwordHash = argon2.hash(ARGON2_ITERATIONS, ARGON2_MEMORY_KB, ARGON2_PARALLELISM, passwordChars);

            Users user = new Users();
            user.setName(name);
            user.setEmail(email);
            user.setPasswordHash(passwordHash);
            user.setRole(role);

            return usersRepo.create(user);
        } finally {
            argon2.wipeArray(passwordChars);
        }
    }

    private boolean verify(String passwordHash, String password) {
        char[] passwordChars = password.toCharArray();
        try {
            return argon2.verify(passwordHash, passwordChars);
        } catch (RuntimeException e) {
            logger.error("Stored password hash could not be verified with Argon2: {}", e.getMessage());
            return false;
        } finally {
            argon2.wipeArray(passwordChars);
        }
    }
}
