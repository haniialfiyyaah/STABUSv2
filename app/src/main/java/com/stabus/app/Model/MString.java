package com.stabus.app.Model;

public class MString {

    //PRODUK
    private int id;
    private String nama;
    private String satuan;
    private int jumlah;

    private boolean isEdit = false;

    private boolean isSelectAll = false;
    private float harga_total;

    public MString() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isEdit() {
        return isEdit;
    }

    public void setEdit(boolean edit) {
        isEdit = edit;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getSatuan() {
        return satuan;
    }

    public void setSatuan(String satuan) {
        this.satuan = satuan;
    }

    public int getJumlah() {
        return jumlah;
    }

    public void setJumlah(int jumlah) {
        this.jumlah = jumlah;
    }

    public float getHarga_total() {
        return harga_total;
    }

    public void setHarga_total(float harga_total) {
        this.harga_total = harga_total;
    }

    public boolean isSelectAll() {
        return isSelectAll;
    }

    public void setSelectAll(boolean selectAll) {
        isSelectAll = selectAll;
    }
}
