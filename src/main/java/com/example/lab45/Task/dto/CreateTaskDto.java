package com.example.lab45.Task.dto;

public class CreateTaskDto {
    private String taskName;
    private Integer priority;
    private String deadline;

    public CreateTaskDto(String taskName, String deadline, Integer priority) {
        this.taskName = taskName;
        this.priority = priority;
        this.deadline = deadline;
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
}
