package com.example.controllers;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.concurrent.ThreadLocalRandom;
import com.example.DTO.NoteDTO;
import com.example.DTO.CreateNoteRequest;
import com.example.DTO.UpdateNoteRequest;
import com.example.services.NoteService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@RestController
@RequestMapping("/api/notes")
public class NoteController {

    private static final Logger logger = LoggerFactory.getLogger(NoteController.class);
    private final NoteService noteService;
    
    @Autowired
    public NoteController(NoteService noteService) {
        this.noteService = noteService;
    }

    @GetMapping
    public ResponseEntity<List<NoteDTO>> getAllNotes() {
        try {
            List<NoteDTO> notes = noteService.getAllNotes();
            return ResponseEntity.ok(notes);
        } catch (Exception e) {
            logger.error("Error fetching all notes", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    @PostMapping
    public ResponseEntity<NoteDTO> createNote(@RequestBody CreateNoteRequest createNoteRequest) {
        logger.info("Received note data on backend: {}", createNoteRequest); // Debugging line
        
        try {
            NoteDTO createdNote = noteService.createNote(createNoteRequest);
            return new ResponseEntity<>(createdNote, HttpStatus.CREATED);
        } catch (Exception e) {
            logger.error("Error creating note", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<NoteDTO> updateNote(@PathVariable Long id, @RequestBody UpdateNoteRequest updateNoteRequest) {
        logger.info("Received note data on backend for update: {}", updateNoteRequest); // Debugging line
        
        try {
            NoteDTO updatedNote = noteService.updateNote(id, updateNoteRequest);
            return ResponseEntity.ok(updatedNote);
        } catch (Exception e) {
            logger.error("Error updating note with id: {}", id, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteNote(@PathVariable Long id) {
        try {
            noteService.deleteNote(id);
            return ResponseEntity.ok().body(new MessageResponse("Note deleted successfully"));
        } catch (Exception e) {
            logger.error("Error deleting note with id: {}", id, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<NoteDTO> getNoteById(@PathVariable Long id) {
        try {
            NoteDTO note = noteService.getNoteById(id);
            return ResponseEntity.ok(note);
        } catch (Exception e) {
            logger.error("Error fetching note with id: {}", id, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/search")
    public ResponseEntity<List<NoteDTO>> searchNotes(@RequestParam String query) {
        try {
            List<NoteDTO> notes = noteService.searchNotes(query);
            return ResponseEntity.ok(notes);
        } catch (Exception e) {
            logger.error("Error searching notes with query: {}", query, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    private static class MessageResponse {
        private String message;
        
        public MessageResponse(String message) {
            this.message = message;
        }
        
        public String getMessage() {
            return message;
        }
        
        public void setMessage(String message) {
            this.message = message;
        }
    }
}