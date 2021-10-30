package com.example.solitariocruel_v1;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import org.w3c.dom.Text;

public class MarcadorActivity extends AppCompatActivity {
    DBHelper db;
    TextView nombre1, nombre2, nombre3, nombre4, nombre5, nombre6, nombre7, nombre8, nombre9, nombre10;
    TextView cartas1, cartas2, cartas3, cartas4, cartas5, cartas6, cartas7, cartas8, cartas9, cartas10;
    TextView jugadas1, jugadas2, jugadas3, jugadas4, jugadas5, jugadas6, jugadas7, jugadas8, jugadas9, jugadas10;
    TextView time1, time2, time3, time4, time5, time6, time7, time8, time9, time10;
    String tabla[][];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_marcador);
        db = new DBHelper(this);
        tabla = new String[10][4];

        getViews();
        agregaInicial();

        setTopPlayers();
    }

    //EN LAYOUT: buscar cómo hacer todos los cuadros de mismo tamaño. Eliminar texto (hacerlo "").


    public void getViews() {
        nombre1 = (TextView) findViewById(R.id.name1);
        nombre2 = (TextView) findViewById(R.id.name2);
        nombre3 = (TextView) findViewById(R.id.name3);
        nombre4 = (TextView) findViewById(R.id.name4);
        nombre5 = (TextView) findViewById(R.id.name5);
        nombre6 = (TextView) findViewById(R.id.name6);
        nombre7 = (TextView) findViewById(R.id.name7);
        nombre8 = (TextView) findViewById(R.id.name8);
        nombre9 = (TextView) findViewById(R.id.name9);
        nombre10 = (TextView) findViewById(R.id.name10);
        cartas1 = (TextView) findViewById(R.id.cartasbase1);
        cartas2 = (TextView) findViewById(R.id.cartasbase2);
        cartas3 = (TextView) findViewById(R.id.cartasbase3);
        cartas4 = (TextView) findViewById(R.id.cartasbase4);
        cartas5 = (TextView) findViewById(R.id.cartasbase5);
        cartas6 = (TextView) findViewById(R.id.cartasbase6);
        cartas7 = (TextView) findViewById(R.id.cartasbase7);
        cartas8 = (TextView) findViewById(R.id.cartasbase8);
        cartas9 = (TextView) findViewById(R.id.cartasbase9);
        cartas10 = (TextView) findViewById(R.id.cartasbase10);
        jugadas1 = (TextView) findViewById(R.id.jugadas1);
        jugadas2 = (TextView) findViewById(R.id.jugadas2);
        jugadas3 = (TextView) findViewById(R.id.jugadas3);
        jugadas4 = (TextView) findViewById(R.id.jugadas4);
        jugadas5 = (TextView) findViewById(R.id.jugadas5);
        jugadas6 = (TextView) findViewById(R.id.jugadas6);
        jugadas7 = (TextView) findViewById(R.id.jugadas7);
        jugadas8 = (TextView) findViewById(R.id.jugadas8);
        jugadas9 = (TextView) findViewById(R.id.jugadas9);
        jugadas10 = (TextView) findViewById(R.id.jugadas10);
        time1 = (TextView) findViewById(R.id.tiempo1);
        time2 = (TextView) findViewById(R.id.tiempo2);
        time3 = (TextView) findViewById(R.id.tiempo3);
        time4 = (TextView) findViewById(R.id.tiempo4);
        time5 = (TextView) findViewById(R.id.tiempo5);
        time6 = (TextView) findViewById(R.id.tiempo6);
        time7 = (TextView) findViewById(R.id.tiempo7);
        time8 = (TextView) findViewById(R.id.tiempo8);
        time9 = (TextView) findViewById(R.id.tiempo9);
        time10 = (TextView) findViewById(R.id.tiempo10);
    }

    //Se deben crear entradas vacías primero? O sea ingresar 10 puntajes nulos a la DB
    public void agregaInicial() {
        db.openDB();
        for(int i=0; i<10; i++) {
            db.addJugador("Anónimo", 0, "00:00", 0);
        }
        db.closeDB();
    }

    public void getPlayers() {
        int i=0;
        db.openDB();
        Cursor c = db.getTopJugadores();
        if (c.moveToFirst()) {
            do {
                tabla[i][0]= c.getString(0); //nombre
                tabla[i][1]= c.getString(1); //cartasbase
                tabla[i][2]= c.getString(3); //jugadas
                tabla[i][3]= c.getString(2); //tiempo
                i++;
            } while(c.moveToNext());
        }
        else {
            //DisplayToast("Base de datos Contactos está vacía");
        }
        c.close();
        db.closeDB();
    }

    public void setTopPlayers() {
        getPlayers();
        nombre1.setText(tabla[0][0]);
        cartas1.setText(tabla[0][1]);
        jugadas1.setText(tabla[0][2]);
        time1.setText(tabla[0][3]);
        nombre2.setText(tabla[1][0]);
        cartas2.setText(tabla[1][1]);
        jugadas2.setText(tabla[1][2]);
        time2.setText(tabla[1][3]);
        nombre3.setText(tabla[2][0]);
        cartas3.setText(tabla[2][1]);
        jugadas3.setText(tabla[2][2]);
        time3.setText(tabla[2][3]);
        nombre4.setText(tabla[3][0]);
        cartas4.setText(tabla[3][1]);
        jugadas4.setText(tabla[3][2]);
        time4.setText(tabla[3][3]);
        nombre5.setText(tabla[4][0]);
        cartas5.setText(tabla[4][1]);
        jugadas5.setText(tabla[4][2]);
        time5.setText(tabla[4][3]);
        nombre6.setText(tabla[5][0]);
        cartas6.setText(tabla[5][1]);
        jugadas6.setText(tabla[5][2]);
        time6.setText(tabla[5][3]);
        nombre7.setText(tabla[6][0]);
        cartas7.setText(tabla[6][1]);
        jugadas7.setText(tabla[6][2]);
        time7.setText(tabla[6][3]);
        nombre8.setText(tabla[7][0]);
        cartas8.setText(tabla[7][1]);
        jugadas8.setText(tabla[7][2]);
        time8.setText(tabla[7][3]);
        nombre9.setText(tabla[8][0]);
        cartas9.setText(tabla[8][1]);
        jugadas9.setText(tabla[8][2]);
        time9.setText(tabla[8][3]);
        nombre10.setText(tabla[9][0]);
        cartas10.setText(tabla[9][1]);
        jugadas10.setText(tabla[9][2]);
        time10.setText(tabla[9][3]);
    }

    public void volver(View v) {
        Intent i = new Intent();
        finish();
    }
}
