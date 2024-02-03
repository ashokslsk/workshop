package com.androidabcd.allinone;

import android.app.Application;

import androidx.room.Room;

import com.androidabcd.allinone.roomdb.db.AppDatabase;
import com.google.firebase.FirebaseApp;

/**
 * Created by Ashok Kumar Srinivas on 03/02/24 around 6:16 am
 * Youtuber on www.youtube.com/AndroidAbcd
 * Contact for any queries ashokslsk@gmail.com
 * Copyright (c) 2024 www.github.com/ashokslsk All rights reserved.
 */
public class MyApp extends Application {
    private static AppDatabase database;

    @Override
    public void onCreate() {
        super.onCreate();

        database = Room.databaseBuilder(getApplicationContext(),
                        AppDatabase.class, "notes-database")
                .build();

        FirebaseApp.initializeApp(this);

    }

    public static AppDatabase getDatabase() {
        return database;
    }
}
