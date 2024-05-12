package com.shilaeva.exceptions;

public class AuthException extends RuntimeException {
    private AuthException(String message) {
        super(message);
    }

    public static AuthException userDataIsNotCorrect() {
        return new AuthException("Wrong login or password.");
    }

    public static AuthException tokenIsExpired() {
        return new AuthException("The token is expired.");
    }

    public static AuthException invalidToken() {
        return new AuthException("The token is invalid.");
    }
}
