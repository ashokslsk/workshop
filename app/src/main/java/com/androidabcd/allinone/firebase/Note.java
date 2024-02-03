package com.androidabcd.allinone.firebase;

/**
 * Created by Ashok Kumar Srinivas on 03/02/24 around 6:54 am
 * Youtuber on www.youtube.com/AndroidAbcd
 * Contact for any queries ashokslsk@gmail.com
 * Copyright (c) 2024 www.github.com/ashokslsk All rights reserved.
 */
public class Note {
    public String title;
    public String content;

    public Note() {
        // Default constructor required for Firebase
    }

    public Note(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
