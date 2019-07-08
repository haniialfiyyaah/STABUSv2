package com.stabus.app;

import android.app.Activity;
import android.app.Dialog;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputLayout;
import android.text.InputFilter;
import android.view.View;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.stabus.app.Database.DBMBahan;
import com.stabus.app.Database.DBMHarga;
import com.stabus.app.Model.MBahanBaku;
import com.stabus.app.Model.MHargaBahan;
import com.stabus.app.RecyclerView.BahanBakuAdapter;
import com.stabus.app.RecyclerView.HargaBahanAdapter;

import java.util.ArrayList;
import java.util.List;

public class DialogTambah implements View.OnClickListener {

    private TextView mTitle;
    private Spinner mSpSatuan;
    private FloatingActionButton fabsave;
    private TextInputLayout mENama, mEMerk, mEIsi, mETempat, mEHarga;
    private String nama, merk, satuan, tempat, title;
    private int isi;
    private float harga;

    private View view;
    private DBMBahan dbmBahan;
    private List<MBahanBaku> bahanBakuList;
    private BahanBakuAdapter mAdapter;
    private FrameLayout frameRV;
    private ScrollView scrollView;

    private String tag;

    DBMHarga dbmHarga;
    List<MHargaBahan> hargaBahanList;
    HargaBahanAdapter mHAdapter;

    private Activity activity;

    public DialogTambah(String tag, Activity activity, View view, DBMBahan dbmBahan, List<MBahanBaku> bahanBakuList, BahanBakuAdapter mAdapter, FrameLayout frameRV, ScrollView scrollView) {
        this.tag = tag;
        this.activity = activity;
        this.view = view;
        this.dbmBahan = dbmBahan;
        this.bahanBakuList = bahanBakuList;
        this.mAdapter = mAdapter;
        this.frameRV = frameRV;
        this.scrollView = scrollView;
    }

    public DialogTambah(String tag, Activity activity, View view,  DBMHarga dbmHarga, List<MHargaBahan> hargaBahanList, HargaBahanAdapter mHAdapter,FrameLayout frameRV, ScrollView scrollView) {
        this.tag = tag;
        this.view = view;
        this.frameRV = frameRV;
        this.scrollView = scrollView;
        this.dbmHarga = dbmHarga;
        this.hargaBahanList = hargaBahanList;
        this.mHAdapter = mHAdapter;
        this.activity = activity;
    }

    public void tambahDialog(String title){
        showDialog(new Dialog(view.getContext()));
        mTitle.setText(title);
        fabsave.setOnClickListener(this);
        //clearMenu();
    }
    private void showDialog(Dialog dialog){
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_tambah_bahan);

        mENama = dialog.findViewById(R.id.textNama);
        mEMerk = dialog.findViewById(R.id.textMerk);
        mEIsi = dialog.findViewById(R.id.textIsi);
        mETempat = dialog.findViewById(R.id.textTempat);
        mEHarga = dialog.findViewById(R.id.textHarga);
        mSpSatuan = dialog.findViewById(R.id.spSatuan);
        fabsave = dialog.findViewById(R.id.fabTambahBK);
        mTitle = dialog.findViewById(R.id.titleHarga);

        Toast toast = Toast.makeText(view.getContext(), tag, Toast.LENGTH_SHORT);
        toast.show();

        InputFilter[] inputFilter = new InputFilter[]{new InputFilter.AllCaps()};
        if (mENama.getEditText()!=null && mEMerk.getEditText()!=null&& mETempat.getEditText()!=null){
            mENama.getEditText().setFilters(inputFilter);
            mEMerk.getEditText().setFilters(inputFilter);
            mETempat.getEditText().setFilters(inputFilter);
        }

        dialog.show();
    }
    private void tambahData(){
        getString();
        //validasi
        if (nama.trim().length() == 0) {
            setErrorMessage(mENama,"Tidak Boleh Kosong");
            return;
        }
        if (dbmBahan.cekNama(nama) && (merk.trim().length() == 0&&isi <= 0&&tempat.trim().length() == 0&&harga <= 0)){
            setErrorMessage(mENama, "Data Sudah Ada");
            return;
        }
        if (merk.trim().length() != 0||isi > 0||tempat.trim().length() != 0||harga > 0){
            if (isi == 0){
                setErrorMessage(mEIsi,"");
                return;
            }
            if (harga==0){
                setErrorMessage(mEHarga,"Tidak Boleh Kosong");
                return;
            }
        }
        //saveharga
        if (dbmBahan.cekNama(nama)){
            //show dialog tambah harga
            //save harga
            //inflate fragment nama
        }else {
            dbmBahan.save(nama,"Bahan Baku");
            bahanBakuList.add(0,dbmBahan.bahanBaku(nama));
            mAdapter.notifyItemInserted(0);
            mAdapter.notifyDataSetChanged();
            cekEmptyList();
        }
        clearText();
        //refreshList();
    }
    private void clearText(){
        if (mENama.getEditText()!=null && mEMerk.getEditText()!=null&& mEIsi.getEditText()!=null&& mETempat.getEditText()!=null&& mEHarga.getEditText()!=null){
            mENama.getEditText().getText().clear();
            mEMerk.getEditText().getText().clear();
            mEIsi.getEditText().getText().clear();
            mETempat.getEditText().getText().clear();
            mEHarga.getEditText().getText().clear();
            mENama.requestFocus();
            mSpSatuan.setSelection(0);
            clearError();
        }
    }
    private void clearError(){
        mENama.setError(null);
        mEMerk.setError(null);
        mEIsi.setError(null);
        mETempat.setError(null);
        mEHarga.setError(null);
    }
    private void getString(){
        if (mENama.getEditText()!=null && mEMerk.getEditText()!=null&& mEIsi.getEditText()!=null&& mETempat.getEditText()!=null&& mEHarga.getEditText()!=null) {

            nama = mENama.getEditText().getText().toString();
            merk = mEMerk.getEditText().getText().toString();
            if (mEIsi.getEditText().getText().toString().trim().length() != 0) {
                isi = Integer.parseInt(mEIsi.getEditText().getText().toString());
            } else {
                isi = 0;
            }
            satuan = mSpSatuan.getSelectedItem().toString();
            tempat = mETempat.getEditText().getText().toString();
            if (mEHarga.getEditText().getText().toString().trim().length() != 0) {
                harga = Float.parseFloat(mEHarga.getEditText().getText().toString());
            } else {
                harga = 0;
            }
            title = mTitle.getText().toString();
        }
    }
    private void setErrorMessage(TextInputLayout inputLayout, String message){
        clearError();
        inputLayout.setError(message);
        inputLayout.requestFocus();
    }
    public void cekEmptyList(){
        if (bahanBakuList.size()==0){
            scrollView.setVisibility(View.GONE);
            frameRV.setVisibility(View.VISIBLE);
        }else {
            scrollView.setVisibility(View.VISIBLE);
            frameRV.setVisibility(View.GONE);
        }
    }


    private void callMenu(){
        if (activity!=null){
            activity.invalidateOptionsMenu();
        }
    }

    @Override
    public void onClick(View v) {
        tambahData();
        callMenu();

    }

}
