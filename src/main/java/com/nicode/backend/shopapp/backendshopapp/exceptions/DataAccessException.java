package com.nicode.backend.shopapp.backendshopapp.exceptions;

public class DataAccessException extends RuntimeException{

    public DataAccessException(String message) {
        super(message);
    }

    public DataAccessException(String message, Throwable cause) {
        super(message, cause);
    }
}