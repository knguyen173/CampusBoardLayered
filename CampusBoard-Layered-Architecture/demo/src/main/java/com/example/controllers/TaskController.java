package com.example.controllers;

import com.example.DTO.TaskDTO;
import com.example.DTO.CreateTaskRequest;
import com.example.DTO.UpdateTaskRequest;
import com.example.services.TaskService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    private final TaskService taskService;
    
    @Autowired
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<TaskDTO>> getTasksByUserId(@PathVariable Long userId) {
        List<TaskDTO> tasks = taskService.getTasksByUserId(userId);
        return ResponseEntity.ok(tasks);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TaskDTO> getTaskById(@PathVariable Long id) {
        TaskDTO task = taskService.getTaskById(id);
        return ResponseEntity.ok(task);
    }

    @PostMapping
    public ResponseEntity<TaskDTO> createTask(@RequestBody CreateTaskRequest createTaskRequest) {
        TaskDTO createdTask = taskService.createTask(createTaskRequest);
        return new ResponseEntity<>(createdTask, HttpStatus.CREATED);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<TaskDTO> updateTask(@PathVariable Long id, @RequestBody UpdateTaskRequest updateTaskRequest) {
        TaskDTO updatedTask = taskService.updateTask(id, updateTaskRequest);
        return ResponseEntity.ok(updatedTask);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id) {
        taskService.deleteTask(id);
        return ResponseEntity.noContent().build();
    }
    
    @GetMapping("/user/{userId}/completed")
    public ResponseEntity<List<TaskDTO>> getCompletedTasksByUserId(@PathVariable Long userId) {
        List<TaskDTO> tasks = taskService.getCompletedTasksByUserId(userId);
        return ResponseEntity.ok(tasks);
    }
    
    @GetMapping("/user/{userId}/incomplete")
    public ResponseEntity<List<TaskDTO>> getIncompleteTasksByUserId(@PathVariable Long userId) {
        List<TaskDTO> tasks = taskService.getIncompleteTasksByUserId(userId);
        return ResponseEntity.ok(tasks);
    }

    @GetMapping("/user/{userId}/search")
    public ResponseEntity<List<TaskDTO>> searchTasksForUser(
            @PathVariable Long userId,
            @RequestParam String query) {
        List<TaskDTO> tasks = taskService.searchTasksForUser(userId, query);
        return ResponseEntity.ok(tasks);
    }
    
    @GetMapping("/user/{userId}/priority")
    public ResponseEntity<List<TaskDTO>> getHighPriorityTasks(
            @PathVariable Long userId,
            @RequestParam(defaultValue = "3") Integer minPriority) {
        List<TaskDTO> tasks = taskService.getHighPriorityTasks(userId, minPriority);
        return ResponseEntity.ok(tasks);
    }
}