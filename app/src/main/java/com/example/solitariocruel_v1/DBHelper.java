package com.example.solitariocruel_v1;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBHelper {
    // Nombre de la base de datos, y tabla asociada
    private static final String DATABASE_NAME = "SolitarioCruel";
    private static final String DATABASE_TABLE = "Scores";
    private static final int DATABASE_VERSION = 1;
    // Las constantes que representan las columnas de la tabla
    private static final String _ID = "_ID";
    private static final String JUGADOR = "JUGADOR";
    private static final String CARTASBASE= "CARTASBASE";
    private static final String TIEMPO = "TIEMPO";
    private static final String JUGADAS = "NUMJUGADAS";
    private static final String TAG = "DBHelper";
    // Este String contiene el comando SQL para la creación de la base de datos
    private static final String DATABASE_CREATE = "create table "+DATABASE_TABLE+"("+_ID+" integer primary key autoincrement, "+JUGADOR+" text not null, "+CARTASBASE+" integer not null, "+TIEMPO+" text not null, "+JUGADAS+" integer not null);";

    private final Context contexto; // Contexto de la aplicacion
    private DatabaseHelper Helper; // Clase interna para acceso a base de datos SQL
    private SQLiteDatabase db; // La base de datos SQL

    DBHelper(Context contexto) {
        this.contexto = contexto;
        Helper = new DatabaseHelper(contexto);
    }

    private static class DatabaseHelper extends SQLiteOpenHelper {
        DatabaseHelper(Context contexto) {
            super(contexto, DATABASE_NAME, null, DATABASE_VERSION);
        }
        @Override
        public void onCreate(SQLiteDatabase db) {
            try {
                Log.w(TAG, "Crear base de datos");
                db.execSQL(DATABASE_CREATE);
            }
            catch(SQLException e) {
                e.printStackTrace();
            }
        }
        @Override
        public void onUpgrade(SQLiteDatabase db, int versionAnterior, int versionNueva) {
            Log.w(TAG, "Actualizando la base de datos desde la versión "+versionAnterior+" a la versión "+versionNueva);
            db.execSQL("DROP TABLE IF EXISTS Scores");
            onCreate(db);
        }
    }

    DBHelper openDB() throws SQLException {
        db = Helper.getWritableDatabase();
        return this;
    }

    void closeDB() {
        Helper.close();
    }

    // Agrega jugador
    long addJugador(String nombre, int cartasBase, String time, int jugadas) {
        ContentValues valores = new ContentValues();
        valores.put(JUGADOR, nombre);
        valores.put(CARTASBASE, cartasBase);
        valores.put(TIEMPO, time);
        valores.put(JUGADAS, jugadas);
        return db.insert(DATABASE_TABLE, null, valores);
    }

    // Elimina el jugador con el _id dado
    boolean deleteJugadorporId(long filaId) {
        return db.delete(DATABASE_TABLE, _ID + "=" + filaId, null) > 0;
    }

    // Elimina todos los jugadores de la base de datos
    void deleteJugadores() {
        contexto.deleteDatabase(DATABASE_NAME);
    }

    // Devuelve los 10 mejores jugadores
    Cursor getTopJugadores() throws SQLException {
        Cursor c = db.query(DATABASE_TABLE, new String[] {JUGADOR, CARTASBASE, TIEMPO, JUGADAS}, null, null, null, null,
                CARTASBASE +" DESC, "+ JUGADAS +" ASC, "+"time(TIEMPO) ASC", "10");
        if (c != null) {
            c.moveToFirst();
        }
        return c;
    }

    // Elimina los peores jugadores (peor que top 10)
    void deletePeores() {

    }
}