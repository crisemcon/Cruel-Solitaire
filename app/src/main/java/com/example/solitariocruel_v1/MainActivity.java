package com.example.solitariocruel_v1;

import android.content.Intent;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(savedInstanceState!=null) {
            //recuperar los valores del savedInstanceState
        }
    }

    public void clickJugar(View v) {
        MediaPlayer mp = MediaPlayer.create(this, R.raw.buttonpress);
        mp.start();
        mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            public void onCompletion(MediaPlayer mp) {
                mp.release();
            }
        });

        startActivity(new Intent(this, ConfigActivity.class));
    }

    public void clickMarcador(View v) {
        MediaPlayer mp = MediaPlayer.create(this, R.raw.buttonpress);
        mp.start();
        mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            public void onCompletion(MediaPlayer mp) {
                mp.release();
            }
        });

        startActivity(new Intent(this, MarcadorActivity.class));
    }

    public void clickInst(View v) {
        MediaPlayer mp = MediaPlayer.create(this, R.raw.buttonpress);
        mp.start();
        mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            public void onCompletion(MediaPlayer mp) {
                mp.release();
            }
        });

        startActivity(new Intent(this, InstrucActivity.class));
    }

    public void clickCred(View v) {
        MediaPlayer mp = MediaPlayer.create(this, R.raw.buttonpress);
        mp.start();
        mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            public void onCompletion(MediaPlayer mp) {
                mp.release();
            }
        });

        startActivity(new Intent(this, CreditsActivity.class));
    }
}