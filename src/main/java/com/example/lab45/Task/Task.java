package com.example.lab45.Task;

import jakarta.persistence.*;

import java.util.Date;

public class Task {
    private Long id;
    private String name;
    private Date deadline;
    private Integer priority;
    private  Boolean done;

    public Boolean getDone() {
        return done;
    }

    public void setDone(Boolean isDone) {
        done = isDone;
    }

    public Long getId() {
        return id;
    }

    public Task() {}

    public Task(Long id, String name, Date deadline, Integer priority, Boolean done) {
        this.id = id;
        this.name = name;
        this.deadline = deadline;
        this.priority = priority;
        this.done = done;
    }

    public Task(String name, Date deadline, Integer priority, Boolean done) {
        this.name = name;
        this.deadline = deadline;
        this.priority = priority;
        this.done = done;
    }

    public String getName() {
        return name;
    }

    public void setName(String taskName) {
        this.name = taskName;
    }

    public Date getDeadline() { return deadline; }

    public void setDeadline(Date deadline) {
        this.deadline = deadline;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }
}
