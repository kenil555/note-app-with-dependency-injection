package com.example.notesapp.feature_notes.presentation.savenotes;

import android.annotation.SuppressLint;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.notesapp.feature_notes.domain.model.Note;
import com.example.notesapp.feature_notes.domain.usecase.NotesUseCase;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.rxjava3.schedulers.Schedulers;

@HiltViewModel
public class AddEditViewModel extends ViewModel {

    private final NotesUseCase notesUseCase;
    public final MutableLiveData<Note> note = new MutableLiveData<>();

    public final MutableLiveData<String> title = new MutableLiveData<>();
    public final MutableLiveData<String> content = new MutableLiveData<>();
    public final MutableLiveData<String> colorName = new MutableLiveData<>();
    public final MutableLiveData<String>emailID = new MutableLiveData<>();


    @Inject
    public AddEditViewModel(NotesUseCase notesUseCase) {
        this.notesUseCase = notesUseCase;
    }

    @SuppressLint("CheckResult")
    public void getNoteById(Integer id){
        notesUseCase.getNoteByIdUseCase.invoke(id)
                .subscribeOn(Schedulers.io())
                .subscribe(note::postValue);
    }


    public void onEvent(AddEditNoteEventClass event){
        if(event instanceof AddEditNoteEventClass.EnteredTitle){
            title.setValue(((AddEditNoteEventClass.EnteredTitle) event).getTitle());
        }
        else if(event instanceof AddEditNoteEventClass.EnteredContent){
            content.setValue(((AddEditNoteEventClass.EnteredContent) event).getContent());
        }

        else if(event instanceof AddEditNoteEventClass.ColorChanged){
            colorName.setValue(((AddEditNoteEventClass.ColorChanged) event).getColorName());
        }
        else if(event instanceof AddEditNoteEventClass.EmailChanged){
            emailID.setValue(((AddEditNoteEventClass.EmailChanged) event).getEmailId());
        }
        else if(event instanceof AddEditNoteEventClass.SaveNote){
            Note note = ((AddEditNoteEventClass.SaveNote) event).getNote();
            notesUseCase.insertNoteUseCase.invoke(note)
                    .subscribeOn(Schedulers.io())
                    .subscribe();
        }
    }


}
