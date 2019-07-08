package com.stabus.app;

import android.support.design.widget.TextInputLayout;
import android.view.View;
import android.widget.Spinner;

import com.stabus.app.Database.DBMBahan;
import com.stabus.app.Database.DBMHarga;

import org.w3c.dom.Text;

public class Class_Validasi {

    private TextInputLayout mENama, mEMerk, mEIsi, mETempat, mEHarga;
    private Spinner mSpSatuan;
    private String nama, merk, satuan, tempat, title;
    private int isi;
    private float harga;

    public Class_Validasi(TextInputLayout mENama,
                          TextInputLayout mEMerk, TextInputLayout mEIsi, TextInputLayout mETempat,
                          TextInputLayout mEHarga, Spinner mSpSatuan) {
        this.mENama = mENama;
        this.mEMerk = mEMerk;
        this.mEIsi = mEIsi;
        this.mETempat = mETempat;
        this.mEHarga = mEHarga;
        this.mSpSatuan = mSpSatuan;
    }

    public String getNama() {
        if (mENama.getEditText() != null) {
            nama = mENama.getEditText().getText().toString().trim();
        }
        return nama;
    }

    public String getMerk() {
        if (mEMerk.getEditText() != null) {
            merk = mEMerk.getEditText().getText().toString().trim();
        }
        return merk;
    }

    public String getSatuan() {
        return satuan = mSpSatuan.getSelectedItem().toString().trim();
    }

    public String getTempat() {
        if (mETempat.getEditText() != null) {
            tempat = mETempat.getEditText().getText().toString().trim();
        }
        return tempat;
    }

    public int getIsi() {
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

    public void setErrorMessage(TextInputLayout inputLayout, String message){
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
    public void clearText(boolean cekHarga){
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

    public void cekBahan(){
        if (getNama().trim().length() == 0) {
            setErrorMessage(mENama, "Tidak Boleh Kosong");
            return;
        }
    }

}
