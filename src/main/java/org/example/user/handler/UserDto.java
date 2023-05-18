package org.example.user.handler;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.utility.Response;
import org.example.utility.Validator;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    @NotBlank(message = Response.INVALID_EMAIL)
    @Email(message = Response.INVALID_EMAIL)
    private String email;

    @Pattern(regexp = Validator.PASSWORD_REGEXP, message = Response.INVALID_PASSWORD)
    private String password;
}
