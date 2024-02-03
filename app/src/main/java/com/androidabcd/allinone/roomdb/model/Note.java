package com.androidabcd.allinone.roomdb.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * Created by Ashok Kumar Srinivas on 03/02/24 around 6:13 am
 * Youtuber on www.youtube.com/AndroidAbcd
 * Contact for any queries ashokslsk@gmail.com
 * Copyright (c) 2024 www.github.com/ashokslsk All rights reserved.
 */
@Entity(tableName = "notes")
public class Note {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String NoteText;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNoteText() {
        return NoteText;
    }

    public void setNoteText(String noteText) {
        NoteText = noteText;
    }
}
