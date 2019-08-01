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
    private float margin_harga;
    private float harga_jual;
    //komponen

    private double tenagaKerja = 15; //15 25
    private double biayaOp = 10; //10 20
    private double resiko = 10;// 10
    private double keuntungan =30;//30 50
    private double marketing = 10; //tidak usah //10 50

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

    public double getTenagaKerja() {
        return tenagaKerja;
    }

    public void setTenagaKerja(double tenagaKerja) {
        this.tenagaKerja = tenagaKerja;
    }

    public double getBiayaOp() {
        return biayaOp;
    }

    public void setBiayaOp(double biayaOp) {
        this.biayaOp = biayaOp;
    }

    public double getResiko() {
        return resiko;
    }

    public void setResiko(double resiko) {
        this.resiko = resiko;
    }

    public double getKeuntungan() {
        return keuntungan;
    }

    public void setKeuntungan(double keuntungan) {
        this.keuntungan = keuntungan;
    }

    public double getMarketing() {
        return marketing;
    }

    public void setMarketing(double marketing) {
        this.marketing = marketing;
    }

    public float getMargin_harga() {
        return margin_harga;
    }

    public void setMargin_harga(float margin_harga) {
        this.margin_harga = margin_harga;
    }

    public float getHarga_jual() {
        return harga_jual;
    }

    public void setHarga_jual(float harga_jual) {
        this.harga_jual = harga_jual;
    }
}
