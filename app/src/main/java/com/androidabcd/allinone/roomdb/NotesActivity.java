package com.androidabcd.allinone.roomdb;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;

import com.androidabcd.allinone.MyApp;
import com.androidabcd.allinone.R;
import com.androidabcd.allinone.roomdb.db.NoteDao;
import com.androidabcd.allinone.roomdb.model.Note;

import java.util.List;

public class NotesActivity extends AppCompatActivity {
    private EditText editTextNote;
    private Button buttonSave;
    private Button buttonClear;

    private TextView textViewNotes;
    private ScrollView scrollView;

    private NoteDao noteDao;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes);

        noteDao = MyApp.getDatabase().noteDao();
        scrollView = findViewById(R.id.scrollView);

        editTextNote = findViewById(R.id.editTextNote);
        buttonSave = findViewById(R.id.buttonSave);
        buttonClear = findViewById(R.id.buttonClear);
        textViewNotes = findViewById(R.id.textViewNotes);

        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveNote();
            }
        });

        buttonClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearNotes();
            }
        });

        displayNotes();
    }

    private void saveNote() {
        final String text = editTextNote.getText().toString().trim();
        if (!text.isEmpty()) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    Note note = new Note();
                    note.setNoteText(text);
                    noteDao.insert(note);
                    editTextNote.post(new Runnable() {
                        @Override
                        public void run() {
                            editTextNote.setText("");
                            displayNotes();
                        }
                    });
                }
            }).start();
        }
    }

    private void clearNotes() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                noteDao.deleteAllNotes();
                displayNotes();
            }
        }).start();
    }


    private void displayNotes() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                final List<Note> notes = noteDao.getAllNotes();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        StringBuilder notesText = new StringBuilder();
                        for (int i = notes.size() - 1; i >= 0; i--) {
                            Note note = notes.get(i);
                            notesText.append("<b>ID:</b> <i>").append(note.getId()).append("</i><br/>")
                                    .append("<b>Note:</b> ").append(note.getNoteText()).append("<br/><br/>");
                        }
                        textViewNotes.setText(Html.fromHtml(notesText.toString(), Html.FROM_HTML_MODE_COMPACT));
                    }
                });
            }
        }).start();
    }
}