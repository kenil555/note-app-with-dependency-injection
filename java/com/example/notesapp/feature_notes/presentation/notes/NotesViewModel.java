package com.example.notesapp.feature_notes.presentation.notes;

import android.annotation.SuppressLint;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.notesapp.feature_notes.domain.model.Note;
import com.example.notesapp.feature_notes.domain.usecase.NotesUseCase;
import com.example.notesapp.feature_notes.domain.util.NoteOrder;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.rxjava3.schedulers.Schedulers;

@HiltViewModel
public class NotesViewModel extends ViewModel {
    private final NotesUseCase notesUseCase;
    public MutableLiveData<List<Note>> noteList = new MutableLiveData<List<Note>>();
    public MutableLiveData<Boolean> isOrderVisible = new MutableLiveData<>();
    private String emailId = "";

    @Inject
    public NotesViewModel(NotesUseCase notesUseCase){
        this.notesUseCase = notesUseCase;
    }

    public void getNotesList(String emailId){
        this.emailId = emailId;
        getNotes(emailId, NoteOrder.Title);
    }
    public void onEvent(NotesEvent event){
       if(event instanceof NotesEvent.Order){
           switch (((NotesEvent.Order) event).getNoteOrder()){
               case Date : getNotes(emailId, NoteOrder.Date);
               break;
               case Title : getNotes(emailId, NoteOrder.Title);
               break;
               case Color : getNotes(emailId, NoteOrder.Color);
               break;
           }
       }
       else if(event instanceof NotesEvent.Delete){
            notesUseCase.deleteNoteUseCase.invoke(((NotesEvent.Delete) event).getNote());
       }
       else if(event == NotesEvent.RestoreNote){

       }
       else if(event == NotesEvent.toggleOrderSection){
           isOrderVisible.setValue(!isOrderVisible.getValue());
       }
    }

    @SuppressLint("CheckResult")
    private void getNotes(String emailId , NoteOrder noteOrder){
         notesUseCase.getAllNotesUseCase.invoke(emailId, noteOrder)
                 .subscribeOn(Schedulers.io())
                 .subscribe(noteList::postValue);
    }
}
