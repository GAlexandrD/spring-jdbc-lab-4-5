package com.example.lab45.Task;

import com.example.lab45.Task.dto.CreateTaskDto;
import com.example.lab45.Task.dto.UpdateTaskDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@Transactional
public class TaskService {
  private final TaskRepository taskRepository;

  @Autowired
  public TaskService(TaskRepository taskRepository) {
    this.taskRepository = taskRepository;
  }

  public List<Task> getAllTasks() {
    return taskRepository.getAllTasks();
  }

  public List<Task> getAllTasks(String sort) {
    if (Objects.equals(sort, "default")) {
      return taskRepository.getAllTasks();
    }
    return taskRepository.getAllTasks(sort);
  }

  public PageImpl<Task> getAllTasks(Pageable pageable) {
    return taskRepository.getAllTasks(pageable);
  }

  public Task getTaskById(Long taskId) {
    return taskRepository.getTaskById(taskId);
  }

  public Task createTask(CreateTaskDto task) {
    return taskRepository.createTask(task.getTaskName(), task.getDeadline(), task.getPriority());
  }

  public int updateTask(Long id, UpdateTaskDto dto) {
    return taskRepository.updateTask(id, dto);
  }

  @Transactional
  public List<Task> createMany(List<CreateTaskDto> tasks) {
    List<Task> createdTasks = new ArrayList<Task>();
    for (CreateTaskDto dto : tasks) {
      Task created = taskRepository.createTask(dto.getTaskName(), dto.getDeadline(), dto.getPriority());
      createdTasks.add(created);
    }
    return createdTasks;
  }

  public int deleteTask(Long id) {
    return taskRepository.deleteTask(id);
  }
}
