package org.example.user.repository;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import org.example.user.handler.UserDto;

import java.util.UUID;

import static org.example.utility.response.Message.INVALID_EMAIL;

@Getter
@Entity(name = "user")
@Table(name="user", schema="public")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter
public class User {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "email")
    @Email(message = INVALID_EMAIL)
    private String email;

    @Column(name = "password")
    @NotBlank()
    private String password;

    public static User create(UserDto user) {
        return User.builder().email(user.getEmail()).password(user.getPassword()).build();
    }
}
