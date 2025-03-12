package com.example.exception;

public class InvalidMessageException extends RuntimeException {
    public InvalidMessageException() {
        super("Message is blank or too long.");
    }
}
