package org.example.user.handler;

import jakarta.validation.Valid;
import org.example.user.repository.User;
import org.example.user.service.UserService;
import org.example.utility.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserHandler {
    private final UserService userService;

    @Autowired
    public UserHandler(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<String> create(@Valid @RequestBody UserDto userDto) {
        User user = User.create(userDto);
        if(userService.userExists(user.getEmail())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Response.EMAIL_ALREADY_EXISTS);
        }
        userService.hashPassword(user);
        userService.create(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(Response.USER_CREATED);
    }
}
