package com.example.notesapp.feature_notes.domain.usecase;

import com.example.notesapp.feature_notes.domain.model.Note;
import com.example.notesapp.feature_notes.domain.repository.NoteRepository;

import javax.inject.Inject;

public class DeleteNoteUseCase {

    private final NoteRepository noteRepository;

    @Inject
    public DeleteNoteUseCase(NoteRepository noteRepository){
        this.noteRepository = noteRepository;
    }

    public void invoke(Note note){
        noteRepository.deleteNote(note);
    }

}
