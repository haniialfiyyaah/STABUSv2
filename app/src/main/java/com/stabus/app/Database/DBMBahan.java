package com.stabus.app.Database;

import android.content.ContentValues;
import android.database.Cursor;
import android.view.View;

import com.stabus.app.Model.MBahanBaku;
import com.stabus.app.Database.DBContract.*;

import java.util.ArrayList;
import java.util.List;

public class DBMBahan {

    View view;
    DBAdapter db;
    Cursor cursor;

    public DBMBahan(View view) {
        this.view = view;
        db = new DBAdapter(view.getContext());
    }
    //SELECT count(*) FROM harga WHERE id_bahanbaku =2
    public void getAllBahan(List<MBahanBaku> bahanBakuList){
        db.openDB();

        bahanBakuList.clear();
        cursor = db.getAllData(BahanBakuEntry.TABLE_BAHANBAKU,null);

        while (cursor.moveToNext()){
            int id = cursor.getInt(0);
            String kategori = cursor.getString(1);
            String nama = cursor.getString(2);
            int jumlah = jumlahHarga(id);
            MBahanBaku bahanBaku = new MBahanBaku(id,kategori,nama,jumlah);
            bahanBakuList.add(0,bahanBaku);
        }

        db.closeDB();
    }

    public int jumlahHarga(int id){
        db.openDB();
        String query = "SELECT COUNT(*) FROM "+HargaBKEntry.TABLE_HARGA+" WHERE "
                +HargaBKEntry.COLS_ID_BAHAN_HARGA+" = "+id;

        Cursor cursor = db.getQuery(query,null);
        cursor.moveToNext();
        int jumlah = cursor.getInt(0);
        db.closeDB();

        return jumlah;
    }

    public long save(String nama, String kategori){
        db.openDB();

        ContentValues cv = new ContentValues();
        cv.put(BahanBakuEntry.COLS_NAMA_BAHAN, nama);
        cv.put(BahanBakuEntry.COLS_KATEGORI_BAHAN, kategori);

        long result = db.addData(BahanBakuEntry.TABLE_BAHANBAKU, cv);

        db.closeDB();
        return result;
    }

    public long delete(int id){
        db.openDB();

        long result = db.deleteData(BahanBakuEntry.TABLE_BAHANBAKU,BahanBakuEntry.COLS_ID_BAHAN+" =?",new String[]{String.valueOf(id)});

        db.closeDB();
        return result;
    }

    public boolean cekNama(String textNama){
        db.openDB();
        String query = "SELECT "+BahanBakuEntry.COLS_NAMA_BAHAN+" FROM "
                +BahanBakuEntry.TABLE_BAHANBAKU+" WHERE "
                +BahanBakuEntry.COLS_NAMA_BAHAN+" =?";
        boolean cek = db.cekSameData(query, new String[]{textNama});

        db.closeDB();

        return cek;

    }
    
    public void getDataTambah(List<MBahanBaku> bahanBakuList, String textNama){
        
        db.openDB();
        
        String query = "SELECT * FROM "
                +BahanBakuEntry.TABLE_BAHANBAKU+" WHERE "
                +BahanBakuEntry.COLS_NAMA_BAHAN+" =?";
        
        db.getQuery(query, new String[]{textNama});

        while (cursor.moveToNext()){
            int id = cursor.getInt(0);
            String kategori = cursor.getString(1);
            String nama = cursor.getString(2);
            int jumlah = jumlahHarga(id);
            MBahanBaku bahanBaku = new MBahanBaku(id,kategori,nama,jumlah);
            bahanBakuList.add(0,bahanBaku);
        }
        
        db.closeDB();
        
    }
    public MBahanBaku bahanBaku(String textNama){

        MBahanBaku bahanBaku = null;
        db.openDB();

        String query = "SELECT * FROM "
                +BahanBakuEntry.TABLE_BAHANBAKU+" WHERE "
                +BahanBakuEntry.COLS_NAMA_BAHAN+" =?";

        cursor = db.getQuery(query, new String[]{textNama});

        while (cursor.moveToNext()){
            int id = cursor.getInt(0);
            String kategori = cursor.getString(1);
            String nama = cursor.getString(2);
            int jumlah = jumlahHarga(id);
            bahanBaku = new MBahanBaku(id,kategori,nama,jumlah);
        }

        db.closeDB();

        return bahanBaku;

    }


}
