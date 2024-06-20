package com.example.notesapp.feature_notes.presentation.savenotes;

import com.example.notesapp.feature_notes.domain.model.Note;

public class AddEditNoteEventClass {

    public static class EnteredTitle extends AddEditNoteEventClass{
        private final String title;

        public EnteredTitle(String title){
            this.title = title;
        }

        public String getTitle(){
            return title;
        }
    }

    public static class EnteredContent extends AddEditNoteEventClass{
        private final String content;

        public EnteredContent(String content){
            this.content = content;
        }

        public String getContent(){
            return content;
        }
    }

    public static class ColorChanged extends AddEditNoteEventClass{
        private final String colorName;

        public ColorChanged(String colorName){
            this.colorName = colorName;
        }

        public String getColorName(){
            return colorName;
        }
    }

    public static class EmailChanged extends AddEditNoteEventClass{
        private final String emailId;

        public EmailChanged(String emailId){
            this.emailId = emailId;
        }

        public String getEmailId(){
            return emailId;
        }
    }
    public static class SaveNote extends AddEditNoteEventClass{
        private final Note note;

        public SaveNote(Note note){
            this.note = note;
        }

        public Note getNote(){
            return note;
        }
    }
}
