package com.example.repositories;

import com.example.entities.Task;
import org.springframework.data.repository.CrudRepository;

import java.util.List;


public interface TaskRepository extends CrudRepository<Task, Long> {
    List<Task> findByUserId(Long userId);
    
    List<Task> findByUserIdOrderByPriorityDesc(Long userId);
    
    List<Task> findByUserIdAndCompleted(Long userId, boolean completed);
    
    List<Task> findByUserIdAndTitleContainingIgnoreCaseOrUserIdAndContentContainingIgnoreCase(
        Long userIdForTitle, String titleText, Long userIdForContent, String contentText);
    
    List<Task> findByUserIdAndPriorityGreaterThanEqual(Long userId, Integer priority);
}