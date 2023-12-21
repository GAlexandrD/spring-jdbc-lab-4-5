package com.example.lab45.Task.errors;

public class InvalidSortParamException extends RuntimeException {
    public InvalidSortParamException(String sort) {
        super("Cannot sort tasks by " + sort);
    }
}
