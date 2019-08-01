package com.stabus.app.RecyclerView;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.stabus.app.Interface.OnListener;
import com.stabus.app.Model.MKemasan;
import com.stabus.app.R;

import java.util.List;
import java.util.Locale;

public class KemasanAdapter extends RecyclerView.Adapter<KemasanHolder> {

    private List<MKemasan> kemasanList;
    private OnListener mOnListener;

    public KemasanAdapter(List<MKemasan> kemasanList, OnListener mOnListener) {
        this.kemasanList = kemasanList;
        this.mOnListener = mOnListener;
    }

    @NonNull
    @Override
    public KemasanHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).
                inflate(R.layout.card_relasi_bahan, viewGroup, false);
        return new KemasanHolder(view, mOnListener);
    }

    @Override
    public void onBindViewHolder(@NonNull KemasanHolder kemasanHolder, int i) {
        MKemasan kemasan = kemasanList.get(i);
        kemasanHolder.nameText.setText(kemasan.getNama());
        kemasanHolder.jumlahText.setText(String.format(Locale.US,"%d %s", kemasan.getJumlah(), kemasan.getSatuan()));
        kemasanHolder.hargaText.setText(String.format(Locale.US,"Rp. %,.0f / %s", kemasan.getHarga(), kemasan.getSatuan()));
    }

    @Override
    public int getItemCount() {
        return kemasanList.size();
    }
}
