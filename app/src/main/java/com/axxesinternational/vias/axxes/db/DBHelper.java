package com.axxesinternational.vias.axxes.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.util.Log;

/**
 * Created by Miguel on 26/01/15.
 */
public class DBHelper extends SQLiteOpenHelper {

    private static final String TAG = DBHelper.class.getSimpleName();

    public static final String DB_NAME = "axxes.db";
    public static final int DB_VERSION = 1;

    public static final String TABLE_PLANO = "plano_actual";
    public static final String C_ID_PLANO = BaseColumns._ID;
    public static final String C_FOLIO_PLANO = "folio";
    public static final String C_CODB_PLANO = "codb";
    public static final String C_NOMBRE_PLANO = "nombre";
    public static final String C_ULT_MANT_PLANO = "ult_mant";
    public static final String C_STATUS_PLANO = "status";
    public static final String C_UBICADO_PLANO = "ubicado";


    public static final int C_ID_PLANO_In = 0;
    public static final int C_FOLIO_PLANO_In = 1;
    public static final int C_CODB_PLANO_In = 2;
    public static final int C_NOMBRE_PLANO_In = 3;
    public static final int C_ULT_MANT_PLANO_In = 4;
    public static final int C_STATUS_PLANO_In = 5;
    public static final int C_UBICADO_PLANO_In = 6;



    public DBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String sql = "create table " + TABLE_PLANO + " (" + C_ID_PLANO + " int primary key, "
                + C_FOLIO_PLANO + " text null, " + C_CODB_PLANO + " text, " + C_NOMBRE_PLANO + " text null, "
                + C_ULT_MANT_PLANO + " text null, " + C_STATUS_PLANO + " text null, " + C_UBICADO_PLANO
                + " text null)";

        db.execSQL(sql);
        Log.d(TAG, sql);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("drop table if exists " + TABLE_PLANO); // drops the old database table
        Log.d(TAG, "onUgraded");
        onCreate(db);

    }
}