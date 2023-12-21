package com.example.lab45.Task;

import com.example.lab45.Task.dto.CreateTaskDto;
import com.example.lab45.Task.dto.UpdateTaskDto;
import com.example.lab45.Task.errors.DataBaseException;
import com.example.lab45.Task.errors.DateParseException;
import com.example.lab45.Task.errors.TaskNotFoundException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController()
@RequestMapping("api/tasks")
@Tag(name = "Task", description = "Operations with tasks")
public class TaskController {
    private final TaskService taskService;

    @Autowired
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @Operation(summary = "get tasks", description = "get all existing tasks")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found tasks",
                    content = {@Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = Task.class)))})})
    @GetMapping
    public List<Task> getAll(@RequestParam(value = "sort", required = false) String sort) {
        if (sort != null) {
            return taskService.getAllTasks(sort);
        }
        return taskService.getAllTasks();
    }

    @Operation(summary = "get page", description = "get page of tasks")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found tasks",
                    content = {@Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = Task.class)))})})
    @GetMapping("/page")
    public PageImpl<Task> getPage(@RequestParam(value = "limit") int limit,
                           @RequestParam(value = "page") int page) {
        Pageable pageable = PageRequest.of(page, limit);
        return taskService.getAllTasks(pageable);
    }

    @Operation(summary = "get task", description = "get task by Id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found tasks",
                    content = {@Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = Task.class)))})})
    @GetMapping(path = "{taskId}")
    public Task getOneById(
            @PathVariable(name = "taskId") Long taskId
    ) {
        return taskService.getTaskById(taskId);
    }

    @Operation(summary = "create task", description = "create one task from given json")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Created successfully",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Task.class))})})
    @PostMapping
    public Task createTask(@RequestBody CreateTaskDto task) {
        return taskService.createTask(task);
    }

    @Operation(summary = "create many", description = "create many tasks")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Created successfully",
                    content = {@Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = Task.class)))})})
    @PostMapping("/create-many")
    public List<Task> createManyTasks(@RequestBody List<CreateTaskDto> tasks) {
        return taskService.createMany(tasks);
    }


    @Operation(summary = "update task", description = "update task information")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Updated successfully",
                    content = {@Content})})
    @PutMapping(path = "{taskId}")
    public int updateTask(
            @PathVariable("taskId") Long taskId,
            @RequestBody UpdateTaskDto task
    ) {
        return taskService.updateTask(taskId, task);
    }

    @Operation(summary = "delete task", description = "delete task by Id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Deleted successfully",
                    content = {@Content})})
    @DeleteMapping(path = "{taskId}")
    public int deleteTask(@PathVariable("taskId") Long taskId) {
        return taskService.deleteTask(taskId);
    }

    @ExceptionHandler({TaskNotFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleNFException(Exception ex) {
        return ErrorResponse.create(ex, HttpStatus.NOT_FOUND, ex.getMessage());
    }
    @ExceptionHandler({DataBaseException.class, DateParseException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleBRException(Exception ex) {
        return ErrorResponse.create(ex, HttpStatus.BAD_REQUEST, ex.getMessage());
    }
}
