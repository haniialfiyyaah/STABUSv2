package com.stabus.app;

import android.app.Activity;
import android.app.Dialog;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputLayout;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;

import com.stabus.app.Database.DBMBahan;
import com.stabus.app.Database.DBMHarga;
import com.stabus.app.Model.MBahanBaku;
import com.stabus.app.Model.MHargaBahan;
import com.stabus.app.RecyclerView.BahanBakuAdapter;
import com.stabus.app.RecyclerView.HargaBahanAdapter;

import java.util.List;

public class ClassDialogTambah implements View.OnClickListener {

    private TextView mTitle;
    private Spinner mSpSatuan;
    private FloatingActionButton fabsave;
    private TextInputLayout mENama, mEMerk, mEIsi, mETempat, mEHarga;

    private View view;
    private FrameLayout frameRV;
    private ScrollView scrollView;
    private Activity activity;
    //harga
    private List<MHargaBahan> hargaBahanList;
    private HargaBahanAdapter mHAdapter;
    //bahan
    private List<MBahanBaku> bahanBakuList;
    private BahanBakuAdapter mAdapter;

    private int sId;
    private String tag;
    private boolean cekHarga;
    private ClassValidasi validasi;
    private DBMBahan dbmBahan;
    private DBMHarga dbmHarga;

    //bahan
    ClassDialogTambah(String tag, Activity activity, View view, List<MBahanBaku> bahanBakuList, BahanBakuAdapter mAdapter, FrameLayout frameRV, ScrollView scrollView) {
        this.tag = tag;
        this.activity = activity;
        this.view = view;
        this.bahanBakuList = bahanBakuList;
        this.mAdapter = mAdapter;
        this.frameRV = frameRV;
        this.scrollView = scrollView;

    }

    //harga
    ClassDialogTambah(String tag, Activity activity, View view, List<MHargaBahan> hargaBahanList, HargaBahanAdapter mHAdapter, FrameLayout frameRV, ScrollView scrollView) {
        this.tag = tag;
        this.view = view;
        this.frameRV = frameRV;
        this.scrollView = scrollView;
        this.hargaBahanList = hargaBahanList;
        this.mHAdapter = mHAdapter;
        this.activity = activity;
    }

