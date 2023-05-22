package org.example.unit.user.service;

import org.example.user.repository.User;
import org.example.user.repository.UserRepo;
import org.example.user.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    private static final String EMAIL = "test@gmail.cm";
    private static final String PASSWORD = "test123@T";

    @Mock
    private UserRepo userRepo;

    @Mock
    private PasswordEncoder passwordEncoder;

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

        assertEquals(user.getEmail(), savedUser.getEmail());
        assertEquals(user.getPassword(), savedUser.getPassword());
    }

    @Test
    public void testHashPassword() {
        String hash = "$2a$10$EzbrJCN8wj8M8B5aQiRmiuWqVvnxna73Ccvm38aoneiJb88kkwlH2";
        User user = User.builder().email(EMAIL).password(PASSWORD).build();
        when(passwordEncoder.encode(PASSWORD)).thenReturn(hash);

        userService.hashPassword(user);

        assertEquals(EMAIL, user.getEmail());
        assertEquals(hash, user.getPassword());
    }

    @Test
    public void testUserExists() {
        User user = new User();
        when(userRepo.findByEmail(EMAIL)).thenReturn(user);

        boolean isUserPresent = userService.userExists(EMAIL);

        assertTrue(isUserPresent);
        verify(userRepo).findByEmail(EMAIL);
    }

    @Test
    public void testUserDoesNotExists() {
        when(userRepo.findByEmail(EMAIL)).thenReturn(null);

        boolean isUserPresent = userService.userExists(EMAIL);

        assertFalse(isUserPresent);
        verify(userRepo).findByEmail(EMAIL);
    }
}
