package com.example.lab45.Task.errors;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "Such name has already been taken")
public class DataBaseException extends RuntimeException {
    public DataBaseException(String message) {
        super("Database error: " + message);
    }
}