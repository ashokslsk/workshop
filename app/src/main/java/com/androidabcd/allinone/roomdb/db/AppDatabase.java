package com.androidabcd.allinone.roomdb.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.androidabcd.allinone.roomdb.model.Note;

/**
 * Created by Ashok Kumar Srinivas on 03/02/24 around 6:15 am
 * Youtuber on www.youtube.com/AndroidAbcd
 * Contact for any queries ashokslsk@gmail.com
 * Copyright (c) 2024 www.github.com/ashokslsk All rights reserved.
 */
@Database(entities = {Note.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    public abstract NoteDao noteDao();
}

