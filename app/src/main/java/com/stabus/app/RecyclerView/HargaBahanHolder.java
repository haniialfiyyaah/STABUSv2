package com.stabus.app.RecyclerView;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import com.stabus.app.Interface.OnListener;
import com.stabus.app.R;

public class HargaBahanHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {

    TextView merkText;
    TextView tempatText;
    TextView hargaText;
    CheckBox checkBox;
    private OnListener onListener;

    public HargaBahanHolder(@NonNull View itemView, OnListener onListener) {
        super(itemView);
        merkText = itemView.findViewById(R.id.tvMerk);
        tempatText = itemView.findViewById(R.id.tvTempat);
        hargaText = itemView.findViewById(R.id.tvHarga);
        checkBox = itemView.findViewById(R.id.checkboxHarga);

        this.onListener = onListener;
        itemView.setOnClickListener(this);
        itemView.setOnLongClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int pos = getAdapterPosition();
        if (pos!= RecyclerView.NO_POSITION){
            onListener.OnClickListener(pos, itemView);
        }
    }

    @Override
    public boolean onLongClick(View v) {
        return onListener.OnLongListener(getAdapterPosition(),itemView);
    }
}
