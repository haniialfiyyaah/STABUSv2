package com.stabus.app.RecyclerView;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.stabus.app.Interface.OnListener;
import com.stabus.app.Model.MRiwayat;
import com.stabus.app.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class RiwayatAdapter extends RecyclerView.Adapter<RiwayatHolder> {

    private List<MRiwayat> riwayatList;
    private OnListener mOnListener;

    public RiwayatAdapter(List<MRiwayat> riwayatList, OnListener mOnListener) {
        this.riwayatList = riwayatList;
        this.mOnListener = mOnListener;
    }

    @NonNull
    @Override
    public RiwayatHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).
                inflate(R.layout.card_relasi_bahan, viewGroup, false);
        return new RiwayatHolder(view, mOnListener);
    }

    @Override
    public void onBindViewHolder(@NonNull RiwayatHolder riwayatHolder, int i) {
        MRiwayat riwayat = riwayatList.get(i);
        riwayatHolder.nameText.setText(riwayat.getNama());
        riwayatHolder.jumlahText.setText(String.format(Locale.US,"%d %s"
                , riwayat.getJumlah(), riwayat.getSatuan()));
        riwayatHolder.hargaText.setText(String.format(Locale.US,"Rp. %,.0f / %s"
                , riwayat.getHarga_jual(), riwayat.getSatuan()));

        if (riwayat.isSelected()){
            riwayatHolder.cardView.setBackgroundColor(Color.GRAY);
            riwayatHolder.nameText.setTextColor(Color.WHITE);
        }else {
            riwayatHolder.cardView.setBackgroundColor(Color.WHITE);
            riwayatHolder.nameText.setTextColor(Color.GRAY);
        }
    }

    @Override
    public int getItemCount() {
        return riwayatList.size();
    }

    public void filter(List<MRiwayat> riwayatListFull , CharSequence s){
        List<MRiwayat> filteredList = new ArrayList<>();
        if (s.length()==0){
            filteredList.addAll(riwayatListFull);
        }else {
            String filterPattern = s.toString().toLowerCase().trim();
            for (MRiwayat item : riwayatListFull){
                if (item.getNama().toLowerCase().contains(filterPattern)){
                    filteredList.add(item);
                }
            }
        }
        riwayatList.clear();
        riwayatList.addAll(filteredList);
        notifyDataSetChanged();
    }
    public void delete(List<MRiwayat> selectedList){
        for (MRiwayat riwayat : selectedList){
            riwayatList.remove(riwayat);
            notifyDataSetChanged();
        }
    }
}
