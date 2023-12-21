package com.example.lab45.Task.errors;

public class TaskNotFoundException extends RuntimeException {
    public TaskNotFoundException(Long id) {
        super("Task with id (" + id + ") wasn't found");
    }

    public TaskNotFoundException(String name) {
        super("Task with name (" + name + ") wasn't found");
    }
}
