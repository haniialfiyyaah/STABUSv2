package com.stabus.app.RecyclerView;

import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.stabus.app.Interface.OnListener;
import com.stabus.app.R;

public class RelasiHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    TextView nameText;
    TextView jumlahText;
    TextView hargaText;
    CardView cardView;
    private OnListener onListener;

    public RelasiHolder(@NonNull View itemView, OnListener onListener) {
        super(itemView);
        nameText = itemView.findViewById(R.id.tvNamaDG);
        jumlahText = itemView.findViewById(R.id.tvJumlahDG);
        hargaText = itemView.findViewById(R.id.tvHargaDG);
        cardView = itemView.findViewById(R.id.cardRelasi);
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
