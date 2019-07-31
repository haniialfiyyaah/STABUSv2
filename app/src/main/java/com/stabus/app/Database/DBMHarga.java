package com.stabus.app.Database;

import android.content.ContentValues;
import android.database.Cursor;
import android.view.View;

import com.stabus.app.Model.MHargaBahan;

import com.stabus.app.Database.DBContract.*;

import java.util.ArrayList;
import java.util.List;

public class DBMHarga {

    private View view;
    private DBAdapter db;
    private Cursor cursor;

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
    public long deleteHarga(int id){
        db.openDB();

        long result = db.deleteData(HargaBKEntry.TABLE_HARGA,HargaBKEntry.COLS_ID_HARGA+" =?",new String[]{String.valueOf(id)});

        db.closeDB();
        return result;
    }
    public long ubahHarga(int id,String merk, int isi, String satuan, String tempat_beli, float harga, int idBK){
        db.openDB();
        ContentValues cv = new ContentValues();
        cv.put(HargaBKEntry.COLS_MERK_HARGA, merk);
        cv.put(HargaBKEntry.COLS_JUMLAH_HARGA, isi);
        cv.put(HargaBKEntry.COLS_SATUAN_HARGA, satuan);
        cv.put(HargaBKEntry.COLS_TEMPAT_HARGA, tempat_beli);
        cv.put(HargaBKEntry.COLS_HARGA_HARGA, harga);
        cv.put(HargaBKEntry.COLS_ID_BAHAN_HARGA, idBK);

        long result = db.updateData(HargaBKEntry.TABLE_HARGA ,cv,HargaBKEntry.COLS_ID_HARGA+" =?",new String[]{String.valueOf(id)});

        db.closeDB();

        return result;
    }

    public boolean cekHarga(String merk,int jumlah,String satuan, String tempat, float harga, int idBK){
        db.openDB();
        cursor  = db.getQuery(HargaBKEntry.TABLE_HARGA,HargaBKEntry.COLS_MERK_HARGA+"=? AND "
                +HargaBKEntry.COLS_JUMLAH_HARGA+"=? AND "+HargaBKEntry.COLS_SATUAN_HARGA+"=? AND "
                +HargaBKEntry.COLS_TEMPAT_HARGA+"=? AND "+HargaBKEntry.COLS_HARGA_HARGA+"=? AND "
                +HargaBKEntry.COLS_ID_BAHAN_HARGA+" =? "
                , new String[]{merk, String.valueOf(jumlah),satuan,tempat, String.valueOf(harga), String.valueOf(idBK)});

        cursor.moveToNext();
        if (cursor.getCount()>0) return true;
        db.closeDB();
        return false;
    }
    public MHargaBahan hargaBahan(int idH,int idBK){

        MHargaBahan hargaBahan = null;
        db.openDB();

        String selection;
        String args ;
        if (idH>0){
            selection = HargaBKEntry.COLS_ID_HARGA;
            args = String.valueOf(idH);
        }else {
            selection = HargaBKEntry.COLS_ID_BAHAN_HARGA;
            args = String.valueOf(idBK);
        }

        cursor=db.getQuery(HargaBKEntry.TABLE_HARGA,selection+" =?",new String[]{args});

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

    public int getIDBahan(String nama){
        db.openDB();
        int aa = 0;
        String query = "SELECT "+BahanBakuEntry.COLS_ID_BAHAN
                +" FROM "+BahanBakuEntry.TABLE_BAHANBAKU+" WHERE "+ BahanBakuEntry.COLS_NAMA_BAHAN+" =?";
        Cursor cek = db.getRawQuery(query, new String[]{nama});

        while (cek.moveToNext()){
            aa = cek.getInt(0);
        }
        return aa;
    }
    public ArrayList<MHargaBahan> getSelectedHarga(String nama){
        MHargaBahan hargaBahan;
        db.openDB();
        int c = getIDBahan(nama);
        ArrayList<MHargaBahan> hargabahans = new ArrayList<MHargaBahan>();
        String query = "SELECT "+HargaBKEntry.COLS_HARGA_HARGA+","
                +HargaBKEntry.COLS_JUMLAH_HARGA+","
                +HargaBKEntry.COLS_SATUAN_HARGA+" FROM "+HargaBKEntry.TABLE_HARGA
                +" WHERE "+HargaBKEntry.COLS_ID_BAHAN_HARGA+" =?";
        Cursor cek = db.getRawQuery(query,new  String[]{String.valueOf(c)});
        while (cek.moveToNext()){
            float harga = cek.getFloat(0);
            int jumlah = cek.getInt(1);
            String satuan = cek.getString(2);
            hargaBahan = new MHargaBahan(jumlah,satuan,harga);
            hargabahans.add(hargaBahan);
        }
        db.closeDB();
        return hargabahans;
    }

    public List<String> getTempat(int idBK){
        List<String> listTempat = new ArrayList<>();
        db.openDB();

        String query = "SELECT DISTINCT "+HargaBKEntry.COLS_TEMPAT_HARGA+" FROM "
                +HargaBKEntry.TABLE_HARGA+" WHERE "
                +HargaBKEntry.COLS_ID_BAHAN_HARGA+" =? ORDER BY "
                +HargaBKEntry.COLS_TEMPAT_HARGA + " DESC ";
        cursor = db.getRawQuery(query,new String[]{String.valueOf(idBK)});
        listTempat.add("-- PILIH TEMPAT --");
        while (cursor.moveToNext()){
            String tempat = cursor.getString(0);
            listTempat.add(tempat);
        }
        db.closeDB();

        return listTempat;
    }
    public void getHarga(int idBK, String stempat,List<MHargaBahan> hargaBahanList ){

        db.openDB();
        String query = "SELECT * FROM "
                +HargaBKEntry.TABLE_HARGA+" WHERE "
                +HargaBKEntry.COLS_ID_BAHAN_HARGA+" =? AND "
                +HargaBKEntry.COLS_TEMPAT_HARGA+" =?";
        cursor = db.getRawQuery(query,new String[]{String.valueOf(idBK),stempat});
        while (cursor.moveToNext()){
            int id = cursor.getInt(0);
            String merk = cursor.getString(1);
            int isi = cursor.getInt(2);
            String satuan = cursor.getString(3);
            String tempat = cursor.getString(4);
            float harga = cursor.getFloat(5);
            int idBahan = cursor.getInt(6);

            MHargaBahan hargaBahan = new MHargaBahan(id,merk,isi,satuan,tempat,harga,idBahan);
            hargaBahanList.add(hargaBahan);
        }
        db.closeDB();
    }
    public long deleteAll(){
        db.openDB();

        long result = db.deleteAll(HargaBKEntry.TABLE_HARGA);

        db.closeDB();
        return result;
    }

}
