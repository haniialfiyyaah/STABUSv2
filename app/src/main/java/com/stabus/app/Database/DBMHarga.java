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

    public void getAllHarga(List<MHargaBahan> hargaBahanList){
        db.openDB();

        hargaBahanList.clear();
        cursor = db.getAllData(DBContract.HargaBKEntry.TABLE_HARGA, null);

        while (cursor.moveToNext()){

            String merk = cursor.getString(0);
            int isi = cursor.getInt(1);
            String satuan = cursor.getString(2);
            String tempat = cursor.getString(3);
            float harga = cursor.getFloat(4);
            int id = cursor.getInt(5);

            MHargaBahan hargaBahan = new MHargaBahan(merk,isi,satuan,tempat,harga,id);
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

        long result = db.addData(DBContract.BahanBakuEntry.TABLE_BAHANBAKU, cv);

        db.closeDB();
        return result;
    }


}
