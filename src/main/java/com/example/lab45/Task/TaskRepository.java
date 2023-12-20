package com.example.lab45.Task;

import com.example.taskmanager.Task.dto.UpdateTaskDto;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import java.util.List;


@Repository
public interface TaskRepository  {
    Task getTaskById(Long id);
    Task getByName(String name);
    List<Task> getAllTasks();
    List<Task> getAllTasks(String sort);
    PageImpl<Task> getAllTasks(Pageable pageable);
    Task createTask(String taskName, String deadline, Integer priority);
    int updateTask(Long id, UpdateTaskDto update);
    int deleteTask(Long id);
}
