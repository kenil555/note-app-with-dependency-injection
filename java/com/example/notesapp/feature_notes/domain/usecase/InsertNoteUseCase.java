package com.example.notesapp.feature_notes.domain.usecase;

import com.example.notesapp.feature_notes.domain.model.Note;
import com.example.notesapp.feature_notes.domain.repository.NoteRepository;

import org.w3c.dom.Node;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Single;

public class InsertNoteUseCase {

    private final NoteRepository noteRepository;

    @Inject
    public InsertNoteUseCase(NoteRepository noteRepository){
        this.noteRepository = noteRepository;
    }

    public Single<Long> invoke(Note note){
        return noteRepository.insertNote(note);
    }

}
