package com.stabus.app.Model;

public class MProdukRelasi {

    //tabel produk
//    private int id_produk;
//    private String nama;

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

    private String nama_produk;
    private String nama_bahan;
    private float harga_pilih;


    //helper
    private int pos;

    public MProdukRelasi() {
    }

    public MProdukRelasi(int fk_id_produk, String nama_produk) {
        this.fk_id_produk = fk_id_produk;
        this.nama_produk = nama_produk;
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

    public String getNama_produk() {
        return nama_produk;
    }

    public void setNama_produk(String nama_produk) {
        this.nama_produk = nama_produk;
    }

    public String getNama_bahan() {
        return nama_bahan;
    }

    public void setNama_bahan(String nama_bahan) {
        this.nama_bahan = nama_bahan;
    }

    public float getHarga_pilih() {
        return harga_pilih;
    }

    public void setHarga_pilih(float harga_pilih) {
        this.harga_pilih = harga_pilih;
    }

}
