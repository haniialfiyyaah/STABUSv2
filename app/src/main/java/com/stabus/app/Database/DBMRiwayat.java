package com.stabus.app.Database;

import android.content.ContentValues;
import android.database.Cursor;
import android.view.View;

import com.stabus.app.Database.DBContract.*;
import com.stabus.app.Model.MRiwayat;

import java.util.List;

public class DBMRiwayat {

    private View view;
    private DBAdapter db;
    private Cursor cursor;

    public DBMRiwayat(View view) {
        this.view = view;
        db = new DBAdapter(view.getContext());
    }

    public void getAllRiwayat(List<MRiwayat> riwayatList){
        db.openDB();
        riwayatList.clear();
        //get produk
        cursor = db.getAllData(RiwayatEntry.TABLE_RIWAYAT);
        while (cursor.moveToNext()){
            int id = cursor.getInt(0);
            String nama = cursor.getString(1);
            int jumlah = cursor.getInt(2);
            String satuan = cursor.getString(3);
            float harga_pokok = cursor.getFloat(4);
            float margin = cursor.getFloat(5);
            float harga_jual = cursor.getFloat(6);

            MRiwayat riwayat = new MRiwayat();
            riwayat.setId_riwayat(id);
            riwayat.setNama(nama);
            riwayat.setJumlah(jumlah);
            riwayat.setSatuan(satuan);
            riwayat.setHarga_pokok(harga_pokok);
            riwayat.setMargin_harga(margin);
            riwayat.setHarga_jual(harga_jual);
            riwayatList.add(0,riwayat);
        }
        db.closeDB();
    }

    public long saveRiwayat(String nama, int jumlah, String satuan, float harga_pokok, float margin, float harga_jual){
        db.openDB();

        ContentValues cv = new ContentValues();
        cv.put(RiwayatEntry.COLS_NAMA_PRODUK, nama);
        cv.put(RiwayatEntry.COLS_JUMLAH_PRODUK, jumlah);
        cv.put(RiwayatEntry.COLS_SATUAN_PRODUK, satuan);
        cv.put(RiwayatEntry.COLS_HARGA_POKOK, harga_pokok);
        cv.put(RiwayatEntry.COLS_MARGIN_HARGA, margin);
        cv.put(RiwayatEntry.COLS_HARGA_JUAL, harga_jual);

        long result = db.addData(RiwayatEntry.TABLE_RIWAYAT, cv);

        db.closeDB();
        return result;
    }

    public long deleteRiwayat(int id){
        db.openDB();

        long result = db.deleteData(RiwayatEntry.TABLE_RIWAYAT
                ,RiwayatEntry.COLS_ID_RIWAYAT+" =?",new String[]{String.valueOf(id)});

        db.closeDB();
        return result;
    }
}
