package com.example.notesapp.feature_notes.domain.usecase;

import com.example.notesapp.NotesApp;
import com.example.notesapp.feature_notes.domain.model.Note;
import com.example.notesapp.feature_notes.domain.repository.NoteRepository;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Single;

public class GetNoteByIdUseCase {

    private NoteRepository noteRepository;

    @Inject
    public GetNoteByIdUseCase(NoteRepository noteRepository){
        this.noteRepository = noteRepository;
    }

    public Single<Note> invoke(int id){
        return noteRepository.getNoteById(id);
    }
}
