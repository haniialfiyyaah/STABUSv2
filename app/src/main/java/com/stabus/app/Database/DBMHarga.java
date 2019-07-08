package com.stabus.app.Database;

import android.content.ContentValues;
import android.database.Cursor;
import android.view.View;

import com.stabus.app.Model.MHargaBahan;

import com.stabus.app.Database.DBContract.*;

import java.util.List;

public class DBMHarga {

    View view;
    DBAdapter db;
    Cursor cursor;

    public DBMHarga(View view) {
        this.view = view;
        db = new DBAdapter(view.getContext());
    }

    public void getAllHarga(List<MHargaBahan> hargaBahanList, int idBK){
        db.openDB();

        hargaBahanList.clear();
        cursor = db.getQuery(DBContract.HargaBKEntry.TABLE_HARGA,HargaBKEntry.COLS_ID_BAHAN_HARGA+" =?",new String[]{String.valueOf(idBK)});

        while (cursor.moveToNext()){
            int id = cursor.getInt(0);
            String merk = cursor.getString(1);
            int isi = cursor.getInt(2);
            String satuan = cursor.getString(3);
            String tempat = cursor.getString(4);
            float harga = cursor.getFloat(5);
            int idBahan = cursor.getInt(6);

            MHargaBahan hargaBahan = new MHargaBahan(id,merk,isi,satuan,tempat,harga,idBahan);
            hargaBahanList.add(0,hargaBahan);
        }

        db.closeDB();
    }

    public long save(String merk, int isi, String satuan, String tempat_beli, float harga, int idBK){
        db.openDB();

        ContentValues cv = new ContentValues();
        cv.put(HargaBKEntry.COLS_MERK_HARGA, merk);
        cv.put(HargaBKEntry.COLS_JUMLAH_HARGA, isi);
        cv.put(HargaBKEntry.COLS_SATUAN_HARGA, satuan);
        cv.put(HargaBKEntry.COLS_TEMPAT_HARGA, tempat_beli);
        cv.put(HargaBKEntry.COLS_HARGA_HARGA, harga);
        cv.put(HargaBKEntry.COLS_ID_BAHAN_HARGA, idBK);

        long result = db.addData(HargaBKEntry.TABLE_HARGA, cv);

        db.closeDB();
        return result;
    }

    public MHargaBahan hargaBahan(int idBK){

        MHargaBahan hargaBahan = null;
        db.openDB();

        String query = "SELECT * FROM "
                +HargaBKEntry.TABLE_HARGA+" WHERE "
                +HargaBKEntry.COLS_ID_BAHAN_HARGA+" =?";

        cursor = db.getRawQuery(query, new String[]{String.valueOf(idBK)});

        while (cursor.moveToNext()){
            int id = cursor.getInt(0);
            String merk = cursor.getString(1);
            int isi = cursor.getInt(2);
            String satuan = cursor.getString(3);
            String tempat = cursor.getString(4);
            float harga = cursor.getFloat(5);
            int idBahan = cursor.getInt(6);

            hargaBahan = new MHargaBahan(id,merk,isi,satuan,tempat,harga,idBahan);
        }

        db.closeDB();

        return hargaBahan;
    }

    public long deleteHarga(int id){
        db.openDB();

        long result = db.deleteData(HargaBKEntry.TABLE_HARGA,HargaBKEntry.COLS_ID_HARGA+" =?",new String[]{String.valueOf(id)});

        db.closeDB();
        return result;
    }

    public boolean cekHarga(String merk,int jumlah,String satuan, String tempat, float harga, int idBK){
        db.openDB();
        cursor  = db.getQuery(HargaBKEntry.TABLE_HARGA,HargaBKEntry.COLS_MERK_HARGA+"=? AND "
                +HargaBKEntry.COLS_JUMLAH_HARGA+"=? AND "+HargaBKEntry.COLS_SATUAN_HARGA+"=? AND "
                +HargaBKEntry.COLS_TEMPAT_HARGA+"=? AND "+HargaBKEntry.COLS_HARGA_HARGA+"=? AND "
                +HargaBKEntry.COLS_ID_BAHAN_HARGA+" =? ", new String[]{merk, String.valueOf(jumlah),satuan,tempat, String.valueOf(harga), String.valueOf(idBK)});

        cursor.moveToNext();
        if (cursor.getCount()>0) return true;
        db.closeDB();
        return false;

    }

    public long deleteAll(){
        db.openDB();

        long result = db.deleteAll(HargaBKEntry.TABLE_HARGA);

        db.closeDB();
        return result;
    }

}
