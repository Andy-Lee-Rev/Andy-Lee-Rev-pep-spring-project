package com.example.exception;

public class InvalidPosterException extends RuntimeException {
    public InvalidPosterException () {
        super("Poster with this accountId does not exist.");
    }    
}