    void tambahDialog(String title, int sId){
        dbmBahan = new DBMBahan(view);
        dbmHarga = new DBMHarga(view);
        this.sId = sId;
        Dialog dialog = new Dialog(view.getContext());
        showDialog(dialog);
        mTitle.setText(title);
        fabsave.setOnClickListener(this);
        setSatuan();
        //clearMenu();
    }
    void showDialog(Dialog dialog){
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

        cekHarga = tag.matches(activity.getString(R.string.HargaBahanBaku));
        validasi = new ClassValidasi(mENama, mEMerk, mEIsi, mETempat, mEHarga, mSpSatuan);

        if (cekHarga){
            mENama.setVisibility(View.GONE);
            String satuan = dbmHarga.hargaBahan(0, sId).getSatuan();
            if (satuan.equals("ml")) {
                mSpSatuan.setSelection(0);
            } else if (satuan.equals("gr")) {
                mSpSatuan.setSelection(1);
            } else {
                mSpSatuan.setSelection(2);
            }
            mSpSatuan.setEnabled(false);
        }
        InputFilter[] inputFilter = new InputFilter[]{new InputFilter.AllCaps()};
        if (mENama.getEditText()!=null && mEMerk.getEditText()!=null&& mETempat.getEditText()!=null){
            mENama.getEditText().setFilters(inputFilter);
            mEMerk.getEditText().setFilters(inputFilter);
            mETempat.getEditText().setFilters(inputFilter);
        }
        dialog.show();
    }
    private void tambahData(){
        //cek nama kosong atau spasi
        boolean cekIsiHarga = false;
        if (!cekHarga) {
            if (validasi.cekBahan()||validasi.cekIsi()||validasi.cekHarga()) return;
            //cek isi dan harga tidak boleh kosong jika salah satu ada terisi
            if (validasi.getMerk().trim().length() != 0||validasi.getIsi() > 0||validasi.getTempat().trim().length() != 0||validasi.getHarga() > 0){
                if (validasi.cekIsi() || validasi.cekHarga()) return;
                cekIsiHarga = true;
            }
            //simpan harga jika data sudah ada
            //nama sama : harga isi ? saveharga cek harga else error
            if (dbmBahan.cekNama(validasi.getNama())&& cekIsiHarga){
                int idBK = dbmBahan.bahanBaku(validasi.getNama(),0).getId();
                boolean cekHargaSama = dbmHarga.cekHarga(validasi.getMerk(),validasi.getIsi(),validasi.getSatuan(),validasi.getTempat(),validasi.getHarga(),idBK);

                if (validasi.cekHargaSama(cekHargaSama)) return;

                dbmHarga.save(validasi.getMerk(),validasi.getIsi(),validasi.getSatuan(),validasi.getTempat(),validasi.getHarga(),idBK);
                refreshList();
            }
            //nama beda : harga isi ? savenama saveharga
            else if (cekIsiHarga){
                dbmBahan.save(validasi.getNama(),"Bahan Baku");
                int idBK = dbmBahan.bahanBaku(validasi.getNama(),0).getId();
                dbmHarga.save(validasi.getMerk(),validasi.getIsi(),validasi.getSatuan(),validasi.getTempat(),validasi.getHarga(),idBK);
                saveBahan();
            }
            //nama beda : harga kosong ? savenama
            else {
                dbmBahan.save(validasi.getNama(),"Bahan Baku");
                saveBahan();
            }
        }
        //cek isi dan harga tidak boleh kosong
        else {
            boolean cekHargaSama = dbmHarga.cekHarga(validasi.getMerk(),validasi.getIsi(),validasi.getSatuan(),validasi.getTempat(),validasi.getHarga(),sId);
            if (validasi.cekIsi() || validasi.cekHarga() || validasi.cekHargaSama(cekHargaSama)) return;
            saveHarga(sId);
        }

        validasi.clearText(cekHarga);
        //refreshList();
    }
    private void saveHarga(int idBK){
        dbmHarga.save(validasi.getMerk(),validasi.getIsi(),validasi.getSatuan(),validasi.getTempat(),validasi.getHarga(),idBK);
        hargaBahanList.add(0,dbmHarga.hargaBahan(0,idBK));
        mHAdapter.notifyItemInserted(0);
        mHAdapter.notifyDataSetChanged();
        cekEmptyList();
    }
    private void saveBahan(){
        //dbmBahan.save(validasi.getNama(),"Bahan Baku");
        bahanBakuList.add(0,dbmBahan.bahanBaku(validasi.getNama(),0));
        mAdapter.notifyItemInserted(0);
        mAdapter.notifyDataSetChanged();
        cekEmptyList();
    }

    private void refreshList(){
        dbmBahan.getAllBahan(bahanBakuList);
        mAdapter.notifyDataSetChanged();
        cekEmptyList();
    }
    private void cekEmptyList(){
        int size;
        if (cekHarga){
            size = hargaBahanList.size();
        }else {
            size = bahanBakuList.size();
        }
        if (size==0){
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

    private void setSatuan(){
        mENama.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                MBahanBaku bahanBaku = dbmBahan.bahanBaku(String.valueOf(s), 0);
                if (bahanBaku!=null){
                    int id = bahanBaku.getId();
                    String satuan = dbmHarga.hargaBahan(0,id).getSatuan();
                    if (satuan.equals("ml")) {
                        mSpSatuan.setSelection(0);
                    } else if (satuan.equals("gr")) {
                        mSpSatuan.setSelection(1);
                    } else {
                        mSpSatuan.setSelection(2);
                    }
                    mSpSatuan.setClickable(false);
                    mSpSatuan.setEnabled(false);
                    validasi.cekData(dbmBahan);
                }else {
                    mSpSatuan.setSelection(0);
                    mSpSatuan.setClickable(true);
                    mSpSatuan.setEnabled(true);
                    validasi.cekData(dbmBahan);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }


    @Override
    public void onClick(View v) {
        tambahData();
        callMenu();

    }

}
