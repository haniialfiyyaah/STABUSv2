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
        return getRawQuery(query,selectionArgs).getCount() > 0;
    }

    public Cursor getRawQuery(String query, String[] selectionArgs){
        return db.rawQuery(query,selectionArgs);
    }

    public Cursor getQuery(String table, String selection,String[] selectionArgs){
        return db.query(table,null,selection,selectionArgs,null,null,null);
    }

    public Cursor getQueryColums(String table, String[] columns, String selection,String[] selectionArgs){
        return db.query(table,columns,selection,selectionArgs,null,null,null);
    }

    public Cursor getAllData(String tableName){
        return db.query(tableName, null, null, null,
                        null, null, null);
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

    public long updateData(String tableName, ContentValues cv, String whereClause, String[] id){
        return db.update(tableName, cv, whereClause, id);
    }

}
