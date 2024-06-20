package com.example.notesapp.feature_notes.domain.usecase;

import com.example.notesapp.feature_notes.domain.model.Note;

public class NotesUseCase {

    public  GetAllNotesUseCase getAllNotesUseCase;
    public  GetNoteByIdUseCase getNoteByIdUseCase;
    public  InsertNoteUseCase insertNoteUseCase;
    public  DeleteNoteUseCase deleteNoteUseCase;

    public NotesUseCase(
            GetAllNotesUseCase getAllNotesUseCase,
            GetNoteByIdUseCase getNoteByIdUseCase,
            InsertNoteUseCase insertNoteUseCase,
            DeleteNoteUseCase deleteNoteUseCase){

        this.getAllNotesUseCase = getAllNotesUseCase;
        this.getNoteByIdUseCase = getNoteByIdUseCase;
        this.insertNoteUseCase = insertNoteUseCase;
        this.deleteNoteUseCase = deleteNoteUseCase;
    }


}
