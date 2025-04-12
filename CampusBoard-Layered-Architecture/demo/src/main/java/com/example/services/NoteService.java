package com.example.services;

import com.example.entities.Note;
import com.example.DTO.NoteDTO;
import com.example.DTO.CreateNoteRequest;
import com.example.DTO.UpdateNoteRequest;
import com.example.repositories.NoteRepository;
import com.example.exception.ResourceNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class NoteService {
    
    private static final Logger logger = LoggerFactory.getLogger(NoteService.class);
    private final NoteRepository noteRepository;
    
    @Autowired
    public NoteService(NoteRepository noteRepository) {
        this.noteRepository = noteRepository;
    }
    
    private NoteDTO convertToDTO(Note note) {
        return new NoteDTO(
            note.getId(),
            note.getUserId(),
            note.getTitle(),
            note.getContent(),
            note.getCreatedAt(),
            note.getUpdatedAt()
        );
    }
    
    public List<NoteDTO> getAllNotes() {
        return noteRepository.findAllByOrderByCreatedAtDesc()
            .stream()
            .map(this::convertToDTO)
            .collect(Collectors.toList());
    }
    
    public NoteDTO getNoteById(Long id) {
        Note note = noteRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Note not found with id: " + id));
        return convertToDTO(note);
    }

    @Transactional
    public NoteDTO createNote(CreateNoteRequest createNoteRequest) {
        logger.info("Creating note with data: {}", createNoteRequest);
        
        Long userId = createNoteRequest.getUserId() != null ? createNoteRequest.getUserId() : 1L;
        
        Note note = new Note(
            userId,
            createNoteRequest.getTitle(),
            createNoteRequest.getContent()
        );
        
        Note savedNote = noteRepository.save(note);
        logger.info("Note created with ID: {}", savedNote.getId());
        return convertToDTO(savedNote);
    }
    
    @Transactional
    public NoteDTO updateNote(Long id, UpdateNoteRequest updateNoteRequest) {
        logger.info("Updating note ID {} with data: {}", id, updateNoteRequest);
        
        Note note = noteRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Note not found with id: " + id));
            
        if (updateNoteRequest.getTitle() != null) {
            note.setTitle(updateNoteRequest.getTitle());
        }
        
        if (updateNoteRequest.getContent() != null) {
            note.setContent(updateNoteRequest.getContent());
        }
        
        note.updateTimestamp();
        Note updatedNote = noteRepository.save(note);
        logger.info("Note updated: {}", updatedNote);
        return convertToDTO(updatedNote);
    }

    @Transactional
    public void deleteNote(Long id) {
        logger.info("Deleting note with ID: {}", id);
        
        if (!noteRepository.existsById(id)) {
            throw new ResourceNotFoundException("Note not found with id: " + id);
        }
        noteRepository.deleteById(id);
        logger.info("Note deleted successfully");
    }
    
    public List<NoteDTO> searchNotes(String query) {
        logger.info("Searching notes with query: {}", query);
        
        return noteRepository.findByTitleContainingIgnoreCaseOrContentContainingIgnoreCase(query, query)
            .stream()
            .map(this::convertToDTO)
            .collect(Collectors.toList());
    }
}
