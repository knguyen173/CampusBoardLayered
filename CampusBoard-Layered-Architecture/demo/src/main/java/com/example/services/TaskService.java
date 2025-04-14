package com.example.services;

import com.example.entities.Task;
import com.example.DTO.TaskDTO;
import com.example.DTO.CreateTaskRequest;
import com.example.DTO.UpdateTaskRequest;
import com.example.repositories.TaskRepository;
import com.example.exception.ResourceNotFoundException;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TaskService {

    private final TaskRepository taskRepository;
    
    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    private TaskDTO convertToDTO(Task task) {
        return new TaskDTO(
            task.getId(),
            task.getUserId(),
            task.getTitle(),
            task.getContent(),
            task.getPriority(),
            task.isCompleted(),
            task.getCreatedAt(),
            task.getUpdatedAt()
        );
    }
    
    public List<TaskDTO> getTasksByUserId(Long userId) {
        return taskRepository.findByUserIdOrderByPriorityDesc(userId)
            .stream()
            .map(this::convertToDTO)
            .collect(Collectors.toList());
    }
    
    public TaskDTO getTaskById(Long id) {
        
        Optional<Task> taskOptional = taskRepository.findById(id);
        Task task = taskOptional.orElseThrow(() -> new ResourceNotFoundException("Task not found with id: " + id));
            
        return convertToDTO(task);
    }
    
    @Transactional
    public TaskDTO createTask(CreateTaskRequest createTaskRequest) {
        Task task = new Task(
            createTaskRequest.getUserId(),
            createTaskRequest.getTitle(),
            createTaskRequest.getContent(),
            createTaskRequest.getPriority()
        );
        Task savedTask = taskRepository.save(task);
        return convertToDTO(savedTask);
    }
    
    @Transactional
    public TaskDTO updateTask(Long id, UpdateTaskRequest updateTaskRequest) {     
        Optional<Task> taskOptional = taskRepository.findById(id);
        Task task = taskOptional.orElseThrow(() -> new ResourceNotFoundException("Task not found with id: " + id));
            
        if (updateTaskRequest.getTitle() != null) {
            task.setTitle(updateTaskRequest.getTitle());
        }
        
        if (updateTaskRequest.getContent() != null) {
            task.setContent(updateTaskRequest.getContent());
        }
        
        if (updateTaskRequest.getPriority() != null) {
            task.setPriority(updateTaskRequest.getPriority());
        }
        
        if (updateTaskRequest.getCompleted() != null) {
            task.setCompleted(updateTaskRequest.getCompleted());
        }
        
        task.updateTimestamp();
        Task updatedTask = taskRepository.save(task);
        return convertToDTO(updatedTask);
    }
    
    @Transactional
    public void deleteTask(Long id) {
        if (!taskRepository.existsById(id)) {
            throw new ResourceNotFoundException("Task not found with id: " + id);
        }
        taskRepository.deleteById(id);
    }
    
    public List<TaskDTO> getCompletedTasksByUserId(Long userId) {
        return taskRepository.findByUserIdAndCompleted(userId, true)
            .stream()
            .map(this::convertToDTO)
            .collect(Collectors.toList());
    }
    
    public List<TaskDTO> getIncompleteTasksByUserId(Long userId) {
        return taskRepository.findByUserIdAndCompleted(userId, false)
            .stream()
            .map(this::convertToDTO)
            .collect(Collectors.toList());
    }
    
    public List<TaskDTO> searchTasksForUser(Long userId, String query) {
        return taskRepository.findByUserIdAndTitleContainingIgnoreCaseOrUserIdAndContentContainingIgnoreCase(
                userId, query, userId, query)
            .stream()
            .map(this::convertToDTO)
            .collect(Collectors.toList());
    }

    public List<TaskDTO> getHighPriorityTasks(Long userId, Integer minPriority) {
        return taskRepository.findByUserIdAndPriorityGreaterThanEqual(userId, minPriority)
            .stream()
            .map(this::convertToDTO)
            .collect(Collectors.toList());
    }
}