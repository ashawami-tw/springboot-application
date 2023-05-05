package org.example.user.service;

import org.example.user.repository.User;
import org.example.user.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepo userRepo;

    @Autowired
    public UserService(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    public void create(User user) {
        userRepo.save(user);
    }

    public boolean UserExists(String email) {
        User user = userRepo.findByEmail(email);
        return user != null;
    }
}
