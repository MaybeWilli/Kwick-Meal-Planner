/*
* This java page controls the music selection page. Every button has an
* onClick method that sets the MainActivity's mediaPlayer to a new song
 */
package com.example.ics3u;

import android.content.Context;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MusicSelection extends AppCompatActivity {

    Button button1;
    Button button2;
    Button button3;
    Button button4;
    Button button5;
    public static Context context;

    //setup the page and add onClick functions to the relevant buttons
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_selection);

        button1 = (Button) findViewById(R.id.songButton1);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.mediaPlayer.stop();
                MainActivity.mediaPlayer = MediaPlayer.create(context, R.raw.domestic_no_kanojo_op);
                MainActivity.mediaPlayer.setLooping(true);
                MainActivity.mediaPlayer.start();
            }
        });

        button2 = (Button) findViewById(R.id.songButton2);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.mediaPlayer.stop();
                MainActivity.mediaPlayer = MediaPlayer.create(context, R.raw.drinking_problem_song);
                MainActivity.mediaPlayer.setLooping(true);
                MainActivity.mediaPlayer.start();
            }
        });

        button3 = (Button) findViewById(R.id.songButton3);
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.mediaPlayer.stop();
                MainActivity.mediaPlayer = MediaPlayer.create(context, R.raw.il_vento_doro);
                MainActivity.mediaPlayer.setLooping(true);
                MainActivity.mediaPlayer.start();
            }
        });

        button4 = (Button) findViewById(R.id.songButton4);
        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.mediaPlayer.stop();
                MainActivity.mediaPlayer = MediaPlayer.create(context, R.raw.let_it_toad);
                MainActivity.mediaPlayer.setLooping(true);
                MainActivity.mediaPlayer.start();
            }
        });

        //add onClick method to stop the mediaPlayer
        button5 = (Button) findViewById(R.id.pauseButton);
        button5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.mediaPlayer.stop();
            }
        });
    }
}