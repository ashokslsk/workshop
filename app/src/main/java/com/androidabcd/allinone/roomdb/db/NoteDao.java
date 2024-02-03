package com.androidabcd.allinone.roomdb.db;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.androidabcd.allinone.roomdb.model.Note;

import java.util.List;

/**
 * Created by Ashok Kumar Srinivas on 03/02/24 around 6:15 am
 * Youtuber on www.youtube.com/AndroidAbcd
 * Contact for any queries ashokslsk@gmail.com
 * Copyright (c) 2024 www.github.com/ashokslsk All rights reserved.
 */
@Dao
public interface NoteDao {

    @Insert
    void insert(Note note);

    @Query("SELECT * FROM notes")
    List<Note> getAllNotes();

    @Query("DELETE FROM notes")
    void deleteAllNotes();
}

