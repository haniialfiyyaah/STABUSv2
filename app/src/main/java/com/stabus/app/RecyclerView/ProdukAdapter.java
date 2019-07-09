package com.stabus.app.RecyclerView;

import android.annotation.SuppressLint;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.stabus.app.Interface.OnListener;
import com.stabus.app.Model.MProdukBahan;
import com.stabus.app.R;

import java.util.List;

public class ProdukAdapter extends RecyclerView.Adapter<ProdukHolder> {

    private List<MProdukBahan> produkList;
    private OnListener mOnListener;

    public ProdukAdapter(List<MProdukBahan> produkList, OnListener mOnListener) {
        this.produkList = produkList;
        this.mOnListener = mOnListener;
    }

    @NonNull
    @Override
    public ProdukHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).
                inflate(R.layout.card_produk, viewGroup, false);
        return new ProdukHolder(view,mOnListener);
    }

    @SuppressLint("DefaultLocale")
    @Override
    public void onBindViewHolder(@NonNull ProdukHolder produkHolder, int i) {
        MProdukBahan item = produkList.get(i);
        String nama = item.getNama();
        int jumlah = item.getJumlah();
        String satuan = item.getSatuan();

        produkHolder.nama_produk.setText(nama);
        produkHolder.jumlah_produksi.setText(String.format("Untuk %d %s", jumlah, satuan));

    }

    @Override
    public int getItemCount() {
        return produkList.size();
    }
}
