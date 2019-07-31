package com.stabus.app.Class;

import android.support.design.widget.TextInputLayout;
import android.text.Editable;
import android.text.TextWatcher;

import com.stabus.app.Database.DBMProduk;
import com.stabus.app.Model.CollectStringCRUD;

public class MyTextWatcher implements TextWatcher {

    private TextInputLayout txInput;
    private DBMProduk dbmProduk;
    private String text;
    private CollectStringCRUD stringCRUD;

    public MyTextWatcher(TextInputLayout txInput, DBMProduk dbmProduk, String text, CollectStringCRUD stringCRUD) {
        this.txInput = txInput;
        this.dbmProduk = dbmProduk;
        this.text = text;
        this.stringCRUD = stringCRUD;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (text.equals("Nama")){
            if (dbmProduk.cekNama(String.valueOf(s))){
                txInput.setError("Tersedia");
            }else {
                txInput.setError(null);
            }
        }
    }

    @Override
    public void afterTextChanged(Editable s) {
        if (text.equals("Nama")){
            stringCRUD.getString().get(0).setNama(String.valueOf(s));
        }
        else{
            if (s.toString().trim().length()>0)
                stringCRUD.getString().get(0).setJumlah(Integer.valueOf(String.valueOf(s)));
        }
    }
}
