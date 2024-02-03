package com.androidabcd.allinone.firebase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.androidabcd.allinone.R;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.androidabcd.allinone.audioplayer.MainActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class FirebaseActivity extends AppCompatActivity {

    private EditText titleEditText, contentEditText;
    private DatabaseReference databaseReference;
    private TextView notesTextView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_firebase);
        notesTextView = findViewById(R.id.notesTextView);

        titleEditText = findViewById(R.id.titleEditText);
        contentEditText = findViewById(R.id.contentEditText);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        // Use the "notes" child as the reference
        databaseReference = database.getReference("notes");

        Button saveButton = findViewById(R.id.saveButton);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveNote();
            }
        });

        Button fetchButton = findViewById(R.id.fetchButton);
        fetchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fetchNotes();
            }
        });
    }

    private void saveNote() {
        String title = titleEditText.getText().toString();
        String content = contentEditText.getText().toString();

        if (!title.isEmpty() && !content.isEmpty()) {
            // Create a unique key for the note
            String noteId = databaseReference.push().getKey();

            // Create a Note object
            Note note = new Note(title, content);

            // Save the note to Firebase Realtime Database
            databaseReference.child(noteId).setValue(note);

            // Clear the input fields
            titleEditText.setText("");
            contentEditText.setText("");

            Toast.makeText(this, "Note saved successfully", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Title and content cannot be empty", Toast.LENGTH_SHORT).show();
        }
    }

    private void fetchNotes() {
        // Read data from Firebase Realtime Database
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // Clear previous notes
                notesTextView.setText("");

                // Iterate through all notes in the database
                for (DataSnapshot noteSnapshot : dataSnapshot.getChildren()) {
                    // Retrieve each note and append to the TextView
                    Note note = noteSnapshot.getValue(Note.class);
                    if (note != null) {
                        String noteText = "Title: " + note.title + "\nContent: " + note.content + "\n\n";
                        notesTextView.append(noteText);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(FirebaseActivity.this, "Error fetching notes", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
