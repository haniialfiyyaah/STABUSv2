package com.stabus.app.Database;

import android.content.ContentValues;
import android.database.Cursor;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.stabus.app.Database.DBContract.*;
import com.stabus.app.Model.CollectBahanCRUD;
import com.stabus.app.Model.MBahanBaku;
import com.stabus.app.Model.MProdukRelasi;
import com.stabus.app.Produk;

import java.util.ArrayList;
import java.util.List;

public class DBMProduk {

    private View view;
    private DBAdapter db;
    private Cursor cursor;

    CollectBahanCRUD crud;
    DBMBahan dbmBahan;

    public DBMProduk(View view) {
        this.view = view;
        db = new DBAdapter(view.getContext());

    }

    private List<MProdukRelasi> getProduk(){
        db.openDB();
        List<MProdukRelasi> getProduk = new ArrayList<>();
        getProduk.clear();
        cursor = db.getAllData(ProdukEntry.TABLE_PRODUK);

        while (cursor.moveToNext()){
            int id = cursor.getInt(0);
            String nama = cursor.getString(1);

            MProdukRelasi bahanBaku = new MProdukRelasi();
            bahanBaku.setId_produk(id);
            bahanBaku.setNama(nama);
            getProduk.add(bahanBaku);
        }

        return  getProduk;
    }
    public void getAllProduk(List<MProdukRelasi> produkRelasiList){
        db.openDB();
        produkRelasiList.clear();

        //get produk
        for (int i = 0; i < getProduk().size(); i++){
            MProdukRelasi produk = getProduk().get(i);
            int id_produk = produk.getId_produk();
            String nama_produk = produk.getNama();
            //get porsi of product
            String query = "SELECT DISTINCT "+ProdukBKEntry.COLS_JUMLAH_PRODUK+","+ProdukBKEntry.COLS_SATUAN_PRODUK
                    +" FROM "+ProdukBKEntry.TABLE_PRODUK_BAHAN+" WHERE "
                    +ProdukBKEntry.COLS_FK_ID_PRODUK+" = "+id_produk;
            cursor = db.getRawQuery(query,null);
            while (cursor.moveToNext()){
                int jumlah = cursor.getInt(0); //porsi
                String satuan = cursor.getString(1); //satuan porsi

                MProdukRelasi bahanBaku = new MProdukRelasi();
                bahanBaku.setId_produk(id_produk);
                bahanBaku.setNama(nama_produk);
                bahanBaku.setJumlah(jumlah);
                bahanBaku.setSatuan(satuan);

                produkRelasiList.add(0,bahanBaku);
            }

        }
        db.closeDB();
    }

    public ArrayList<String> getAllNamaProduk(){
        db.openDB();
        ArrayList<String> daftarProduk = new ArrayList<>();

        String query = "SELECT "+ProdukEntry.COLS_NAMA_PRODUK +" FROM "+ProdukEntry.TABLE_PRODUK+" ORDER BY "
                +ProdukEntry.COLS_ID_PRODUK+" DESC";
        cursor = db.getRawQuery(query,null);
        while (cursor.moveToNext()){
            String nama_produk = cursor.getString(0); //porsi
            daftarProduk.add(nama_produk);
        }

        db.closeDB();
        return daftarProduk;
    }

