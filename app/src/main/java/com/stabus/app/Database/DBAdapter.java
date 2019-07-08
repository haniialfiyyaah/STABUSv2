package com.stabus.app.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;


public class DBAdapter {
    private static final String TAG = "DBAdapter";

    private Context context;
    private SQLiteDatabase db;
    private DBHelper dbHelper;

    public DBAdapter(Context context) {
        this.context = context;
        dbHelper = new DBHelper(context);
    }

    public DBAdapter openDB(){
        db = dbHelper.getWritableDatabase();
        return this;
    }

    public void closeDB(){
        dbHelper.close();
    }

    public boolean cekSameData(String query,String[] selectionArgs){
        return getQuery(query,selectionArgs).getCount() > 0;
    }

    public Cursor getQuery(String query, String[] selectionArgs){
        return db.rawQuery(query,selectionArgs);
    }

    public Cursor getAllData(String tableName, String having){
        return db.query(tableName, null, null, null,
                        null, having, null);
    }

    public long addData(String tableName, ContentValues cv){
        return db.insert(tableName,null, cv);
    }

    public long deleteData(String tableName, String whereClause, String[] id){
        return db.delete(tableName, whereClause, id);
    }

    public long deleteAll(String tableName){
        return db.delete(tableName, null, null);
    }

    public long updateData(String tableName, String whereClause, ContentValues cv){
        return db.update(tableName, cv, whereClause, null);
    }

}
