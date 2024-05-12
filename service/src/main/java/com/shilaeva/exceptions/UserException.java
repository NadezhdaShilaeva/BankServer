package com.shilaeva.exceptions;

public class UserException extends RuntimeException {
    private UserException(String message) {
        super(message);
    }

    public static UserException userAlreadyExists(String login) {
        return new UserException(String.format("User with login %s already exists", login));
    }
}
