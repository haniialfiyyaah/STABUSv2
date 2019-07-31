package com.stabus.app.RecyclerView;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.stabus.app.Interface.OnListener;
import com.stabus.app.R;

public class KemasanHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    TextView nameText;
    TextView jumlahText;
    TextView hargaText;
    private OnListener onListener;

    KemasanHolder(@NonNull View itemView, OnListener onListener) {
        super(itemView);
        nameText = itemView.findViewById(R.id.tvNamaDG);
        jumlahText = itemView.findViewById(R.id.tvJumlahDG);
        hargaText = itemView.findViewById(R.id.tvHargaDG);
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
