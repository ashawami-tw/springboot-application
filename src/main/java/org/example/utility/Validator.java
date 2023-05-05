package org.example.utility;

public class Validator {
    public static final int PASSWORD_MIN_SIZE = 8;
    public static final int PASSWORD_MAX_SIZE = 15;
    public static final String PASSWORD_SPECIAL_CHARS = "@#$%^&+=*";

    public static final String PASSWORD_REGEXP = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*["+PASSWORD_SPECIAL_CHARS+"])(?=\\S+$).{"+PASSWORD_MIN_SIZE+","+ PASSWORD_MAX_SIZE +"}$";
}
