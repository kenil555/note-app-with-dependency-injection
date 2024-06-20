package com.example.notesapp.feature_notes.data.data_source;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.notesapp.feature_notes.domain.model.Note;

@Database(
        entities = {Note.class},
        version = 1
)
public abstract class NoteDatabase extends RoomDatabase {
    public static final String NOTE_DATABASE_NAME = "notes_database";
    public abstract NoteDao noteDao();
}
