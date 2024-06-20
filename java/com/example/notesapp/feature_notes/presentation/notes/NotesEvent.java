package com.example.notesapp.feature_notes.presentation.notes;

import com.example.notesapp.feature_notes.domain.model.Note;
import com.example.notesapp.feature_notes.domain.util.NoteOrder;

public class NotesEvent {
    public static class Order extends NotesEvent{
        private final NoteOrder noteOrder;
        public Order(NoteOrder noteOrder) {
            this.noteOrder = noteOrder;
        }
        public NoteOrder getNoteOrder(){
            return noteOrder;
        }
    }
    public static class Delete extends NotesEvent{
        private final Note note;
        public Delete(Note note) {
            this.note = note;
        }
        public Note getNote(){
            return note;
        }
    }

    public static final NotesEvent RestoreNote = new NotesEvent() {};
    public static final NotesEvent toggleOrderSection = new NotesEvent() {};
}
