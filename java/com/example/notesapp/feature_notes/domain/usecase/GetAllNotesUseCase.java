package com.example.notesapp.feature_notes.domain.usecase;

import com.example.notesapp.feature_notes.domain.model.Note;
import com.example.notesapp.feature_notes.domain.repository.NoteRepository;
import com.example.notesapp.feature_notes.domain.util.NoteOrder;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Observable;

public class GetAllNotesUseCase {
    private NoteRepository noteRepository;

    @Inject
    public GetAllNotesUseCase(NoteRepository noteRepository){
        this.noteRepository = noteRepository;
    }
    public Observable<List<Note>> invoke(String emailId, NoteOrder noteOrder){
        return  noteRepository.getAllNotesByEmailId(emailId)
                .map(notes -> {
                    switch (noteOrder){
                        case Title: notes.sort(new Comparator<Note>() {
                            @Override
                            public int compare(Note o1, Note o2) {
                                return o1.Title.compareToIgnoreCase(o2.Title);
                            }
                        });
                        case Date : notes.sort(new Comparator<Note>() {
                            @Override
                            public int compare(Note o1, Note o2) {
                                return o1.TimeStamp.compareTo(o2.TimeStamp);
                            }
                        });

                        case Color: notes.sort(new Comparator<Note>() {
                            @Override
                            public int compare(Note o1, Note o2) {
                                return o1.Color.compareToIgnoreCase(o2.Color);
                            }
                        });
                    }
                    Collections.reverse(notes);
                    return notes;
                });
    }

}
