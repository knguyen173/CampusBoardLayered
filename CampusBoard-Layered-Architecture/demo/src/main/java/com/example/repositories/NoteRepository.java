package com.example.repositories;

import com.example.entities.Note;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface NoteRepository extends CrudRepository<Note, Long> {
    List<Note> findByTitleContainingIgnoreCaseOrContentContainingIgnoreCase(String titleText, String contentText);
    
    List<Note> findByTitleContainingIgnoreCase(String title);
    
    List<Note> findAllByOrderByCreatedAtDesc();
    
    List<Note> findByUserId(Long userId);
    
    List<Note> findByUserIdOrderByCreatedAtDesc(Long userId);

}