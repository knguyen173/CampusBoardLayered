package com.example.repositories;

import com.example.entities.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    // Find tasks by user ID
    List<Task> findByUserId(Long userId);
    
    // Find tasks by user ID and ordered by priority
    List<Task> findByUserIdOrderByPriorityDesc(Long userId);
    
    // Find completed tasks by user ID
    List<Task> findByUserIdAndCompleted(Long userId, boolean completed);
    
    // Find tasks containing text in title or content for a specific user
    List<Task> findByUserIdAndTitleContainingIgnoreCaseOrUserIdAndContentContainingIgnoreCase(
        Long userIdForTitle, String titleText, Long userIdForContent, String contentText);
    
    // Find tasks by priority greater than or equal to value
    List<Task> findByUserIdAndPriorityGreaterThanEqual(Long userId, Integer priority);
}