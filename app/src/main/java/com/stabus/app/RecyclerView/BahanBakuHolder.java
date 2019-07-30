package com.stabus.app.RecyclerView;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import com.stabus.app.Interface.OnListener;
import com.stabus.app.R;

class BahanBakuHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
    TextView nameText;
    TextView jumlahText;
    CheckBox checkBox;
    private OnListener onListener;

    BahanBakuHolder(@NonNull View itemView, OnListener onListener) {
        super(itemView);
        nameText = itemView.findViewById(R.id.tvNamaBK);
        jumlahText = itemView.findViewById(R.id.tvJumlahBK);
        checkBox = itemView.findViewById(R.id.checkboxBahan);
        this.onListener = onListener;
        itemView.setOnClickListener(this);
        itemView.setOnLongClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int pos = getAdapterPosition();
        String c = nameText.getText().toString();
        if (pos!= RecyclerView.NO_POSITION){
            onListener.OnClickListener(pos,c,itemView);
        }
    }

    @Override
    public boolean onLongClick(View v) {
        return onListener.OnLongListener(getAdapterPosition(),itemView);
    }
}
