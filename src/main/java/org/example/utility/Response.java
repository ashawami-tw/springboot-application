package org.example.utility;

public class Response {
    public static final String INVALID_EMAIL = "Please provide valid email";
    public static final String INVALID_PASSWORD = "Please provide password of min length and max length of " + Validator.PASSWORD_MIN_SIZE + " and " + Validator.PASSWORD_MAX_SIZE + " respectively and password should contain at least 1 uppercase, 1 lowercase, 1 digit, 1 special character from " + Validator.PASSWORD_SPECIAL_CHARS;
    public static final String EMAIL_ALREADY_EXISTS = "User or Password is incorrect";

    public static final String USER_CREATED = "User created successfully";
}
