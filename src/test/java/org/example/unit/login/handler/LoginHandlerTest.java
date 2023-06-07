package org.example.unit.login.handler;

import org.example.login.handler.LoginHandler;
import org.example.user.handler.UserDto;
import org.example.user.repository.User;
import org.example.user.service.UserService;
import org.example.utility.response.Message;
import org.example.utility.response.Response;
import org.junit.jupiter.api.BeforeEach;
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
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class LoginHandlerTest {
    private static final String EMAIL = "test@gmail.cm";
    private static final String PASSWORD = "test123@T";

    private static UserDto userDto;
    private static User user;

    @Mock
    private UserService userService;

    @InjectMocks
    private LoginHandler loginHandler;

    @BeforeEach
    public void setUp() {
        userDto = new UserDto(EMAIL, PASSWORD);
        user = User.create(userDto);
    }

    @Test
    public void testLoginSuccess() {
        when(userService.getUser(user.getEmail())).thenReturn(Optional.of(user));
        when(userService.comparePassword(user, PASSWORD)).thenReturn(true);

        ResponseEntity<Response> response = loginHandler.login(userDto);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.ACCEPTED);
        assertThat(Objects.requireNonNull(response.getBody()).getMessage().get(0)).isEqualTo(Message.LOGIN_SUCCESSFUL);
    }

    @Test
    public void testPasswordIsIncorrect() {
        when(userService.getUser(user.getEmail())).thenReturn(Optional.of(user));
        when(userService.comparePassword(user, PASSWORD)).thenReturn(false);

        ResponseEntity<Response> response = loginHandler.login(userDto);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(Objects.requireNonNull(response.getBody()).getMessage().get(0)).isEqualTo(Message.EMAIL_OR_PASSWORD_IS_INCORRECT);
    }

    @Test
    public void testEmailDoesNotExist() {
        when(userService.getUser(user.getEmail())).thenReturn(Optional.empty());

        ResponseEntity<Response> response = loginHandler.login(userDto);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(Objects.requireNonNull(response.getBody()).getMessage().get(0)).isEqualTo(Message.EMAIL_OR_PASSWORD_IS_INCORRECT);
    }
}
