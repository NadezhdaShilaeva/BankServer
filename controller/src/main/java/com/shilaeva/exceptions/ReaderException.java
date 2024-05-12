package com.shilaeva.exceptions;

public class ReaderException extends RuntimeException {
    private ReaderException(String message) {
        super(message);
    }

    public static ReaderException errorReadingResponse(String message) {
        return new ReaderException(String.format("Error reading request: %s", message));
    }
}
