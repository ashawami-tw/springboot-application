package org.example.unit.user.service;

import org.example.user.repository.User;
import org.example.user.repository.UserRepo;
import org.example.user.service.UserService;
import org.example.utility.AppConfig;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    private static final String EMAIL = "test@gmail.cm";
    private static final String PASSWORD = "test123@T";

    private static final String HASH_PASSWORD = "$2a$10$EzbrJCN8wj8M8B5aQiRmiuWqVvnxna73Ccvm38aoneiJb88kkwlH2";

    @Mock
    private UserRepo userRepo;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private AppConfig appConfig;

    @Captor
    private ArgumentCaptor<User> userArgumentCaptor = ArgumentCaptor.forClass(User.class);

    @InjectMocks
    private UserService userService;

    @Test
    public void testSaveLead() {
        User user = User.builder().email(EMAIL).password(PASSWORD).build();

        userService.create(user);

        verify(userRepo).save(userArgumentCaptor.capture());
        User savedUser = userArgumentCaptor.getValue();

        assertThat(savedUser.getEmail()).isEqualTo(user.getEmail());
        assertThat(savedUser.getPassword()).isEqualTo(user.getPassword());
    }

    @Test
    public void testHashPassword() {
        User user = User.builder().email(EMAIL).password(PASSWORD).build();
        when(passwordEncoder.encode(PASSWORD)).thenReturn(HASH_PASSWORD);

        userService.hashPassword(user);

        assertThat(user.getEmail()).isEqualTo(EMAIL);
        assertThat(user.getPassword()).isEqualTo(HASH_PASSWORD);
    }

    @Test
    public void testUserExists() {
        User user = User.builder().email(EMAIL).password(PASSWORD).build();
        when(userRepo.findByEmail(EMAIL)).thenReturn(user);

        Optional<User> savedUser = userService.getUser(user.getEmail());

        assertThat(savedUser.isPresent()).isTrue();
        assertThat(savedUser.get().getEmail()).isEqualTo(user.getEmail());
        verify(userRepo).findByEmail(EMAIL);
    }

    @Test
    public void testUserDoesNotExists() {
        when(userRepo.findByEmail(EMAIL)).thenReturn(null);

        Optional<User> savedUser = userService.getUser(EMAIL);

        assertThat(savedUser.isPresent()).isFalse();
        verify(userRepo).findByEmail(EMAIL);
    }

    @Test
    public void testLoginPasswordIsCorrect() {
        User user = User.builder().email(EMAIL).password(HASH_PASSWORD).build();
        when(appConfig.decodePassword(HASH_PASSWORD, PASSWORD)).thenReturn(true);

        boolean isCorrect = userService.comparePassword(user, PASSWORD);

        assertThat(isCorrect).isTrue();
    }

    @Test
    public void testLoginPasswordIsInCorrect() {
        User user = User.builder().email(EMAIL).password(HASH_PASSWORD).build();
        when(appConfig.decodePassword(HASH_PASSWORD, PASSWORD)).thenReturn(false);

        boolean isCorrect = userService.comparePassword(user, PASSWORD);

        assertThat(isCorrect).isFalse();
    }
}
