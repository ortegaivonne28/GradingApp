package com.example.ivonneortega.nueva;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;

import java.util.ArrayList;
import java.util.List;

public class BaseDatos extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;

    private static final String DATABASE_NAME = "bd_n",
            TABLA_N = "tabla_n",
            ID = "id",
            TITU_N = "titulo_n",
            TEX_N = "text_n";

    public BaseDatos(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLA_N + "(" + ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + TITU_N + " TEXT," + TEX_N + " TEXT)");
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLA_N);

        onCreate(db);
    }

    public void crear_nota(Nota nota) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(TITU_N, nota.obtener_titulo());
        values.put(TEX_N, nota.obtener_texto());

        db.insert(TABLA_N, null, values);
        db.close();
    }

    public Nota obtener_nota(int id) {
        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.query(TABLA_N, new String[] { ID, TITU_N, TEX_N }, ID + "=?", new String[] { String.valueOf(id) }, null, null, null, null );

        if (cursor != null)
            cursor.moveToFirst();

        Nota nota = new Nota(Integer.parseInt(cursor.getString(0)), cursor.getString(1), cursor.getString(2));
        db.close();
        cursor.close();
        return nota;
    }

    public void borrar_nota(Nota nota) {
        SQLiteDatabase db = getWritableDatabase();
        db.delete(TABLA_N, ID + "=?", new String[] { String.valueOf(nota.getId()) });
        db.close();
    }

    public int numero_notas() {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLA_N, null);
        int count = cursor.getCount();
        db.close();
        cursor.close();

        return count;
    }

    public int update_nota(Nota nota) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(TITU_N, nota.obtener_titulo());
        values.put(TEX_N, nota.obtener_texto());

        int rowsAffected = db.update(TABLA_N, values, ID + "=?", new String[] { String.valueOf(nota.getId()) });
        db.close();

        return rowsAffected;
    }

    public List<Nota> obtener_todas() {
        List<Nota> notas = new ArrayList<Nota>();

        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLA_N, null);

        if (cursor.moveToFirst()) {
            do {
                notas.add(new Nota(Integer.parseInt(cursor.getString(0)), cursor.getString(1), cursor.getString(2)));
            }
            while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return notas;
    }
}
