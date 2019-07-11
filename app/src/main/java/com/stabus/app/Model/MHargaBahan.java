package com.stabus.app.Model;

public class MHargaBahan {

    //tabel bahan
    private int id_bahan;
    private String kategori_bahan;
    private String nama_bahan;
    //untuk penambahan model cardlist
   // private int jumlah;

    //tabel harga
    private int id;
    private String merk;
    private int isi;
    private String satuan;
    private String tempat_beli;
    private float harga;
    //foreign
    private int idBK;

    boolean isSelected = false;
    boolean isOpen = false;

    //constructor tabel bahan
    public MHargaBahan(int id_bahan, String kategori_bahan, String nama_bahan) {
        this.id_bahan = id_bahan;
        this.kategori_bahan = kategori_bahan;
        this.nama_bahan = nama_bahan;
    }

    //constructor tabel harga + nama bahan
    public MHargaBahan(String nama_bahan, int id, String merk, int isi, String satuan, String tempat_beli, float harga, int idBK) {
        this.nama_bahan = nama_bahan;
        this.id = id;
        this.merk = merk;
        this.isi = isi;
        this.satuan = satuan;
        this.tempat_beli = tempat_beli;
        this.harga = harga;
        this.idBK = idBK;
    }

    //constructor lama
    public MHargaBahan(int id, String merk, int isi, String satuan, String tempat_beli, float harga, int idBK) {
        this.id = id;
        this.merk = merk;
        this.isi = isi;
        this.satuan = satuan;
        this.tempat_beli = tempat_beli;
        this.harga = harga;
        this.idBK = idBK;
    }


    public int getId_bahan() {
        return id_bahan;
    }

    public void setId_bahan(int id_bahan) {
        this.id_bahan = id_bahan;
    }

    public String getKategori_bahan() {
        return kategori_bahan;
    }

    public void setKategori_bahan(String kategori_bahan) {
        this.kategori_bahan = kategori_bahan;
    }

    public String getNama_bahan() {
        return nama_bahan;
    }

    public void setNama_bahan(String nama_bahan) {
        this.nama_bahan = nama_bahan;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMerk() {
        return merk;
    }

    public void setMerk(String merk) {
        this.merk = merk;
    }

    public int getIsi() {
        return isi;
    }

    public void setIsi(int isi) {
        this.isi = isi;
    }

    public String getSatuan() {
        return satuan;
    }

    public void setSatuan(String satuan) {
        this.satuan = satuan;
    }

    public String getTempat_beli() {
        return tempat_beli;
    }

    public void setTempat_beli(String tempat_beli) {
        this.tempat_beli = tempat_beli;
    }

    public float getHarga() {
        return harga;
    }

    public void setHarga(float harga) {
        this.harga = harga;
    }

    public int getIdBK() {
        return idBK;
    }

    public void setIdBK(int idBK) {
        this.idBK = idBK;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public boolean isOpen() {
        return isOpen;
    }

    public void setOpen(boolean open) {
        isOpen = open;
    }
}
