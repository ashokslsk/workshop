package com.androidabcd.allinone.contentprovider;

/**
 * Created by Ashok Kumar Srinivas on 03/02/24 around 5:38 am
 * Youtuber on www.youtube.com/AndroidAbcd
 * Contact for any queries ashokslsk@gmail.com
 * Copyright (c) 2024 www.github.com/ashokslsk All rights reserved.
 */
import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.net.Uri;

public class MyContentProvider extends ContentProvider {

    private static final String AUTHORITY = "com.androidabcd.allinone.contentprovider";
    private static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/mydata");

    private static final int DATA = 1;
    private static final UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        uriMatcher.addURI(AUTHORITY, "mydata", DATA);
    }

    @Override
    public boolean onCreate() {
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        MatrixCursor cursor = new MatrixCursor(new String[]{"_id", "data"});
        // In a real-world scenario, you might fetch data from a database here.
        return cursor;
    }

    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        try {
            if (uriMatcher.match(uri) == DATA) {
                // In a real-world scenario, you might insert data into a database here.
                // Log statements here can help you identify issues.
                return CONTENT_URI;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        return 0;
    }
}
