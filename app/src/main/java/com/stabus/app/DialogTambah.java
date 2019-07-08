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

import java.util.List;

public class DialogTambah implements View.OnClickListener {

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
    private Class_Validasi validasi;
    private DBMBahan dbmBahan;
    private DBMHarga dbmHarga;

    //bahan
    DialogTambah(String tag, Activity activity, View view, List<MBahanBaku> bahanBakuList, BahanBakuAdapter mAdapter, FrameLayout frameRV, ScrollView scrollView) {
        this.tag = tag;
        this.activity = activity;
        this.view = view;
        this.bahanBakuList = bahanBakuList;
        this.mAdapter = mAdapter;
        this.frameRV = frameRV;
        this.scrollView = scrollView;
        dbmBahan = new DBMBahan(view);
        dbmHarga = new DBMHarga(view);
    }

    //harga
    DialogTambah(String tag, Activity activity, View view, List<MHargaBahan> hargaBahanList, HargaBahanAdapter mHAdapter, FrameLayout frameRV, ScrollView scrollView) {
        this.tag = tag;
        this.view = view;
        this.frameRV = frameRV;
        this.scrollView = scrollView;
        this.hargaBahanList = hargaBahanList;
        this.mHAdapter = mHAdapter;
        this.activity = activity;
        dbmBahan = new DBMBahan(view);
        dbmHarga = new DBMHarga(view);
    }

    void tambahDialog(String title, int sId){
        showDialog(new Dialog(view.getContext()));
        mTitle.setText(title);
        this.sId = sId;
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

        cekHarga = tag.matches(activity.getString(R.string.HargaBahanBaku));
        validasi = new Class_Validasi(mENama,mEMerk,mEIsi,mETempat,mEHarga,mSpSatuan);

        if (cekHarga){
            mENama.setVisibility(View.GONE);
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
            if (validasi.getNama().trim().length() == 0) {
                validasi.setErrorMessage(mENama, "Tidak Boleh Kosong");
                return;
            }
            //cek isi dan harga tidak boleh kosong jika salah satu ada terisi
            if (validasi.getMerk().trim().length() != 0||validasi.getIsi() > 0||validasi.getTempat().trim().length() != 0||validasi.getHarga() > 0){
                if (validasi.getIsi() == 0){
                    validasi.setErrorMessage(mEIsi,"");
                    return;
                }
                if (validasi.getHarga()==0){
                    validasi.setErrorMessage(mEHarga,"Tidak Boleh Kosong");
                    return;
                }
                cekIsiHarga = true;
            }
            //cek bahan sudah ada atau belum
            if (dbmBahan.cekNama(validasi.getNama()) && (validasi.getMerk().trim().length() == 0&&validasi.getIsi() <= 0&&validasi.getTempat().trim().length() == 0&&validasi.getHarga() <= 0)){
                validasi.setErrorMessage(mENama, "Data Sudah Ada");
                return;
            }
            //simpan harga jika data sudah ada
            //nama sama : harga isi ? saveharga cek harga else error
            if (dbmBahan.cekNama(validasi.getNama())&& cekIsiHarga){
                int idBK = dbmBahan.bahanBaku(validasi.getNama()).getId();
                boolean cekHargaSama = dbmHarga.cekHarga(validasi.getMerk(),validasi.getIsi(),validasi.getSatuan(),validasi.getTempat(),validasi.getHarga(),idBK);
                if (cekHargaSama){
                    validasi.setErrorMessage(mEHarga,"Harga ini sudah ada!");
                    return;
                }
                dbmHarga.save(validasi.getMerk(),validasi.getIsi(),validasi.getSatuan(),validasi.getTempat(),validasi.getHarga(),idBK);
                dbmBahan.getAllBahan(bahanBakuList);
                mAdapter.notifyDataSetChanged();
                cekEmptyList();
            }
            //nama beda : harga isi ? savenama saveharga
            else if (cekIsiHarga){
                dbmBahan.save(validasi.getNama(),"Bahan Baku");
                int idBK = dbmBahan.bahanBaku(validasi.getNama()).getId();
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
            if (validasi.getIsi() == 0){
                validasi.setErrorMessage(mEIsi,"");
                return;
            }
            if (validasi.getHarga()==0){
                validasi.setErrorMessage(mEHarga,"Tidak Boleh Kosong");
                return;
            }
            boolean cekHargaSama = dbmHarga.cekHarga(validasi.getMerk(),validasi.getIsi(),validasi.getSatuan(),validasi.getTempat(),validasi.getHarga(),sId);
            if (cekHargaSama){
                validasi.setErrorMessage(mEHarga,"Harga ini sudah ada!");
                return;
            }
            saveHarga(sId);
        }

        validasi.clearText(cekHarga);
        //refreshList();
    }
    private void saveHarga(int idBK){
        dbmHarga.save(validasi.getMerk(),validasi.getIsi(),validasi.getSatuan(),validasi.getTempat(),validasi.getHarga(),idBK);
        hargaBahanList.add(0,dbmHarga.hargaBahan(idBK));
        mHAdapter.notifyItemInserted(0);
        mHAdapter.notifyDataSetChanged();
        cekEmptyList();
    }
    private void saveBahan(){
        //dbmBahan.save(validasi.getNama(),"Bahan Baku");
        bahanBakuList.add(0,dbmBahan.bahanBaku(validasi.getNama()));
        mAdapter.notifyItemInserted(0);
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

    @Override
    public void onClick(View v) {
        tambahData();
        callMenu();

    }

}
