package com.androidabcd.allinone.contentprovider;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.androidabcd.allinone.R;

public class ContentProviderActivity extends AppCompatActivity {

    private EditText inputText;
    private TextView displayText;

    // ContentProvider URI
    private static final Uri CONTENT_URI = Uri.parse("content://com.androidabcd.allinone.contentprovider/mydata");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content_provider);

        inputText = findViewById(R.id.inputText);
        displayText = findViewById(R.id.displayText);

        Button saveButton = findViewById(R.id.saveButton);
        Button retrieveButton = findViewById(R.id.retrieveButton);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveData(inputText.getText().toString());
            }
        });

        retrieveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                retrieveData();
            }
        });
    }

    private void saveData(String data) {
        ContentResolver contentResolver = getContentResolver();
        ContentValues values = new ContentValues();
        values.put("data", data);
        contentResolver.insert(CONTENT_URI, values);

        Log.d("MainActivity", "Data saved: " + data);
    }
    private void retrieveData() {
        ContentResolver contentResolver = getContentResolver();
        Cursor cursor = contentResolver.query(CONTENT_URI, null, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            try {
                int dataIndex = cursor.getColumnIndexOrThrow("data");
                String data = cursor.getString(dataIndex);
                displayText.setText(data);
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
                displayText.setText("Invalid column index for 'data'");
            } finally {
                cursor.close();
            }
        } else {
            displayText.setText("No data found");
        }
    }


}
