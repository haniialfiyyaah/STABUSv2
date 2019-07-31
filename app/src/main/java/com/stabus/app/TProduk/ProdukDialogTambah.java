package com.stabus.app.TProduk;

import android.app.Dialog;
import android.support.design.widget.TextInputLayout;
import android.view.View;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;

import com.stabus.app.Database.DBMHarga;
import com.stabus.app.Model.CollectBahanCRUD;
import com.stabus.app.Model.MBahanBaku;
import com.stabus.app.Model.MProdukRelasi;
import com.stabus.app.R;
import com.stabus.app.RecyclerView.BahanBakuAdapter;
import com.stabus.app.RecyclerView.RelasiAdapter;

import java.util.List;

public class ProdukDialogTambah implements View.OnClickListener {

    //widget dialog
    private TextView nmBahan;
    private TextInputLayout mJumlah;
    private Spinner mSatuan;
    private ImageButton mOk;
    private Dialog d;
    //Bahan Baku POS
    private int pos;
    View view;

    //ProdukPIlihBahan
    private DBMHarga dbmHarga;
    private List<MBahanBaku> bahanBakuList;
    private BahanBakuAdapter mBKAdapter;
    //ProdukTambah
    private RelasiAdapter mAdapter;
    private CollectBahanCRUD crud;
    private ScrollView scrollView;
    private FrameLayout frameLayout;

    //jumlah digunakan from ProdukPilihBahan
    public ProdukDialogTambah(DBMHarga dbmHarga, List<MBahanBaku> bahanBakuList, BahanBakuAdapter mBKAdapter, CollectBahanCRUD crud) {
        this.dbmHarga = dbmHarga;
        this.bahanBakuList = bahanBakuList;
        this.mBKAdapter = mBKAdapter;
        this.crud = crud;
    }

    //jumlah digunakan from ProdukTambah

    public ProdukDialogTambah(RelasiAdapter mAdapter, CollectBahanCRUD crud, ScrollView scrollView, FrameLayout frameLayout) {
        this.mAdapter = mAdapter;
        this.crud = crud;
        this.scrollView = scrollView;
        this.frameLayout = frameLayout;
    }

    public void showDialog(View view, int pos){
        this.view = view;
        this.pos = pos;
        d = new Dialog(view.getContext());
        d.requestWindowFeature(Window.FEATURE_NO_TITLE);
        d.setContentView(R.layout.dialog_pilih_jumlahdg);
        nmBahan = d.findViewById(R.id.tvNmBahan);
        mJumlah = d.findViewById(R.id.textJumlahdg);
        mSatuan = d.findViewById(R.id.spSatuan);
        mOk = d.findViewById(R.id.fabOk);
        if (bahanBakuList!=null){
            showEditPilihBahan();
        }else {
            showEdit();
        }
        mOk.setOnClickListener(this);
        d.show();

    }
    private void showEdit(){
        MProdukRelasi produkRelasi = crud.getRelasi().get(pos);
        String satuan = produkRelasi.getSatuan_digunakan();
        int jumlah = produkRelasi.getIsi_digunakan();
        if (satuan.equals("ml")) {
            mSatuan.setSelection(0);
        } else if (satuan.equals("gr")) {
            mSatuan.setSelection(1);
        } else {
            mSatuan.setSelection(2);
        }
        mSatuan.setEnabled(false);
        mJumlah.getEditText().setText(String.valueOf(jumlah));
        nmBahan.setText(produkRelasi.getNama_bahan());
    }
    private void showEditPilihBahan(){
        MBahanBaku bahanBaku = bahanBakuList.get(pos);
        String satuan = dbmHarga.hargaBahan(0,bahanBaku.getId()).getSatuan();
        String nama = bahanBaku.getNama_bahan();
        if (satuan.equals("ml")) {
            mSatuan.setSelection(0);
        } else if (satuan.equals("gr")) {
            mSatuan.setSelection(1);
        } else {
            mSatuan.setSelection(2);
        }
        mSatuan.setEnabled(false);
        nmBahan.setText(nama);

        //set jumlah on edit
        if (bahanBaku.isSelected()){
            int poscrud = bahanBaku.getPos();
            mJumlah.getEditText().setText(String.valueOf(crud.getRelasi().get(poscrud).getIsi_digunakan()));
            //Toast toast = Toast.makeText(view.getContext(), String.valueOf(poscrud), Toast.LENGTH_SHORT);
            //toast.show();
        }
    }
    private void edit(){
        MProdukRelasi produkRelasi = crud.getRelasi().get(pos);
        if (!(mJumlah.getEditText().getText().toString().trim().length() >0)|| mJumlah.getEditText().getText().toString().equals("0")) {
            crud.delete(pos);
        }else {
            setData(produkRelasi);
            crud.update(pos, produkRelasi);
        }
        cekEmptyList();
    }
    private void pilihBahan(){
        MProdukRelasi produkRelasi = new MProdukRelasi();
        //useless
        if (!bahanBakuList.get(pos).isSelected()&&!(mJumlah.getEditText().getText().toString().trim().length() >0)){
            mJumlah.setError("Tidak Boleh Kosong");
            return;
        }
        if (!(mJumlah.getEditText().getText().toString().trim().length() >0)|| mJumlah.getEditText().getText().toString().equals("0")){
            crud.delete(bahanBakuList.get(pos).getPos());
            bahanBakuList.get(pos).setSelected(false);
            bahanBakuList.get(pos).setPos(0);
        }else {
            //ganti crud
            setData(produkRelasi);
            //update rv bahan
            if (bahanBakuList.get(pos).isSelected()) {
                crud.update(bahanBakuList.get(pos).getPos(), produkRelasi);
            } else {
                crud.addNew(produkRelasi);
                //produkRelasi.setPos(crud.getRelasi().size() - 1);
                bahanBakuList.get(pos).setPos(crud.getRelasi().size() - 1);
                bahanBakuList.get(pos).setSelected(true);
            }
        }
        mBKAdapter.notifyDataSetChanged();
    }
    private void setData(MProdukRelasi produkRelasi){
        int id_bahan;
        int jumlah_dg;
        String satuan;
        String nama;
        if (bahanBakuList!=null){
            id_bahan = bahanBakuList.get(pos).getId();
            satuan = mSatuan.getSelectedItem().toString();
            nama =bahanBakuList.get(pos).getNama_bahan();
        }else {
            id_bahan = crud.getRelasi().get(pos).getFk_id_bahan();
            satuan = crud.getRelasi().get(pos).getSatuan_digunakan();
            nama = crud.getRelasi().get(pos).getNama_bahan();
        }
        jumlah_dg = Integer.parseInt(mJumlah.getEditText().getText().toString());
        //set id_bahan to crud
        produkRelasi.setFk_id_bahan(id_bahan);
        produkRelasi.setIsi_digunakan(jumlah_dg);
        produkRelasi.setSatuan_digunakan(satuan);
        produkRelasi.setNama_bahan(nama);
    }
    private void cekEmptyList(){
        mAdapter.notifyDataSetChanged();

        if (crud.getRelasi().size()==0){
            scrollView.setVisibility(View.GONE);
            frameLayout.setVisibility(View.VISIBLE);
        }else {
            scrollView.setVisibility(View.VISIBLE);
            frameLayout.setVisibility(View.GONE);
        }
    }
    @Override
    public void onClick(View v) {
        if (bahanBakuList!=null){
            pilihBahan();
        }else {
            edit();
            cekEmptyList();
        }
        //back
        d.dismiss();

    }
}
