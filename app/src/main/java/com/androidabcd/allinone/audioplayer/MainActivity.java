package com.androidabcd.allinone.audioplayer;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.androidabcd.allinone.R;
import com.androidabcd.allinone.contentprovider.ContentProviderActivity;
import com.androidabcd.allinone.firebase.FirebaseActivity;
import com.androidabcd.allinone.roomdb.NotesActivity;
import com.androidabcd.allinone.sharedpref.SharedPref;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mediaPlayer = new MediaPlayer();
        Button btnPlayUrl = findViewById(R.id.btnPlayUrl);
        btnPlayUrl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playAudioFromUrl("https://file-examples.com/wp-content/storage/2017/11/file_example_MP3_1MG.mp3");
            }
        });

        Button btnPlayResource = findViewById(R.id.btnPlayResource);
        btnPlayResource.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playAudioFromResource();
            }
        });

        Button btnPlaySDCard = findViewById(R.id.btnPlaySDCard);
        btnPlaySDCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playAudioFromSDCard();
            }
        });
    }

    private void playAudioFromUrl(String url) {
        try {
            mediaPlayer.reset();
            mediaPlayer.setDataSource(url);
            mediaPlayer.prepare();
            mediaPlayer.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void playAudioFromResource() {
        mediaPlayer.reset();
        mediaPlayer = MediaPlayer.create(this, R.raw.sample); // Assumes 'sample.mp3' is in the 'res/raw/' folder
        mediaPlayer.start();
    }

    private void playAudioFromSDCard() {
        Intent intent = new Intent(MainActivity.this, FirebaseActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }
}
