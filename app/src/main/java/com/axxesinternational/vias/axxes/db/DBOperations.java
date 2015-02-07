package com.axxesinternational.vias.axxes.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.axxesinternational.vias.axxes.models.Plano;

import java.util.ArrayList;

/**
 * Created by Miguel on 26/01/15.
 */
public class DBOperations {

    private static final String TAG = DBOperations.class.getSimpleName();
    private DBHelper dbhelper;

    public DBOperations(Context context) {
        dbhelper = new DBHelper(context);
    }


    public void insertOrIgnore(ContentValues values) {

        Log.d(TAG, "insertOrIgnore on: " + values);
        SQLiteDatabase dataBase = dbhelper.getWritableDatabase();

        try {
            dataBase.insertWithOnConflict(DBHelper.TABLE_PLANO, null, values, SQLiteDatabase.CONFLICT_IGNORE);
        } finally {
            dataBase.close();
        }

    }


    public void deletePlanoActual() {

        Log.d(TAG, "delete plano_actual registro 1");
        SQLiteDatabase dataBase = dbhelper.getWritableDatabase();

        try {
            dataBase.delete(DBHelper.TABLE_PLANO, DBHelper.C_ID_PLANO+"=1", null);
        } finally {
            dataBase.close();
        }

    }


    public ArrayList<Plano> getPlanoActual() {
        ArrayList<Plano> plano = new ArrayList<Plano>();

        SQLiteDatabase dataBase = dbhelper.getReadableDatabase();

        //String[] args = new String[] {"1"};
        //Cursor cursor = dataBase.query(DBHelper.TABLE_PLANO, null, DBHelper.C_ID_PLANO+"=?", args, null, null, null);
        Cursor cursor = dataBase.rawQuery(" SELECT * FROM plano_actual WHERE _id=1 ", null);

        if (cursor.moveToFirst()) {
            while (cursor.isAfterLast() == false) {

                Plano miplano = new Plano();
                miplano.setHerrNombre(cursor.getString(DBHelper.C_NOMBRE_PLANO_In));
                miplano.setfUltMant(cursor.getString(DBHelper.C_ULT_MANT_PLANO_In));
                miplano.setSttHrj(cursor.getString(DBHelper.C_STATUS_PLANO_In));
                miplano.setUbicacion(cursor.getString(DBHelper.C_UBICADO_PLANO_In));

                plano.add(miplano);
                cursor.moveToNext();

            }
        }

        return plano;

    }

}
