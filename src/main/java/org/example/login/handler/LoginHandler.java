package org.example.login.handler;

import jakarta.validation.Valid;
import org.example.user.handler.UserDto;
import org.example.user.repository.User;
import org.example.user.service.UserService;
import org.example.utility.response.Message;
import org.example.utility.response.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/login")
public class LoginHandler {
    private final UserService userService;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public LoginHandler(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<Response> login(@Valid @RequestBody UserDto userDto) {
        User user = User.create(userDto);
        Optional<User> savedUser = userService.getUser(user.getEmail());
        if(savedUser.isEmpty()) {
            logger.error("User does not exist");
            return  Response.create(List.of(Message.EMAIL_OR_PASSWORD_IS_INCORRECT), HttpStatus.BAD_REQUEST);
        }

        if(!userService.comparePassword(savedUser.get(), user.getPassword())) {
            logger.error("Password is incorrect");
            return  Response.create(List.of(Message.EMAIL_OR_PASSWORD_IS_INCORRECT), HttpStatus.BAD_REQUEST);
        }

        return Response.create(List.of(Message.LOGIN_SUCCESSFUL), HttpStatus.ACCEPTED);
    }
}
