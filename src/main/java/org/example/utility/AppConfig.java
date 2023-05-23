package org.example.utility;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.net.PasswordAuthentication;

@Configuration
public class AppConfig {
    private final BCryptPasswordEncoder BCRYPT = new BCryptPasswordEncoder();
    @Bean
    public PasswordEncoder passwordEncoder() {
        return BCRYPT;
    }

    public Boolean decodePassword(String savedPassword, String password) {
        return BCRYPT.matches(password, savedPassword);
    }
}
