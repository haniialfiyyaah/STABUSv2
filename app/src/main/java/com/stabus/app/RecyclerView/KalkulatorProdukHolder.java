package com.stabus.app.RecyclerView;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.stabus.app.Interface.OnListener;
import com.stabus.app.R;

public class KalkulatorProdukHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    TextView nameText;
    private OnListener onListener;

    public KalkulatorProdukHolder(@NonNull View itemView, OnListener onListener) {
        super(itemView);
        this.nameText = itemView.findViewById(R.id.tvNamaProduk);
        this.onListener = onListener;
        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int pos = getAdapterPosition();
        if (pos!= RecyclerView.NO_POSITION){
            onListener.OnClickListener(pos,itemView);
        }
    }
}
