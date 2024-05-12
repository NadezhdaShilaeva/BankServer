package com.shilaeva.exceptions;

public class WriterException extends RuntimeException {
    private WriterException(String message) {
        super(message);
    }

    public static WriterException errorWritingResponse(String message) {
        return new WriterException(String.format("Error writing response: %s", message));
    }
}
