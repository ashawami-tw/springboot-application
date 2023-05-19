package org.example.user.service;

import org.example.user.repository.User;
import org.example.user.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepo userRepo, PasswordEncoder passwordEncoder) {
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
    }

    public void create(User user) {
        userRepo.save(user);
    }

    public boolean userExists(String email) {
        Optional<User> user = Optional.ofNullable(userRepo.findByEmail(email));
        return user.isPresent();
    }

    public void hashPassword(User user) {
        String hash = passwordEncoder.encode(user.getPassword());
        user.setPassword(hash);
    }
}
