package com.stabus.app.Model;

public class MRiwayat {
    private int id_riwayat;
    private String nama;
<<<<<<< HEAD
    private String jumlah_satuan;
    private float harga_jual;
=======
    private int jumlah;
    private String satuan;
    private float harga_pokok;
    private float margin_harga;
    private float harga_jual;

>>>>>>> origin/master
    private String tenaga_kerja;
    private String biaya_operasional;
    private String resiko;
    private String keuntungan;
    private String marketing;
<<<<<<< HEAD
    private float margin_harga;
=======

    private boolean isSelected =false;
    private boolean isHapus =false;


    public MRiwayat() {
    }
>>>>>>> origin/master

    public int getId_riwayat() {
        return id_riwayat;
    }

    public void setId_riwayat(int id_riwayat) {
        this.id_riwayat = id_riwayat;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

<<<<<<< HEAD
    public String getJumlah_satuan() {
        return jumlah_satuan;
    }

    public void setJumlah_satuan(String jumlah_satuan) {
        this.jumlah_satuan = jumlah_satuan;
=======
    public int getJumlah() {
        return jumlah;
    }

    public void setJumlah(int jumlah) {
        this.jumlah = jumlah;
    }

    public String getSatuan() {
        return satuan;
    }

    public void setSatuan(String satuan) {
        this.satuan = satuan;
    }

    public float getHarga_pokok() {
        return harga_pokok;
    }

    public void setHarga_pokok(float harga_pokok) {
        this.harga_pokok = harga_pokok;
    }

    public float getMargin_harga() {
        return margin_harga;
    }

    public void setMargin_harga(float margin_harga) {
        this.margin_harga = margin_harga;
>>>>>>> origin/master
    }

    public float getHarga_jual() {
        return harga_jual;
    }

    public void setHarga_jual(float harga_jual) {
        this.harga_jual = harga_jual;
    }

    public String getTenaga_kerja() {
        return tenaga_kerja;
    }

    public void setTenaga_kerja(String tenaga_kerja) {
        this.tenaga_kerja = tenaga_kerja;
    }

    public String getBiaya_operasional() {
        return biaya_operasional;
    }

    public void setBiaya_operasional(String biaya_operasional) {
        this.biaya_operasional = biaya_operasional;
    }

    public String getResiko() {
        return resiko;
    }

    public void setResiko(String resiko) {
        this.resiko = resiko;
    }

    public String getKeuntungan() {
        return keuntungan;
    }

    public void setKeuntungan(String keuntungan) {
        this.keuntungan = keuntungan;
    }

    public String getMarketing() {
        return marketing;
    }

    public void setMarketing(String marketing) {
        this.marketing = marketing;
    }

<<<<<<< HEAD
    public float getMargin_harga() {
        return margin_harga;
    }

    public void setMargin_harga(float margin_harga) {
        this.margin_harga = margin_harga;
=======
    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public boolean isHapus() {
        return isHapus;
    }

    public void setHapus(boolean hapus) {
        isHapus = hapus;
>>>>>>> origin/master
    }
}
