package org.example.user.repository;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import org.example.user.handler.UserDto;

import java.util.UUID;

@Getter
@Entity(name = "user")
@Table(name="user", schema="public")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter
public class User {
    private static final int PASSWORD_MIN_SIZE = 8;
    private static final int PASSWORD_MAX_SIZE = 15;
    private static final String PASSWORD_SPECIAL_CHARS = "@#$%^&+=*";

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "email")
    @Email(message = "Please provide valid email")
    private String email;

    @Column(name = "password")
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*["+PASSWORD_SPECIAL_CHARS+"])(?=\\S+$).{"+PASSWORD_MIN_SIZE+","+ PASSWORD_MAX_SIZE +"}$", message = "Please provide password of min length and max length of " + PASSWORD_MIN_SIZE + " and " + PASSWORD_MAX_SIZE + " respectively and password should contain at least 1 uppercase, 1 lowercase, 1 digit, 1 special character from " + PASSWORD_SPECIAL_CHARS)
    private String password;

    public static User create(UserDto user) {
        return User.builder().email(user.getEmail()).password(user.getPassword()).build();
    }
}
