package com.example.notesapp.feature_notes.data.data_source;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Upsert;
import com.example.notesapp.feature_notes.domain.model.Note;

import org.w3c.dom.Node;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;

@Dao
public interface NoteDao {
    @Query("SELECT * FROM note WHERE email_Id = :emailId")
    Observable<List<Note>> getAllNotesByEmailId(String emailId);
    @Query("SELECT * FROM note WHERE ID = :id")
    Single<Note> getNoteById(int id);
    @Insert(onConflict =  OnConflictStrategy.REPLACE)
    Single<Long> inertNote(Note note);
    @Delete
    Completable deleteNote(Note note);
}
