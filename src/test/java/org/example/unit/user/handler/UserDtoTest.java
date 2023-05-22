package org.example.unit.user.handler;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.example.user.handler.UserDto;
import org.example.utility.Response;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;


public class UserDtoTest {
    private static final String VALID_PASSWORD = "Password1@";
    private static final String VALID_EMAIL = "test@gmail.com";

    private Validator validator;

    @BeforeEach
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void testValidUserReq() {
        UserDto userDto = new UserDto(VALID_EMAIL, VALID_PASSWORD);
        Set<ConstraintViolation<UserDto>> constraintViolations = validator.validate(userDto);

        assertEquals(0, constraintViolations.size());
    }

    @Test
    public void testEmptyEmail() {
        UserDto userDto = new UserDto("", VALID_PASSWORD);
        Set<ConstraintViolation<UserDto>> constraintViolations = validator.validate(userDto);

        assertEquals(1, constraintViolations.size());
        assertEquals(Response.INVALID_EMAIL, constraintViolations.iterator().next().getMessage());
    }

    @Test
    public void testEmailWithoutDomain() {
        UserDto userDto = new UserDto("test.com", VALID_PASSWORD);
        Set<ConstraintViolation<UserDto>> constraintViolations = validator.validate(userDto);

        assertEquals(1, constraintViolations.size());
        assertEquals(Response.INVALID_EMAIL, constraintViolations.iterator().next().getMessage());
    }

    @Test
    public void testEmailWithoutExtension() {
        UserDto userDto = new UserDto("test@gmail", VALID_PASSWORD);
        Set<ConstraintViolation<UserDto>> constraintViolations = validator.validate(userDto);

        assertEquals(0, constraintViolations.size());
    }

    @Test
    public void testEmailWithoutUsername() {
        UserDto userDto = new UserDto("@gmail.com", VALID_PASSWORD);
        Set<ConstraintViolation<UserDto>> constraintViolations = validator.validate(userDto);

        assertEquals(1, constraintViolations.size());
        assertEquals(Response.INVALID_EMAIL, constraintViolations.iterator().next().getMessage());
    }

    @Test
    public void testEmptyPassword() {
        UserDto userDto = new UserDto(VALID_EMAIL, "");
        Set<ConstraintViolation<UserDto>> constraintViolations = validator.validate(userDto);

        assertEquals(1, constraintViolations.size());
        assertEquals(Response.INVALID_PASSWORD, constraintViolations.iterator().next().getMessage());
    }

    @Test
    public void testPasswordOfLengthThree() {
        UserDto userDto = new UserDto(VALID_EMAIL, "abc");
        Set<ConstraintViolation<UserDto>> constraintViolations = validator.validate(userDto);

        assertEquals(1, constraintViolations.size());
        assertEquals(Response.INVALID_PASSWORD, constraintViolations.iterator().next().getMessage());
    }

    @Test
    public void testPasswordOfLengthTwenty() {
        UserDto userDto = new UserDto(VALID_EMAIL, "invalid_password_20");
        Set<ConstraintViolation<UserDto>> constraintViolations = validator.validate(userDto);

        assertEquals(1, constraintViolations.size());
        assertEquals(Response.INVALID_PASSWORD, constraintViolations.iterator().next().getMessage());
    }

    @Test
    public void testPasswordWithoutLowerCase() {
        UserDto userDto = new UserDto(VALID_EMAIL, "PASSWORD1@");
        Set<ConstraintViolation<UserDto>> constraintViolations = validator.validate(userDto);

        assertEquals(1, constraintViolations.size());
        assertEquals(Response.INVALID_PASSWORD, constraintViolations.iterator().next().getMessage());
    }

    @Test
    public void testPasswordWithoutUpperCase() {
        UserDto userDto = new UserDto(VALID_EMAIL, "password1@");
        Set<ConstraintViolation<UserDto>> constraintViolations = validator.validate(userDto);

        assertEquals(1, constraintViolations.size());
        assertEquals(Response.INVALID_PASSWORD, constraintViolations.iterator().next().getMessage());
    }

    @Test
    public void testPasswordWithoutDigit() {
        UserDto userDto = new UserDto(VALID_EMAIL, "Password@");
        Set<ConstraintViolation<UserDto>> constraintViolations = validator.validate(userDto);

        assertEquals(1, constraintViolations.size());
        assertEquals(Response.INVALID_PASSWORD, constraintViolations.iterator().next().getMessage());
    }

    @Test
    public void testPasswordWithoutSpecialChar() {
        UserDto userDto = new UserDto(VALID_EMAIL, "Password1");
        Set<ConstraintViolation<UserDto>> constraintViolations = validator.validate(userDto);

        assertEquals(1, constraintViolations.size());
        assertEquals(Response.INVALID_PASSWORD, constraintViolations.iterator().next().getMessage());
    }

    @Test
    public void testPasswordWithSpace() {
        UserDto userDto = new UserDto(VALID_EMAIL, "Password 1");
        Set<ConstraintViolation<UserDto>> constraintViolations = validator.validate(userDto);

        assertEquals(1, constraintViolations.size());
        assertEquals(Response.INVALID_PASSWORD, constraintViolations.iterator().next().getMessage());
    }

    @Test
    public void testPasswordWithNotAllowedSpecialChar() {
        UserDto userDto = new UserDto(VALID_EMAIL, "Password1~");
        Set<ConstraintViolation<UserDto>> constraintViolations = validator.validate(userDto);

        assertEquals(1, constraintViolations.size());
        assertEquals(Response.INVALID_PASSWORD, constraintViolations.iterator().next().getMessage());
    }
}
