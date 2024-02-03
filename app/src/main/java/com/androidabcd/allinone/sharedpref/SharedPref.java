package com.androidabcd.allinone.sharedpref;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.androidabcd.allinone.R;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class SharedPref extends AppCompatActivity {

    private static final String PREFS_NAME = "MyPrefs";
    private static final String TEXT_KEY = "savedText";

    private EditText editText;
    private TextView displayText;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shared_pref);

        // Initialize views
        editText = findViewById(R.id.editText);
        displayText = findViewById(R.id.displayText);

        // Load SharedPreferences
        sharedPreferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        String savedText = sharedPreferences.getString(TEXT_KEY, "");
        displayText.setText(savedText);

        // Save button click listener
        Button saveButton = findViewById(R.id.saveButton);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveTextToSharedPreferences();
            }
        });
    }

    private void saveTextToSharedPreferences() {
        // Get text from EditText
        String inputText = editText.getText().toString();

        // Save text to SharedPreferences
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(TEXT_KEY, inputText);
        editor.apply();

        // Display saved text
        displayText.setText(inputText);
    }
}
