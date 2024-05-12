package com.shilaeva.exceptions;

public class AdapterException extends RuntimeException {
    private AdapterException(String message) {
        super(message);
    }

    public static AdapterException errorParsingRequest(String message) {
        return new AdapterException(String.format("Error parsing request: %s", message));
    }
}
