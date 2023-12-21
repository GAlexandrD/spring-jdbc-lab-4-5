package com.example.lab45.Task.dto;

public class UpdateTaskDto {
    private String taskName;
    private Integer priority;
    private String deadline;
    private Boolean done;

    public UpdateTaskDto(String taskName, String deadline, Integer priority, Boolean done) {
        this.taskName = taskName;
        this.priority = priority;
        this.deadline = deadline;
        this.done = done;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public String getDeadline() {
        return deadline;
    }

    public void setDeadline(String deadline) {
        this.deadline = deadline;
    }

    public Boolean getDone() {
        return done;
    }

    public void setDone(Boolean done) {
        this.done = done;
    }
}
