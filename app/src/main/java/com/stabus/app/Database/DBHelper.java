package com.stabus.app.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.stabus.app.Database.DBContract.*;

public class DBHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "DBStabus.db";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DBContract.CREATE_TABLE_BAHANBAKU);
        db.execSQL(DBContract.CREATE_TABLE_HARGA);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+ BahanBakuEntry.TABLE_BAHANBAKU);
        db.execSQL("DROP TABLE IF EXISTS "+HargaBKEntry.TABLE_HARGA);
        onCreate(db);
    }
}
