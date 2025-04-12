package com.example.repositories;

import com.example.entities.Note;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NoteRepository extends JpaRepository<Note, Long> {
    List<Note> findByTitleContainingIgnoreCaseOrContentContainingIgnoreCase(String titleText, String contentText);
    
    List<Note> findByTitleContainingIgnoreCase(String title);
    
    List<Note> findAllByOrderByCreatedAtDesc();
    
    List<Note> findByUserId(Long userId);
    
    List<Note> findByUserIdOrderByCreatedAtDesc(Long userId);
}