package com.stabus.app;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
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
import com.stabus.app.Interface.ISetListener;
import com.stabus.app.Model.MHargaBahan;
import com.stabus.app.RecyclerView.HargaBahanAdapter;

import java.util.List;

public class DialogEdit implements View.OnClickListener {

    private TextView mTitle;
    private Spinner mSpSatuan;
    private FloatingActionButton fabsave;
    private TextInputLayout mENama, mEMerk, mEIsi, mETempat, mEHarga;

    private DialogTambah dialogTambah;

    private View view;
    private Activity activity;
    private String tag;
    private ScrollView scrollView;
    private FrameLayout frameLayout;

    private List<MHargaBahan> hargaBahanList;
    private HargaBahanAdapter mHAdapter;

    private String nama, merk, satuan, tempat;
    private int idH,idB, isi, idBK;
    private float harga;

    private DBMBahan dbmBahan;
    private DBMHarga dbmHarga;
    private Class_Validasi validasi;
    private boolean cekBahan;

    private ISetListener mISetListener;
    private Dialog dialog;

    DialogEdit(ISetListener mISetListener,View view, Activity activity, List<MHargaBahan> hargaBahanList, HargaBahanAdapter mHAdapter,ScrollView scrollView, FrameLayout frameLayout) {
        this.mISetListener = mISetListener;
        this.view = view;
        this.activity = activity;
        this.hargaBahanList = hargaBahanList;
        this.mHAdapter = mHAdapter;
        this.scrollView = scrollView;
        this.frameLayout = frameLayout;
    }


    void editDialog(String tag,int id, String nama){
        dbmBahan = new DBMBahan(view);
        dbmHarga = new DBMHarga(view);
        this.tag=tag;
        dialog = new Dialog(view.getContext());
        showDialog(dialog);
        mTitle.setText(String.format("%s %s", tag, nama));
        getData(id, nama);
        setTextView();
        fabsave.setOnClickListener(this);

    }
    private void getData(int idHarga, String namaBahan){
        MHargaBahan hargaBahan = dbmHarga.hargaBahan(idHarga,0);
        idB=idHarga;
        nama=namaBahan;
        if (!cekBahan) {
            idH = hargaBahan.getId();
            merk = hargaBahan.getMerk();
            isi = hargaBahan.getIsi();
            satuan = hargaBahan.getSatuan();
            tempat = hargaBahan.getTempat_beli();
            harga = hargaBahan.getHarga();
            idBK = hargaBahan.getIdBK();
        }
    }
    private void editData(){
        boolean cekIsiHarga = false;
        //cek isi dan harga tidak boleh kosong jika salah satu ada terisi
        if (cekBahan){
            if (validasi.cekBahan()) return;
            Toast toast = Toast.makeText(view.getContext(), validasi.getNama(), Toast.LENGTH_SHORT);
            toast.show();
            dbmBahan.ubahBahan(dbmBahan.bahanBaku(nama,0).getId(),validasi.getNama(),"Bahan Baku");
            dialog.dismiss();
        }else {
            if (validasi.cekIsi() || validasi.cekHarga()) return;
            dbmHarga.ubahHarga(idH, validasi.getMerk(), validasi.getIsi(), validasi.getSatuan(), validasi.getTempat(), validasi.getHarga(), idBK);
            refreshList();
        }
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

        InputFilter[] inputFilter = new InputFilter[]{new InputFilter.AllCaps()};
        if (mENama.getEditText()!=null && mEMerk.getEditText()!=null&& mETempat.getEditText()!=null){
            mENama.getEditText().setFilters(inputFilter);
            mEMerk.getEditText().setFilters(inputFilter);
            mETempat.getEditText().setFilters(inputFilter);
        }
        cekBahan=false;
        cekBahan = tag.matches(activity.getString(R.string.UbahBahan));
        validasi = new Class_Validasi(mENama,mEMerk,mEIsi,mETempat,mEHarga,mSpSatuan);
        if (cekBahan){
            mENama.setEnabled(true);
            mEMerk.setVisibility(View.GONE);
            mEIsi.setVisibility(View.GONE);
            mSpSatuan.setVisibility(View.GONE);
            mETempat.setVisibility(View.GONE);
            mEHarga.setVisibility(View.GONE);
        }else {
            mENama.setVisibility(View.GONE);
        }
        dialog.show();
    }
    @SuppressLint("DefaultLocale")
    private void setTextView(){
        if (mENama.getEditText() != null && mEMerk.getEditText() != null && mEIsi.getEditText() != null && mETempat.getEditText() != null&&mEHarga.getEditText() != null) {
            mENama.getEditText().setText(nama);
            mEMerk.getEditText().setText(merk);
            mEIsi.getEditText().setText(String.valueOf(isi));
            setSatuan();
            mETempat.getEditText().setText(tempat);
            mEHarga.getEditText().setText(String.format("%.0f", harga));
        }

    }
    private void setSatuan(){
        if (!cekBahan) {
            if (satuan.equals("ml")) {
                mSpSatuan.setSelection(0);
            } else if (satuan.equals("gr")) {
                mSpSatuan.setSelection(1);
            } else {
                mSpSatuan.setSelection(2);
            }
        }
    }

    private void refreshList(){
        dbmHarga.getAllHarga(hargaBahanList,idBK);
        mHAdapter.notifyDataSetChanged();
        cekEmptyList();
    }
    private void cekEmptyList(){
        int size;
        if (hargaBahanList.size()==0){
            scrollView.setVisibility(View.GONE);
            frameLayout.setVisibility(View.VISIBLE);
        }else {
            scrollView.setVisibility(View.VISIBLE);
            frameLayout.setVisibility(View.GONE);
        }
    }
    private void callMenu(){
        if (activity!=null){
            activity.invalidateOptionsMenu();
        }
        mISetListener.setToolbarTitle(validasi.getNama());
    }

    @Override
    public void onClick(View v) {
        editData();
        callMenu();
    }
}
