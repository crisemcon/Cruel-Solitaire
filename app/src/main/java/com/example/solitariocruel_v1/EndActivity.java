package com.example.solitariocruel_v1;

import android.content.Intent;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import org.w3c.dom.Text;

public class EndActivity extends AppCompatActivity {
    TextView textofin1, textonombre, textofin2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end);

        textofin1 = (TextView) findViewById(R.id.textoFin1);
        textonombre = (TextView) findViewById(R.id.textonombre);
        textofin2 = (TextView) findViewById(R.id.textoFin2);
        String mensaje1 = getIntent().getStringExtra("mensaje1");
        String nombre = getIntent().getStringExtra("nombre");
        String mensaje2 = getIntent().getStringExtra("mensaje2");
        textofin1.setText(mensaje1);
        textonombre.setText(nombre);
        textofin2.setText(mensaje2);
    }

    void clickMenu(View v) {
        MediaPlayer mp = MediaPlayer.create(this, R.raw.buttonpress);
        mp.start();
        mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            public void onCompletion(MediaPlayer mp) {
                mp.release();
            }
        });
        finish();
    }

    void clickMarcador(View v) {
        MediaPlayer mp = MediaPlayer.create(this, R.raw.buttonpress);
        mp.start();
        mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            public void onCompletion(MediaPlayer mp) {
                mp.release();
            }
        });
        startActivity(new Intent(this, MarcadorActivity.class));
        finish();
    }
}
