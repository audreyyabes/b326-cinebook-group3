package com.joysistvi.cinebookapp.service;

import com.joysistvi.cinebookapp.model.Users;
import com.joysistvi.cinebookapp.repository.UsersRepo;
import com.joysistvi.cinebookapp.repository.UsersRepoImpl;
import org.mindrot.jbcrypt.BCrypt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

public class UsersServiceImpl implements UsersService {

    private static final Logger logger = LoggerFactory.getLogger(UsersServiceImpl.class);

    private final UsersRepo usersRepo;

    public UsersServiceImpl() {
        this(new UsersRepoImpl());
    }

    public UsersServiceImpl(UsersRepo usersRepo) {
        this.usersRepo = usersRepo;
    }

    @Override
    public Optional<Users> authenticate(String email, String password) {
        return usersRepo.findByEmail(email)
                .filter(user -> verify(password, user.getPasswordHash()));
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
        String passwordHash = BCrypt.hashpw(password, BCrypt.gensalt());

        Users user = new Users();
        user.setName(name);
        user.setEmail(email);
        user.setPasswordHash(passwordHash);
        user.setRole(role);

        return usersRepo.create(user);
    }

    private boolean verify(String password, String passwordHash) {
        try {
            return BCrypt.checkpw(password, passwordHash);
        } catch (IllegalArgumentException e) {
            logger.error("Stored password hash could not be verified with bcrypt: {}", e.getMessage());
            return false;
        }
    }
}
