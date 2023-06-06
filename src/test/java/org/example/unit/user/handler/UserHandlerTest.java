package org.example.unit.user.handler;

import org.example.user.handler.UserDto;
import org.example.user.handler.UserHandler;
import org.example.user.service.UserService;
import org.example.utility.response.Message;
import org.example.utility.response.Response;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserHandlerTest {
    private static final String EMAIL = "test@gmail.cm";
    private static final String PASSWORD = "test123@T";

    @Mock
    private UserService userService;

    @InjectMocks
    private UserHandler userHandler;

    @Test
    public void testUserAlreadyExists() {
        UserDto userDto = new UserDto(EMAIL, PASSWORD);
        when(userService.userExists(EMAIL)).thenReturn(true);

        ResponseEntity<Response> response = userHandler.create(userDto);

        assertEquals(response.getStatusCode(), HttpStatus.BAD_REQUEST);
        assertEquals(Message.EMAIL_ALREADY_EXISTS, Objects.requireNonNull(response.getBody()).getMessage().get(0));
    }

    @Test
    public void testCreateUser() {
        UserDto userDto = new UserDto(EMAIL, PASSWORD);
        when(userService.userExists(EMAIL)).thenReturn(false);

        ResponseEntity<Response> response = userHandler.create(userDto);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(Message.USER_CREATED, Objects.requireNonNull(response.getBody()).getMessage().get(0));
    }

}
