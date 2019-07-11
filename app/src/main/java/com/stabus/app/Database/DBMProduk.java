package com.stabus.app.Database;

import android.content.ContentValues;
import android.database.Cursor;
import android.view.View;

import com.stabus.app.Database.DBContract.*;
import com.stabus.app.Model.MProdukRelasi;

import java.util.List;

public class DBMProduk {

    private View view;
    private DBAdapter db;
    private Cursor cursor;

    public DBMProduk(View view) {
        this.view = view;
        db = new DBAdapter(view.getContext());
    }

    public void getAllRelasi(List<MProdukRelasi> produkRelasiList){
        db.openDB();

        produkRelasiList.clear();
        cursor = db.getAllData(ProdukBKEntry.TABLE_PRODUK_BAHAN);

        while (cursor.moveToNext()){
            int id_relasi = cursor.getInt(0);
            int fk_id_produk = cursor.getInt(1);
            int jumlah = cursor.getInt(2);
            String satuan = cursor.getString(3);
            int fk_id_bahan = cursor.getInt(4);
            int jumah_dg = cursor.getInt(5);
            String satuan_dg = cursor.getString(6);

            String nama_produk = namaProduk(fk_id_produk);

            MProdukRelasi bahanBaku = new MProdukRelasi(nama_produk,id_relasi,fk_id_produk,jumlah,satuan,fk_id_bahan,jumah_dg,satuan_dg);
            produkRelasiList.add(0,bahanBaku);
        }

        db.closeDB();
    }

    private String namaProduk(int fk_id_produk){
        db.openDB();
        String query = "SELECT "+ProdukEntry.COLS_NAMA_PRODUK+" FROM "+ProdukEntry.TABLE_PRODUK+" WHERE "
                +ProdukEntry.COLS_ID_PRODUK+" = "+fk_id_produk;

        Cursor cursor = db.getRawQuery(query,null);
        String namaProduk = "";
        if (cursor.moveToNext()){
            namaProduk = cursor.getString(0);
        }
        db.closeDB();

        return namaProduk;
    }

    public void getAllProduk(List<MProdukRelasi> produkList){
        db.openDB();
        cursor = db.getAllData(ProdukEntry.TABLE_PRODUK);
        while (cursor.moveToNext()){
            int id = cursor.getInt(0);
            String nama = cursor.getString(1);

            MProdukRelasi bahanBaku = new MProdukRelasi(id, nama);
            produkList.add(0,bahanBaku);
        }
        db.closeDB();
    }

    public long saveProduk(String nama){
        db.openDB();

        ContentValues cv = new ContentValues();
        cv.put(ProdukEntry.COLS_NAMA_PRODUK, nama);

        long result = db.addData(ProdukEntry.TABLE_PRODUK, cv);

        db.closeDB();
        return result;
    }

    public long saveRelasi(int fk_id_poduk, int jumlah, String satuan, int fk_id_bahan, int jumlah_digunakan, String satuan_digunakan){
        db.openDB();

        ContentValues cv = new ContentValues();
        cv.put(ProdukBKEntry.COLS_FK_ID_PRODUK, fk_id_poduk);
        cv.put(ProdukBKEntry.COLS_JUMLAH_PRODUK, jumlah);
        cv.put(ProdukBKEntry.COLS_SATUAN_PRODUK, satuan);
        cv.put(ProdukBKEntry.COLS_FK_ID_BAHAN, fk_id_bahan);
        cv.put(ProdukBKEntry.COLS_JUMLAH_DIGUNAKAN, jumlah_digunakan);
        cv.put(ProdukBKEntry.COLS_SATUAN_DIGUNAKAN, satuan_digunakan);

        long result = db.addData(ProdukBKEntry.TABLE_PRODUK_BAHAN, cv);

        db.closeDB();
        return result;
    }

    public long deleteProduk(int id){
        db.openDB();

        long result = db.deleteData(ProdukEntry.TABLE_PRODUK,ProdukEntry.COLS_ID_PRODUK+" =?",new String[]{String.valueOf(id)});

        db.closeDB();
        return result;
    }

    public MProdukRelasi produkBahan(String textNama, int idBahan){

        MProdukRelasi produkBahan = null;
        db.openDB();

        String selection;
        String args ;
        if (textNama.trim().length()>0){
            selection = ProdukEntry.COLS_NAMA_PRODUK;
            args = textNama;
        }else {
            selection = ProdukEntry.COLS_ID_PRODUK;
            args = String.valueOf(idBahan);
        }

        cursor=db.getQuery(ProdukEntry.TABLE_PRODUK,selection+" =?",new String[]{args});

        //cursor = db.getRawQuery(query, new String[]{textNama});

        while (cursor.moveToNext()){
            int id = cursor.getInt(0);
            String nama = cursor.getString(1);

            produkBahan = new MProdukRelasi(id, nama);
        }

        db.closeDB();

        return produkBahan;

    }

    //cek produk udah ada belum
    public boolean cekNama(String textNama){
        db.openDB();
        String query = "SELECT "+ProdukEntry.COLS_NAMA_PRODUK+" FROM "
                +ProdukEntry.TABLE_PRODUK+" WHERE "
                +ProdukEntry.COLS_NAMA_PRODUK+" =?";
        boolean cek = db.cekSameData(query, new String[]{textNama});

        db.closeDB();

        return cek;

    }


}
