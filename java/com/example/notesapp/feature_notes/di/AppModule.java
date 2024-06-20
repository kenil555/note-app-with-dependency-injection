package com.example.notesapp.feature_notes.di;

import android.app.Application;

import androidx.lifecycle.SavedStateHandle;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.notesapp.feature_notes.data.data_source.NoteDatabase;
import com.example.notesapp.feature_notes.data.repositoryimplementation.NoteRepositoryImpl;
import com.example.notesapp.feature_notes.domain.repository.NoteRepository;
import com.example.notesapp.feature_notes.domain.usecase.DeleteNoteUseCase;
import com.example.notesapp.feature_notes.domain.usecase.GetAllNotesUseCase;
import com.example.notesapp.feature_notes.domain.usecase.GetNoteByIdUseCase;
import com.example.notesapp.feature_notes.domain.usecase.InsertNoteUseCase;
import com.example.notesapp.feature_notes.domain.usecase.NotesUseCase;
import com.example.notesapp.feature_notes.presentation.notes.NotesViewModel;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;

@Module()
@InstallIn(SingletonComponent.class)
public class AppModule {
        @Provides
        @Singleton
        public NoteDatabase provideNoteDatabase(Application application){
                return Room.databaseBuilder(
                        application,
                        NoteDatabase.class,
                        NoteDatabase.NOTE_DATABASE_NAME
                ).build();
        }

        @Provides
        @Singleton
        public NoteRepository provideNoteRepository(NoteDatabase noteDatabase){
                return new NoteRepositoryImpl(noteDatabase.noteDao());
        }

        @Provides
        @Singleton
        public NotesUseCase provideNotesUseCase(NoteRepository repo){
                return new NotesUseCase(
                        new GetAllNotesUseCase(repo),
                        new GetNoteByIdUseCase(repo),
                        new InsertNoteUseCase(repo),
                        new DeleteNoteUseCase(repo)
                );
        }

}
