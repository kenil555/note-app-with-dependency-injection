package com.example.notesapp.feature_notes.domain.model;

import androidx.annotation.ColorInt;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;


@Entity
public class Note {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    public int id;
    @ColumnInfo(name = "title")
    public String Title;
    @ColumnInfo(name = "content")
    public String Content;
    @ColumnInfo(name = "email_id")
    public String emailId;
    @ColumnInfo(name = "time_stamp")
    public Long TimeStamp;
    @ColumnInfo(name = "color")
    public String Color;

    public static final String[] NOTE_COLORS = {
            "RedOrange",
            "RedPink",
            "BabyBlue",
            "Violet",
            "LightGreen"
    };
    public static final String getNoteColorName(int position) {
        if (position < 0 || position >= NOTE_COLORS.length) {
            throw new IllegalArgumentException("Invalid color position: " + position);
        }
        return NOTE_COLORS[position];
    }

    public Note( String title, String content, String emailId, Long timeStamp, String color) {
        Title = title;
        Content = content;
        this.emailId = emailId;
        TimeStamp = timeStamp;
        Color = color;
    }

    public Note(int id, String title, String content, String emailId, Long timeStamp, String color) {
        this.id = id;
        Title = title;
        Content = content;
        this.emailId = emailId;
        TimeStamp = timeStamp;
        Color = color;
    }

    public Note(){

    }
}