    public void getAllRelasi(List<MBahanBaku> produkRelasiList, int id_produk, int jumlah_produk){
        db.openDB();

        produkRelasiList.clear();
//        "SELECT * FROM ProdukBahan WHERE fk_id_produk = 9 AND jumlah = 575";
        String s = "SELECT * FROM "+ProdukBKEntry.TABLE_PRODUK_BAHAN+" WHERE "+ProdukBKEntry.COLS_FK_ID_PRODUK+" =? AND "+
        ProdukBKEntry.COLS_JUMLAH_PRODUK+" =?";
        cursor = db.getRawQuery(s,new String[]{String.valueOf(id_produk),String.valueOf(jumlah_produk)});

        while (cursor.moveToNext()){
            //int id_relasi = cursor.getInt(0);
            int fk_id_produk = cursor.getInt(1);
            //int jumlah = cursor.getInt(2);
            //String satuan = cursor.getString(3);
            int fk_id_bahan = cursor.getInt(4);
            int jumah_dg = cursor.getInt(5);
            String satuan_dg = cursor.getString(6);
            dbmBahan = new DBMBahan(view);
            String nama_bahan = dbmBahan.bahanBaku("",fk_id_bahan).getNama_bahan();

            MBahanBaku bahanBaku = new MBahanBaku();
            bahanBaku.setId(fk_id_bahan);
            bahanBaku.setJumlah_dg(jumah_dg);
            bahanBaku.setSatuan_dg(satuan_dg);
            bahanBaku.setNama_bahan(nama_bahan);
            //MProdukRelasi bahanBaku = new MProdukRelasi(fk_id_produk,nama_produk,id_relasi,fk_id_produk,jumlah,satuan,fk_id_bahan,jumah_dg,satuan_dg);
            produkRelasiList.add(bahanBaku);
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

        Log.d("Value","Nama id_produk "+fk_id_poduk+" jumlah "+jumlah+" satuan "+satuan+" fk_id_bahan "+fk_id_bahan+" jumlah digunakan "+jumlah_digunakan+" satuan digunakan "+satuan_digunakan);

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
    public long deleteRelasi(int id, int jumlah){
        db.openDB();

        long result = db.deleteData(ProdukBKEntry.TABLE_PRODUK_BAHAN,ProdukBKEntry.COLS_FK_ID_PRODUK+" =? AND "
                                    +ProdukBKEntry.COLS_JUMLAH_PRODUK+" =?"
                ,new String[]{String.valueOf(id), String.valueOf(jumlah)});

        db.closeDB();
        return result;
    }

    public long ubahProduk(int id, String nama){
        db.openDB();
        ContentValues cv = new ContentValues();
        cv.put(ProdukEntry.COLS_NAMA_PRODUK, nama);

        long result = db.updateData(ProdukEntry.TABLE_PRODUK ,cv,ProdukEntry.COLS_ID_PRODUK+" =?",new String[]{String.valueOf(id)});

        db.closeDB();

        return result;
    }

    public MProdukRelasi produk(String textNama, int idBahan){

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

            produkBahan = new MProdukRelasi(id, nama) ;
        }

        db.closeDB();

        return produkBahan;

    }
    public MProdukRelasi relasi(int id_produk){

        MProdukRelasi produkBahan = null;
        db.openDB();

        cursor=db.getQuery(ProdukBKEntry.TABLE_PRODUK_BAHAN,ProdukBKEntry.COLS_FK_ID_PRODUK+" =?",new String[]{String.valueOf(id_produk)});

        //cursor = db.getRawQuery(query, new String[]{textNama});

        while (cursor.moveToNext()){
            int id_relasi = cursor.getInt(0);
            int fk_id_produk = cursor.getInt(1);
            int jumlah = cursor.getInt(2);
            String satuan = cursor.getString(3);
            int fk_id_bahan = cursor.getInt(4);
            int jumah_dg = cursor.getInt(5);
            String satuan_dg = cursor.getString(6);

            String nama_produk = produk("",fk_id_produk).getNama();

            produkBahan = new MProdukRelasi(id_produk,nama_produk,id_relasi,fk_id_produk,jumlah,satuan,fk_id_bahan,jumah_dg,satuan_dg);

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
    public boolean cekFKProduk(int fk_id_produk){
        db.openDB();
        String query = "SELECT * FROM "
                + ProdukBKEntry.TABLE_PRODUK_BAHAN +" WHERE "
                +ProdukBKEntry.COLS_FK_ID_PRODUK+" =?";
        boolean cek = db.cekSameData(query, new String[]{String.valueOf(fk_id_produk)});

        db.closeDB();

        return cek;

    }



}
