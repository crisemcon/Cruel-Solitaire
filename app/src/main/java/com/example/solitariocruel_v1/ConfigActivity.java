package com.example.solitariocruel_v1;

import android.content.Intent;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Switch;

public class ConfigActivity extends AppCompatActivity {
    EditText nombreJugador;
    String nombre;
    Switch switchTimer, switchMusic;
    boolean music, timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_config);
        nombreJugador = (EditText) findViewById(R.id.textNombre);
        switchMusic = (Switch) findViewById(R.id.switchMusica);
        switchTimer = (Switch) findViewById(R.id.switchTimer);
    }

    public void clickJugar(View v) {
        MediaPlayer mp = MediaPlayer.create(this, R.raw.buttonpress);
        mp.start();
        mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            public void onCompletion(MediaPlayer mp) {
                mp.release();
            }
        });

        Intent i = new Intent(this, GameActivity.class);
        if(nombreJugador.getText().length()==0) {
            nombre = "Anónimo";
        }
        else {
            nombre = nombreJugador.getText().toString();
        }
        music = switchMusic.isChecked();
        timer = switchTimer.isChecked();
        i.putExtra("nombre",nombre);
        i.putExtra("timerOp",timer);
        i.putExtra("musicaOp",music);
        startActivity(i);
        finish();
    }
}
