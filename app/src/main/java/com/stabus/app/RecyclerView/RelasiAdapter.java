package com.stabus.app.RecyclerView;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.stabus.app.Interface.OnListener;
import com.stabus.app.Model.MProdukRelasi;
import com.stabus.app.R;

import java.util.List;

public class RelasiAdapter extends RecyclerView.Adapter<RelasiHolder> {

    private List<MProdukRelasi> produkRelasiList;
    private OnListener mOnListener;

    private boolean isKalkulator;

    public RelasiAdapter(List<MProdukRelasi> produkRelasiList, OnListener mOnListener, boolean isKalkulator) {
        this.produkRelasiList = produkRelasiList;
        this.mOnListener = mOnListener;
        this.isKalkulator = isKalkulator;
    }

    @NonNull
    @Override
    public RelasiHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).
                inflate(R.layout.card_relasi_bahan, viewGroup, false);
        return new RelasiHolder(view, mOnListener);
    }

    @SuppressLint({"DefaultLocale", "ResourceAsColor"})
    @Override
    public void onBindViewHolder(@NonNull RelasiHolder relasiHolder, int i) {
        MProdukRelasi produkRelasi = produkRelasiList.get(i);
        relasiHolder.nameText.setText(produkRelasi.getNama_bahan());
        if (isKalkulator){
            relasiHolder.jumlahText.setVisibility(View.VISIBLE);
            relasiHolder.jumlahText.setText(String.format("%d %s", produkRelasi.getIsi_digunakan(), produkRelasi.getSatuan_digunakan()));
            if (produkRelasi.getHarga_pilih()==0){
                relasiHolder.hargaText.setText("PILIH HARGA");
                relasiHolder.hargaText.setTextColor(Color.RED);
            }else {
                relasiHolder.hargaText.setText(String.format("Rp. %,.0f / %d %s"
                        , produkRelasi.getHarga_pilih(), produkRelasi.getIsi_pilih(), produkRelasi.getSatuan_pilih()));
                //relasiHolder.hargaText.setText(String.format("Rp. %,.0f /  ", produkRelasi.getHarga_pilih()));
                relasiHolder.hargaText.setTextColor(Color.BLACK);
            }
        }else {
            relasiHolder.hargaText.setText(String.format("%d %s", produkRelasi.getIsi_digunakan(), produkRelasi.getSatuan_digunakan()));
            relasiHolder.jumlahText.setVisibility(View.GONE);
        }

    }

    @Override
    public int getItemCount() {
        return produkRelasiList.size();
    }
}
