package com.stabus.app.Database;

import android.database.Cursor;
import android.view.View;

import com.stabus.app.Database.DBContract.*;
import com.stabus.app.Model.MProdukBahan;

import java.util.List;

public class DBMProduk {

    private View view;
    private DBAdapter db;
    private Cursor cursor;

    public DBMProduk(View view) {
        this.view = view;
        db = new DBAdapter(view.getContext());
    }

    public void getAllProduk(List<MProdukBahan> produkList){
        db.openDB();

        cursor = db.getAllData(ProdukEntry.TABLE_PRODUK);
        while (cursor.moveToNext()){
            int id = cursor.getInt(0);
            String nama = cursor.getString(1);

            MProdukBahan bahanBaku = new MProdukBahan(id, nama);
            produkList.add(0,bahanBaku);
        }


        db.closeDB();
    }
}
