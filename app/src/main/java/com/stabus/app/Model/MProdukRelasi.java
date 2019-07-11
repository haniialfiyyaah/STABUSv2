package com.stabus.app.Model;

public class MProdukRelasi {

    //tabel produk
    private int id_produk;
    private String nama;

    //tabel relasi
    private int id_relasi;
    private int fk_id_produk;
    private int jumlah;
    private String satuan;
    private int fk_id_bahan;
    private int isi_digunakan;
    private String satuan_digunakan;


    private boolean isSelected =false;
    private boolean isHapus =false;

    public MProdukRelasi() {
    }

    public MProdukRelasi(int id_produk, String nama) {
        this.id_produk = id_produk;
        this.nama = nama;
    }

    public MProdukRelasi(int id_produk, String nama, int id_relasi, int fk_id_produk, int jumlah, String satuan, int fk_id_bahan, int isi_digunakan, String satuan_digunakan) {
        this.id_produk = id_produk;
        this.nama = nama;
        this.id_relasi = id_relasi;
        this.fk_id_produk = fk_id_produk;
        this.jumlah = jumlah;
        this.satuan = satuan;
        this.fk_id_bahan = fk_id_bahan;
        this.isi_digunakan = isi_digunakan;
        this.satuan_digunakan = satuan_digunakan;
    }

    public int getId_produk() {
        return id_produk;
    }

    public void setId_produk(int id_produk) {
        this.id_produk = id_produk;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public int getId_relasi() {
        return id_relasi;
    }

    public void setId_relasi(int id_relasi) {
        this.id_relasi = id_relasi;
    }

    public int getFk_id_produk() {
        return fk_id_produk;
    }

    public void setFk_id_produk(int fk_id_produk) {
        this.fk_id_produk = fk_id_produk;
    }

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

    public int getFk_id_bahan() {
        return fk_id_bahan;
    }

    public void setFk_id_bahan(int fk_id_bahan) {
        this.fk_id_bahan = fk_id_bahan;
    }

    public int getIsi_digunakan() {
        return isi_digunakan;
    }

    public void setIsi_digunakan(int isi_digunakan) {
        this.isi_digunakan = isi_digunakan;
    }

    public String getSatuan_digunakan() {
        return satuan_digunakan;
    }

    public void setSatuan_digunakan(String satuan_digunakan) {
        this.satuan_digunakan = satuan_digunakan;
    }

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
    }
}
