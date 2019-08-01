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
        static final String TABLE_PRODUK_BAHAN = "ProdukBahan";
        //information COLUMNS for TABLE BAHAN BAKU
        static final String COLS_ID_RELASI = "id_relasi";
        static final String COLS_FK_ID_PRODUK = "fk_id_produk";
        static final String COLS_JUMLAH_PRODUK = "jumlah";
        static final String COLS_SATUAN_PRODUK = "satuan";
        static final String COLS_FK_ID_BAHAN = "fk_id_bahan";
        static final String COLS_JUMLAH_DIGUNAKAN = "jumlah_dg";
        static final String COLS_SATUAN_DIGUNAKAN = "satuan_dg";
    }

    static final class RiwayatEntry implements BaseColumns{
        static final String TABLE_RIWAYAT = "Riwayat";
        static final String COLS_ID_RIWAYAT = "id_riwayat";
        static final String COLS_NAMA_PRODUK = "nama_produk";
        static final String COLS_JUMLAH_PRODUK ="jumlah_produk";
        static final String COLS_SATUAN_PRODUK ="satuan_produk";
        static final String COLS_HARGA_POKOK ="harga_pokok";
        static final String COLS_MARGIN_HARGA ="margin_harga";
        static final String COLS_HARGA_JUAL ="harga_jual";

        static final String COLS_TENAGA_KERJA ="tenaga_kerja";
        static final String COLS_BIAYA_OPERASIONAL ="biaya_op";
        static final String COLS_RESIKO = "resiko";
        static final String COLS_KEUNTUNGAN ="keuntungan";
        static final String COLS_MARKETING ="marketing";

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
            +ProdukBKEntry.COLS_FK_ID_BAHAN+" INTEGER NOT NULL,"
            +ProdukBKEntry.COLS_JUMLAH_DIGUNAKAN+" INTEGER NOT NULL,"
            +ProdukBKEntry.COLS_SATUAN_DIGUNAKAN+" TEXT NOT NULL);";

    static final String CREATE_TABLE_RIWAYAT = "CREATE TABLE "
            +RiwayatEntry.TABLE_RIWAYAT+"("
            +RiwayatEntry.COLS_ID_RIWAYAT+" INTEGER PRIMARY KEY AUTOINCREMENT,"
            +RiwayatEntry.COLS_NAMA_PRODUK+" TEXT NOT NULL,"
            +RiwayatEntry.COLS_JUMLAH_PRODUK+" INTEGER NOT NULL,"
            +RiwayatEntry.COLS_SATUAN_PRODUK+" TEXT NOT NULL,"
            +RiwayatEntry.COLS_HARGA_POKOK+" BIGINT NOT NULL,"
            +RiwayatEntry.COLS_MARGIN_HARGA+" BIGINT NOT NULL,"
            +RiwayatEntry.COLS_HARGA_JUAL+" BIGINT NOT NULL,"
            //use later
            +RiwayatEntry.COLS_TENAGA_KERJA+" INTEGER,"
            +RiwayatEntry.COLS_BIAYA_OPERASIONAL+" INTEGER,"
            +RiwayatEntry.COLS_RESIKO+" INTEGER,"
            +RiwayatEntry.COLS_KEUNTUNGAN+" INTEGER ,"
            +RiwayatEntry.COLS_MARKETING+" INTEGER);";

    /*kalkulator
    harga
    komponen 5

    nama produk
    jumlah
    satuan
    hpp/hargatotal
    margin
    hargajual

    hargajualsatuan

    */
}
