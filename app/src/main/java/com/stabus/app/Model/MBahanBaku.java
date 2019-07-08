package com.stabus.app.Model;

public class MBahanBaku {
    private int id;
    private String kategori;
    private String nama_bahan;
    //untuk penambahan model cardlist
    private int jumlah;

    boolean isSelected = false;
    boolean isOpen = false;

    public MBahanBaku(int id, String kategori, String nama_bahan, int jumlah) {
        this.id = id;
        this.kategori = kategori;
        this.nama_bahan = nama_bahan;
        this.jumlah = jumlah;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getKategori() {
        return kategori;
    }

    public void setKategori(String kategori) {
        this.kategori = kategori;
    }

    public String getNama_bahan() {
        return nama_bahan;
    }

    public void setNama_bahan(String nama_bahan) {
        this.nama_bahan = nama_bahan;
    }

    public int getJumlah() {
        return jumlah;
    }

    public void setJumlah(int jumlah) {
        this.jumlah = jumlah;
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
