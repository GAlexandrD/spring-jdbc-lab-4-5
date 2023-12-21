package com.example.lab45.Task.errors;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "Wrong deadline format")
public class DateParseException extends RuntimeException {
    public  DateParseException(String input) {
        super("Failed parsing '" + input + "' to date, use format yyyy-MM-dd");
    }
}