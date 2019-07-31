package com.stabus.app.Database;

import android.content.ContentValues;
import android.database.Cursor;
import android.util.Log;
import android.view.View;

import com.stabus.app.Database.DBContract.*;
import com.stabus.app.Model.MProdukRelasi;

import java.util.ArrayList;
import java.util.List;

public class DBMProduk {

    private View view;
    private DBAdapter db;
    private Cursor cursor;
    private DBMBahan dbmBahan;

    public DBMProduk(View view) {
        this.view = view;
        db = new DBAdapter(view.getContext());

    }

    public void getAllProduk(List<MProdukRelasi> produkRelasiList){
        db.openDB();
        produkRelasiList.clear();
        //get produk
        for (int i = 0; i < getProduk().size(); i++){
            MProdukRelasi produkRelasi = getProduk().get(i);
            int id_produk = produkRelasi.getFk_id_produk();
            String nama_produk = produkRelasi.getNama_produk();
            //get porsi of product
            String query = "SELECT DISTINCT "+ProdukBKEntry.COLS_JUMLAH_PRODUK+","+ProdukBKEntry.COLS_SATUAN_PRODUK
                    +" FROM "+ProdukBKEntry.TABLE_PRODUK_BAHAN+" WHERE "
                    +ProdukBKEntry.COLS_FK_ID_PRODUK+" = "+id_produk;
            cursor = db.getRawQuery(query,null);
            while (cursor.moveToNext()){
                int jumlah = cursor.getInt(0); //porsi
                String satuan = cursor.getString(1); //satuan porsi
                produkRelasi.setFk_id_produk(id_produk);
                produkRelasi.setNama_produk(nama_produk);
                produkRelasi.setJumlah(jumlah);
                produkRelasi.setSatuan(satuan);
                produkRelasiList.add(0,produkRelasi);
            }
        }
        db.closeDB();
    }
    public void getAllRelasi(List<MProdukRelasi> produkRelasiList, int id_produk, int jumlah_produk){
        db.openDB();
        produkRelasiList.clear();
//        "SELECT * FROM ProdukBahan WHERE fk_id_produk = 9 AND jumlah = 575";
        String s = "SELECT * FROM "+ProdukBKEntry.TABLE_PRODUK_BAHAN+" WHERE "+ProdukBKEntry.COLS_FK_ID_PRODUK+" =? AND "+
                ProdukBKEntry.COLS_JUMLAH_PRODUK+" =?";
        cursor = db.getRawQuery(s,new String[]{String.valueOf(id_produk),String.valueOf(jumlah_produk)});

        while (cursor.moveToNext()){
            int id_relasi = cursor.getInt(0);
            int fk_id_produk = cursor.getInt(1);
            int jumlah = cursor.getInt(2);
            String satuan = cursor.getString(3);
            int fk_id_bahan = cursor.getInt(4);
            int jumah_dg = cursor.getInt(5);
            String satuan_dg = cursor.getString(6);

            dbmBahan = new DBMBahan(view);
            String nama_bahan = dbmBahan.bahanBaku("",fk_id_bahan).getNama_bahan();
//            String nama_produk = produk("",fk_id_produk).getNama_produk();

            MProdukRelasi produkRelasi = new MProdukRelasi();
            produkRelasi.setId_relasi(id_relasi);
            produkRelasi.setFk_id_produk(fk_id_produk);
            produkRelasi.setJumlah(jumlah);
            produkRelasi.setSatuan(satuan);
            produkRelasi.setFk_id_bahan(fk_id_bahan);
            produkRelasi.setIsi_digunakan(jumah_dg);
            produkRelasi.setSatuan_digunakan(satuan_dg);

//            produkRelasi.setNama_produk(nama_produk);
            produkRelasi.setNama_bahan(nama_bahan);

            produkRelasiList.add(produkRelasi);
        }

        db.closeDB();
    }
    private List<MProdukRelasi> getProduk(){
        db.openDB();
        List<MProdukRelasi> getProduk = new ArrayList<>();
        getProduk.clear();
        cursor = db.getAllData(ProdukEntry.TABLE_PRODUK);

        while (cursor.moveToNext()){
            int id = cursor.getInt(0);
            String nama = cursor.getString(1);

            MProdukRelasi produkRelasi = new MProdukRelasi();
            produkRelasi.setFk_id_produk(id);
            produkRelasi.setNama_produk(nama);
            getProduk.add(produkRelasi);
        }

        return  getProduk;
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
    public ArrayList<String> getIdBahan(int id_produk){
        ArrayList<String> nama = new ArrayList<>();
        db.openDB();
        String name = "SELECT "+ProdukBKEntry.TABLE_PRODUK_BAHAN+"."+ProdukBKEntry.COLS_FK_ID_BAHAN+" FROM "
                +ProdukEntry.TABLE_PRODUK+" INNER JOIN "+ProdukBKEntry.TABLE_PRODUK_BAHAN+" ON "
                +ProdukEntry.TABLE_PRODUK+"."+ProdukEntry.COLS_ID_PRODUK+" = "
                +ProdukBKEntry.TABLE_PRODUK_BAHAN+"."+ProdukBKEntry.COLS_FK_ID_PRODUK
                +" WHERE "+ProdukEntry.TABLE_PRODUK+"."+ ProdukEntry.COLS_ID_PRODUK+" =?";
        Cursor cek = db.getRawQuery(name,new String[]{String.valueOf(id_produk)});
        while (cek.moveToNext()){
            int nama_bahan = cek.getInt(0);
            nama.add(String.valueOf(nama_bahan));
        }
        return nama;
    }
    public ArrayList<String> getNameBahan(int id_produk){
        db.openDB();
        ArrayList IdBahan = getIdBahan(id_produk);
        ArrayList<String> nama_bahan = new ArrayList<>();
        Log.d("Size", ""+IdBahan.size());
        for (int i = 0; i < IdBahan.size(); i++){
            String query = "SELECT "+BahanBakuEntry.COLS_NAMA_BAHAN+" FROM "
                    +BahanBakuEntry.TABLE_BAHANBAKU+" WHERE "+BahanBakuEntry.COLS_ID_BAHAN+" =?";
            Cursor cek = db.getRawQuery(query,new String[]{String.valueOf(IdBahan.get(i))});
            while (cek.moveToNext()){
                String aa = cek.getString(0);
                nama_bahan.add(aa);
            }
        }

        db.closeDB();
        return nama_bahan;
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
        MProdukRelasi produkRelasi = null;
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
            produkRelasi = new MProdukRelasi(id, nama) ;
        }

        db.closeDB();
        return produkRelasi;

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
    public boolean cekJumlah(String textNama, int jumlah, String satuan){
        db.openDB();
        String query = "SELECT "+ProdukBKEntry.COLS_JUMLAH_PRODUK+" , "+ProdukBKEntry.COLS_SATUAN_PRODUK+" FROM "
                +ProdukBKEntry.TABLE_PRODUK_BAHAN+" WHERE "
                +ProdukBKEntry.COLS_FK_ID_PRODUK+" = " +
                "(SELECT "+ProdukEntry.COLS_ID_PRODUK+" FROM "+ProdukEntry.TABLE_PRODUK+" WHERE " +
                ProdukEntry.COLS_NAMA_PRODUK+" =? ) AND "
                +ProdukBKEntry.COLS_JUMLAH_PRODUK+" =? AND "
                +ProdukBKEntry.COLS_SATUAN_PRODUK+" =?";
        boolean cek = db.cekSameData(query, new String[]{textNama, String.valueOf(jumlah), satuan});

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
