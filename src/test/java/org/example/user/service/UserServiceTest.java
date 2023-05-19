package org.example.user.service;

import org.example.user.repository.User;
import org.example.user.repository.UserRepo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    private static final String EMAIL = "test@gmail.cm";

    private static final String PASSWORD = "test123@T";
    @Mock
    private UserRepo userRepo;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    @Test
    public void testSaveLead() {
        User user = User.builder().email(EMAIL).password(PASSWORD).build();

        userService.create(user);

        verify(userRepo, times(1)).save(user);
    }

    @Test
    public void testUserExists() {
        User user = new User();
        when(userRepo.findByEmail(EMAIL)).thenReturn(user);

        boolean isUserPresent = userService.UserExists(EMAIL);

        assertTrue(isUserPresent);
    }

    @Test
    public void testUserDoesNotExists() {
        when(userRepo.findByEmail(EMAIL)).thenReturn(null);

        boolean isUserPresent = userService.UserExists(EMAIL);

        assertFalse(isUserPresent);
    }

}
