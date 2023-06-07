package org.example.user.service;

import org.example.user.repository.User;
import org.example.user.repository.UserRepo;
import org.example.utility.AppConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;
    private final AppConfig appConfig;

    @Autowired
    public UserService(UserRepo userRepo, PasswordEncoder passwordEncoder, AppConfig appConfig) {
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
        this.appConfig = appConfig;
    }

    public void create(User user) {
        userRepo.save(user);
    }

    public Optional<User> getUser(String email) {
        return Optional.ofNullable(userRepo.findByEmail(email));
    }

    public void hashPassword(User user) {
        String hash = passwordEncoder.encode(user.getPassword());
        user.setPassword(hash);
    }

    public boolean comparePassword(User user, String password) {
        return appConfig.decodePassword(user.getPassword(), password);
    }
}
