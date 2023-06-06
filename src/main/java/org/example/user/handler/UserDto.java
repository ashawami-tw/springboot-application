package org.example.user.handler;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.utility.response.Message;
import org.example.utility.response.Validator;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    @NotBlank(message = Message.INVALID_EMAIL)
    @Email(message = Message.INVALID_EMAIL)
    private String email;

    @Pattern(regexp = Validator.PASSWORD_REGEXP, message = Message.INVALID_PASSWORD)
    private String password;
}
