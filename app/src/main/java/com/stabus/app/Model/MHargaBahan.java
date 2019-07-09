package com.stabus.app.Model;

public class MHargaBahan {
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

    public MHargaBahan(int id, String merk, int isi, String satuan, String tempat_beli, float harga, int idBK) {
        this.id = id;
        this.merk = merk;
        this.isi = isi;
        this.satuan = satuan;
        this.tempat_beli = tempat_beli;
        this.harga = harga;
        this.idBK = idBK;
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
