package com.example.notesapp.feature_notes.data.repositoryimplementation;

import com.example.notesapp.feature_notes.data.data_source.NoteDao;
import com.example.notesapp.feature_notes.domain.model.Note;
import com.example.notesapp.feature_notes.domain.repository.NoteRepository;

import java.util.List;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class NoteRepositoryImpl implements NoteRepository {
    NoteDao noteDao;
    public NoteRepositoryImpl(NoteDao noteDao){
        this.noteDao = noteDao;
    }
    @Override
    public Observable<List<Note>> getAllNotesByEmailId(String emailId) {
        return noteDao.getAllNotesByEmailId(emailId);
    }

    @Override
    public Single<Note> getNoteById(int id) {
        return noteDao.getNoteById(id);
    }

    @Override
    public Single<Long> insertNote(Note note) {
        return noteDao.inertNote(note);
    }

    @Override
    public void deleteNote(Note note) {
        noteDao.deleteNote(note)
                .subscribeOn(Schedulers.io())
                .subscribe();
    }
}
