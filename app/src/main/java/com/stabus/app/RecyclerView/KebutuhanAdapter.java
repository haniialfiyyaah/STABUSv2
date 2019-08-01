package com.stabus.app.RecyclerView;

import android.annotation.SuppressLint;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.stabus.app.Interface.OnListener;
import com.stabus.app.Model.MKebutuhan;
import com.stabus.app.R;

import java.util.List;

public class KebutuhanAdapter extends RecyclerView.Adapter<KebutuhanHolder> {

    private List<MKebutuhan> kebutuhanList;
    private OnListener mOnListener;

    public KebutuhanAdapter(List<MKebutuhan> kebutuhanList, OnListener mOnListener) {
        this.kebutuhanList = kebutuhanList;
        this.mOnListener = mOnListener;
    }

    @NonNull
    @Override
    public KebutuhanHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).
                inflate(R.layout.card_relasi_bahan, viewGroup, false);
        return new KebutuhanHolder(view, mOnListener);
    }

    @SuppressLint("DefaultLocale")
    @Override
    public void onBindViewHolder(@NonNull KebutuhanHolder kebutuhanHolder, int i) {
        MKebutuhan kebutuhan = kebutuhanList.get(i);
        kebutuhanHolder.jumlahText.setVisibility(View.GONE);

        kebutuhanHolder.nameText.setText(kebutuhan.getNama());
        kebutuhanHolder.hargaText.setText(String.format("Rp. %,.0f ", kebutuhan.getHarga()));
    }

    @Override
    public int getItemCount() {
        return kebutuhanList.size();
    }
}
