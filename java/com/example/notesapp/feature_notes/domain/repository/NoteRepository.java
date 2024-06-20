package com.example.notesapp.feature_notes.domain.repository;

import com.example.notesapp.feature_notes.domain.model.Note;
import java.util.List;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;

public interface NoteRepository {
    Observable<List<Note>> getAllNotesByEmailId(String emailId);
    Single<Note> getNoteById(int id);
    Single<Long> insertNote(Note note);
    void deleteNote(Note note);
}
