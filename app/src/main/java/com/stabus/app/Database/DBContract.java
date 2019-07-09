package com.stabus.app.Database;

import android.provider.BaseColumns;

class DBContract {

    static final class BahanBakuEntry implements BaseColumns {
        //information TABLE
        static final String TABLE_BAHANBAKU = "BahanBaku";
        //information COLUMNS for TABLE BAHAN BAKU
        static final String COLS_ID_BAHAN = "id_bahanbaku";
        static final String COLS_NAMA_BAHAN = "nama_bahanbaku";
        static final String COLS_KATEGORI_BAHAN = "kategori";
    }

    static final class HargaBKEntry implements BaseColumns{
        //information TABLE
        static final String TABLE_HARGA = "Harga";
        //information COLUMNS for TABLE BAHAN BAKU
        static final String COLS_ID_HARGA = "id_harga";
        static final String COLS_MERK_HARGA = "merk";
        static final String COLS_JUMLAH_HARGA = "jumlah";
        static final String COLS_SATUAN_HARGA = "satuan";
        static final String COLS_TEMPAT_HARGA = "tempat_beli";
        static final String COLS_HARGA_HARGA = "harga";
        static final String COLS_ID_BAHAN_HARGA = "id_bahanbaku";
    }

    static final class ProdukEntry implements BaseColumns{
        //information TABLE
        static final String TABLE_PRODUK = "Produk";
        //information COLUMNS for TABLE BAHAN BAKU
        static final String COLS_ID_PRODUK = "id_produk";
        static final String COLS_NAMA_PRODUK = "nama";
    }

    static final class ProdukBKEntry implements BaseColumns{
        //information TABLE
        static final String TABLE_PRODUK_BAHAN = "Produk";
        //information COLUMNS for TABLE BAHAN BAKU
        static final String COLS_ID_RELASI = "id_relasi";
        static final String COLS_FK_ID_PRODUK = "fk_id_produk";
        static final String COLS_JUMLAH_PRODUK = "jumlah";
        static final String COLS_SATUAN_PRODUK = "satuan";
        static final String COLS_FK_ID_BAHAN = "fk_id_bahan";
        static final String COLS_JUMLAH_DIGUNAKAN = "jumlah";
        static final String COLS_SATUAN_DIGUNAKAN = "satuan";
    }

    //Create Table in database
    static final String CREATE_TABLE_BAHANBAKU = "CREATE TABLE "
            +BahanBakuEntry.TABLE_BAHANBAKU +"("
            +BahanBakuEntry.COLS_ID_BAHAN+" INTEGER PRIMARY KEY AUTOINCREMENT,"
            + BahanBakuEntry.COLS_KATEGORI_BAHAN+" TEXT NOT NULL,"
            + BahanBakuEntry.COLS_NAMA_BAHAN+" TEXT NOT NULL);";

    static final String CREATE_TABLE_HARGA = "CREATE TABLE "
            +HargaBKEntry.TABLE_HARGA +"("
            +HargaBKEntry.COLS_ID_HARGA+" INTEGER PRIMARY KEY AUTOINCREMENT,"
            +HargaBKEntry.COLS_MERK_HARGA+" TEXT,"
            + HargaBKEntry.COLS_JUMLAH_HARGA+" INTEGER NOT NULL,"
            + HargaBKEntry.COLS_SATUAN_HARGA+" TEXT NOT NULL,"
            + HargaBKEntry.COLS_TEMPAT_HARGA+" TEXT,"
            + HargaBKEntry.COLS_HARGA_HARGA+" BIGINT NOT NULL,"
            + HargaBKEntry.COLS_ID_BAHAN_HARGA+" INTEGER );";

    static final String CREATE_TABLE_PRODUK = "CREATE TABLE "
            +ProdukEntry.TABLE_PRODUK +"("
            +ProdukEntry.COLS_ID_PRODUK+" INTEGER PRIMARY KEY AUTOINCREMENT,"
            +ProdukEntry.COLS_NAMA_PRODUK+" TEXT NOT NULL);";

    static final String CREATE_TABLE_RELASI = "CREATE TABLE "
            +ProdukBKEntry.TABLE_PRODUK_BAHAN +"("
            +ProdukBKEntry.COLS_ID_RELASI+" INTEGER PRIMARY KEY AUTOINCREMENT,"
            +ProdukBKEntry.COLS_FK_ID_PRODUK+" INTEGER NOT NULL,"
            +ProdukBKEntry.COLS_JUMLAH_PRODUK+" INTEGER NOT NULL,"
            +ProdukBKEntry.COLS_SATUAN_PRODUK+" TEXT NOT NULL,"
            +ProdukBKEntry.COLS_FK_ID_BAHAN+" TEXT NOT NULL,"
            +ProdukBKEntry.COLS_JUMLAH_DIGUNAKAN+" INTEGER NOT NULL,"
            +ProdukBKEntry.COLS_SATUAN_DIGUNAKAN+" TEXT NOT NULL);";


}
