package com.stabus.app;

import android.app.Dialog;
import android.support.design.widget.TextInputLayout;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.stabus.app.Database.DBMHarga;
import com.stabus.app.Model.CollectBahanCRUD;
import com.stabus.app.Model.MBahanBaku;
import com.stabus.app.RecyclerView.BahanBakuAdapter;
import com.stabus.app.RecyclerView.ProdukAdapter;

import java.util.List;

public class ProdukDialogTambah implements View.OnClickListener {

    //widget dialog
    TextView nmBahan;
    TextInputLayout mJumlah;
    Spinner mSatuan;
    ImageButton mOk;
    List<MBahanBaku> bahanBakuList;
    BahanBakuAdapter mAdapter;
    DBMHarga dbmHarga;

    //
    Dialog d;
    int pos;
    View view;

    CollectBahanCRUD crud;

    public ProdukDialogTambah(List<MBahanBaku> bahanBakuList, BahanBakuAdapter mAdapter, DBMHarga dbmHarga, CollectBahanCRUD crud) {
        this.bahanBakuList = bahanBakuList;
        this.mAdapter = mAdapter;
        this.dbmHarga = dbmHarga;
        this.crud = crud;
    }

    public ProdukDialogTambah(CollectBahanCRUD crud, BahanBakuAdapter mAdapter) {
        this.crud = crud;
        this.mAdapter = mAdapter;
    }

    public void showDialog(View view, int pos){
        this.view = view;
        this.pos = pos;

        d = new Dialog(view.getContext());
        d.requestWindowFeature(Window.FEATURE_NO_TITLE);
        d.setContentView(R.layout.list_pilih_jumlahdg);
        nmBahan = d.findViewById(R.id.tvNmBahan);
        mJumlah = d.findViewById(R.id.textJumlahdg);
        mSatuan = d.findViewById(R.id.spSatuan);
        mOk = d.findViewById(R.id.fabOk);
        if (bahanBakuList!=null){
            fromRV();
        }else {
            showEdit();
        }

        mOk.setOnClickListener(this);
        d.show();

    }
    private void showEdit(){
        MBahanBaku bahanBaku = crud.getBahanBakuList().get(pos);
        String satuan = bahanBaku.getSatuan_dg();
        int jumlah = bahanBaku.getJumlah_dg();
        if (satuan.equals("ml")) {
            mSatuan.setSelection(0);
        } else if (satuan.equals("gr")) {
            mSatuan.setSelection(1);
        } else {
            mSatuan.setSelection(2);
        }
        mJumlah.getEditText().setText(String.valueOf(bahanBaku.getJumlah_dg()));
        mSatuan.setEnabled(false);
        nmBahan.setText(bahanBaku.getNama_bahan());

    }

    private void edit(){
        MBahanBaku bahanBaku = crud.getBahanBakuList().get(pos);
        setData(bahanBaku);
        if (!(mJumlah.getEditText().getText().toString().trim().length() >0)|| mJumlah.getEditText().getText().toString().equals("0")) {
            crud.delete(pos);
        }else {
            crud.update(bahanBaku.getPos(), bahanBaku);
        }
        mAdapter.notifyDataSetChanged();
    }

    private void fromRV(){
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
            mJumlah.getEditText().setText(String.valueOf(crud.getBahanBakuList().get(poscrud).getJumlah_dg()));
            Toast toast = Toast.makeText(view.getContext(), String.valueOf(poscrud), Toast.LENGTH_SHORT);
            toast.show();
        }
        d.show();
    }

    private void pilihBahan(){
        MBahanBaku bahanBaku = new MBahanBaku();
        if (!bahanBakuList.get(pos).isSelected()&&!(mJumlah.getEditText().getText().toString().trim().length() >0)){
            mJumlah.setError("Tidak Boleh Kosong");
            return;
        }
        if (!(mJumlah.getEditText().getText().toString().trim().length() >0)|| mJumlah.getEditText().getText().toString().equals("0")){
            crud.delete(bahanBaku.getPos());
            bahanBakuList.get(pos).setSelected(false);
            bahanBakuList.get(pos).setPos(0);
        }else {
            //ganti crud
            setData(bahanBaku);
            //update rv bahan
            if (bahanBakuList.get(pos).isSelected()) {
                crud.update(bahanBaku.getPos(), bahanBaku);
            } else {
                crud.addNew(bahanBaku);
                bahanBakuList.get(pos).setPos(crud.getBahanBakuList().size() - 1);
                bahanBakuList.get(pos).setSelected(true);
            }
        }
        mAdapter.notifyDataSetChanged();
    }
    private void setData(MBahanBaku bahanBaku){
        int id_bahan;
        int jumlah_dg;
        String satuan;
        String nama;

        if (bahanBakuList!=null){
            id_bahan = bahanBakuList.get(pos).getId();

            satuan = mSatuan.getSelectedItem().toString();
            nama =bahanBakuList.get(pos).getNama_bahan();
        }else {
            id_bahan = crud.getBahanBakuList().get(pos).getId();
            satuan = crud.getBahanBakuList().get(pos).getSatuan_dg();
            nama = crud.getBahanBakuList().get(pos).getNama_bahan();
        }
        jumlah_dg = Integer.parseInt(mJumlah.getEditText().getText().toString());
        //set id_bahan to crud

        bahanBaku.setId(id_bahan);
        bahanBaku.setJumlah_dg(jumlah_dg);
        bahanBaku.setSatuan_dg(satuan);
        bahanBaku.setNama_bahan(nama);
    }

    @Override
    public void onClick(View v) {
        if (bahanBakuList!=null){
            pilihBahan();
        }else {
            edit();
        }
        //back
        d.dismiss();

    }
}
