package com.stabus.app.Class;

import android.support.design.widget.TextInputLayout;
import android.widget.Spinner;

import com.stabus.app.Database.DBMBahan;
import com.stabus.app.Database.DBMHarga;

public class ClassValidasi {

    private TextInputLayout mENama, mEMerk, mEIsi, mETempat, mEHarga;
    private Spinner mSpSatuan;
    private String nama, merk, satuan, tempat, title;
    private int isi;
    private float harga;

    public ClassValidasi() {
    }

    public ClassValidasi(TextInputLayout mENama,
                  TextInputLayout mEMerk, TextInputLayout mEIsi, TextInputLayout mETempat,
                  TextInputLayout mEHarga, Spinner mSpSatuan) {
        this.mENama = mENama;
        this.mEMerk = mEMerk;
        this.mEIsi = mEIsi;
        this.mETempat = mETempat;
        this.mEHarga = mEHarga;
        this.mSpSatuan = mSpSatuan;
    }

    String getNama() {
        if (mENama.getEditText() != null) {
            nama = mENama.getEditText().getText().toString().trim();
        }
        return nama;
    }

    String getMerk() {
        if (mEMerk.getEditText() != null) {
            merk = mEMerk.getEditText().getText().toString().trim();
        }
        return merk;
    }

    String getSatuan() {
        return satuan = mSpSatuan.getSelectedItem().toString().trim();
    }

    String getTempat() {
        if (mETempat.getEditText() != null) {
            tempat = mETempat.getEditText().getText().toString().trim();
        }
        return tempat;
    }

    int getIsi() {
        if (mEIsi.getEditText() != null) {
            if (mEIsi.getEditText().getText().toString().trim().length() != 0) {
                isi = Integer.parseInt(mEIsi.getEditText().getText().toString());
            } else {
                isi = 0;
            }
        }
        return isi;
    }

    public float getHarga() {
        if (mEHarga.getEditText() != null) {
            if (mEHarga.getEditText().getText().toString().trim().length() != 0) {
                harga = Float.parseFloat(mEHarga.getEditText().getText().toString());
            } else {
                harga = 0;
            }
        }
        return harga;
    }

    private void setErrorMessage(TextInputLayout inputLayout, String message){
        clearError();
        inputLayout.setError(message);
        inputLayout.requestFocus();
    }
    private void clearError(){
        mENama.setError(null);
        mEMerk.setError(null);
        mEIsi.setError(null);
        mETempat.setError(null);
        mEHarga.setError(null);
    }
    void clearText(boolean cekHarga){
        if (mENama.getEditText()!=null && mEMerk.getEditText()!=null&& mEIsi.getEditText()!=null&& mETempat.getEditText()!=null&& mEHarga.getEditText()!=null){
            mENama.getEditText().getText().clear();
            mEMerk.getEditText().getText().clear();
            mEIsi.getEditText().getText().clear();
            mETempat.getEditText().getText().clear();
            mEHarga.getEditText().getText().clear();
            mSpSatuan.setSelection(0);
            if (cekHarga){
                mEMerk.requestFocus();
            }else {
                mENama.requestFocus();
            }
            clearError();
        }
    }

    boolean cekBahan(){
        if (getNama().trim().length() == 0) {
            setErrorMessage(mENama, "Tidak Boleh Kosong");
            return true;
        }
        return false;
    }
    boolean cekIsi(){
        if (getIsi() == 0){
            setErrorMessage(mEIsi,"Tidak Boleh Kosong");
            return true;
        }
        return false;
    }
    boolean cekHarga(){
        if (getHarga()==0){
            setErrorMessage(mEHarga,"Tidak Boleh Kosong");
            return true;
        }
        return false;
    }
    boolean cekHargaSama(boolean cekHargaSama){
        if (cekHargaSama){
            setErrorMessage(mEHarga,"Harga ini sudah ada!");
            return true;
        }
        return false;
    }
    boolean cekSame(String s,DBMBahan dbmBahan, DBMHarga dbmHarga){
        if (dbmBahan.cekNama(s)){
            int id = dbmBahan.bahanBaku(s,0).getId();
            String satuan = dbmHarga.hargaBahan(0,id).getSatuan();
            if (satuan.equals("ml")) {
                mSpSatuan.setSelection(0);
            } else if (satuan.equals("gr")) {
                mSpSatuan.setSelection(1);
            } else {
                mSpSatuan.setSelection(2);
            }
            mSpSatuan.setClickable(false);
            return true;
        }else {
            mSpSatuan.setClickable(true);
            return true;
        }
    }
    /*
    */

    public boolean cekData(DBMBahan dbmBahan){
        if (dbmBahan.cekNama(getNama())){
            setErrorMessage(mENama, "Tersedia");
            return true;
        }else {
            clearError();
            return false;
        }
    }
    private void setSatuan(){
        if (getSatuan().equals("ml")) {
            mSpSatuan.setSelection(0);
        } else if (getSatuan().equals("gr")) {
            mSpSatuan.setSelection(1);
        } else {
            mSpSatuan.setSelection(2);
        }
    }

}
