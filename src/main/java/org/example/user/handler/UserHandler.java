package org.example.user.handler;

import jakarta.validation.Valid;
import org.example.user.repository.User;
import org.example.user.service.UserService;
import org.example.utility.response.Message;
import org.example.utility.response.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserHandler {
    private final UserService userService;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public UserHandler(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<Response> create(@Valid @RequestBody UserDto userDto) {
        User user = User.create(userDto);
        if(userService.getUser(user.getEmail()).isPresent()) {
            logger.error("User already exists");
            return Response.create(List.of(Message.EMAIL_ALREADY_EXISTS), HttpStatus.BAD_REQUEST);
        }
        userService.hashPassword(user);
        userService.create(user);
        return Response.create(List.of(Message.USER_CREATED), HttpStatus.CREATED);
    }
}
