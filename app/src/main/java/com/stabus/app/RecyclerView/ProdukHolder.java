package com.stabus.app.RecyclerView;

import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.stabus.app.Interface.OnListener;
import com.stabus.app.R;

public class ProdukHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {


    TextView nama_produk;
    TextView jumlah_produksi;
    CardView cardView;
    private OnListener onListener;

    public ProdukHolder(@NonNull View itemView, OnListener onListener) {
        super(itemView);
        this.nama_produk = itemView.findViewById(R.id.tvProduk);
        this.jumlah_produksi = itemView.findViewById(R.id.tvJumlahProduksi);
        this.cardView = itemView.findViewById(R.id.cardProduk);
        this.onListener = onListener;
        itemView.setOnClickListener(this);
        itemView.setOnLongClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int pos = getAdapterPosition();
        if (pos!= RecyclerView.NO_POSITION){
            onListener.OnClickListener(pos,itemView);
        }
    }

    @Override
    public boolean onLongClick(View v) {
        return onListener.OnLongListener(getAdapterPosition(),itemView);
    }
}
