package org.example.unit.user.handler;

import org.example.user.handler.UserDto;
import org.example.user.handler.UserHandler;
import org.example.user.repository.User;
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
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
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
        User user = User.create(userDto);
        when(userService.getUser(EMAIL)).thenReturn(Optional.of(user));

        ResponseEntity<Response> response = userHandler.create(userDto);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(Objects.requireNonNull(response.getBody()).getMessage().get(0)).isEqualTo(Message.EMAIL_ALREADY_EXISTS);
    }

    @Test
    public void testCreateUser() {
        UserDto userDto = new UserDto(EMAIL, PASSWORD);
        when(userService.getUser(EMAIL)).thenReturn(Optional.empty());

        ResponseEntity<Response> response = userHandler.create(userDto);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(Objects.requireNonNull(response.getBody()).getMessage().get(0)).isEqualTo(Message.USER_CREATED);
    }

}
