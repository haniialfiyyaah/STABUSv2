package com.stabus.app.RecyclerView;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.stabus.app.Interface.OnListener;
import com.stabus.app.Model.MBahanBaku;
import com.stabus.app.R;

import java.util.ArrayList;
import java.util.List;

public class BahanBakuAdapter extends RecyclerView.Adapter<BahanBakuHolder> {

    private List<MBahanBaku> bahanBakuList;
    private OnListener mOnListener;
    private boolean isProduk;



    public BahanBakuAdapter(List<MBahanBaku> bahanBakuList, OnListener onListener, boolean isProduk) {
        this.bahanBakuList = bahanBakuList;
        this.mOnListener = onListener;
        this.isProduk = isProduk;
    }

    @NonNull
    @Override
    public BahanBakuHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).
                inflate(R.layout.card_bahan_baku, viewGroup, false);
        return new BahanBakuHolder(view, mOnListener);
    }

    @SuppressLint("DefaultLocale")
    @Override
    public void onBindViewHolder(@NonNull BahanBakuHolder bahanBakuHolder, int i) {
        final MBahanBaku bahanBaku = bahanBakuList.get(i);
        final String nama = bahanBaku.getNama_bahan();
        String jumlah;
        if (bahanBaku.getJumlah()>99){
            jumlah = "99+ harga";
        }else if (bahanBaku.getJumlah()<=0){
            jumlah = "Tidak Ada Harga";
        }else {
            jumlah = String.format("%s harga",bahanBaku.getJumlah());
        }

        bahanBakuHolder.nameText.setText(nama);
        if (bahanBaku.getJumlah_dg()!=0){
            bahanBakuHolder.jumlahText.setText(String.format("%d %s", bahanBaku.getJumlah_dg(), bahanBaku.getSatuan_dg()));
        }else {
            bahanBakuHolder.jumlahText.setText(jumlah);
        }


        if (isProduk){
            bahanBakuHolder.checkBox.setVisibility(View.VISIBLE);
            bahanBakuHolder.jumlahText.setVisibility(View.GONE);
        }else {
            bahanBakuHolder.checkBox.setVisibility(View.GONE);
            bahanBakuHolder.jumlahText.setVisibility(View.VISIBLE);
            if (!bahanBaku.isOpen()){
                bahanBakuHolder.checkBox.setVisibility(View.GONE);
                bahanBakuHolder.checkBox.setChecked(false);
            }else {
                bahanBakuHolder.checkBox.setVisibility(View.VISIBLE);
            }
        }
        if (bahanBaku.isSelected()){
            bahanBakuHolder.checkBox.setChecked(true);
        }else {
            bahanBakuHolder.checkBox.setChecked(false);
        }

    }

    @Override
    public int getItemCount() {
        return bahanBakuList.size();
    }

    public void filter(List<MBahanBaku> bahanBakuListFull ,CharSequence s){
        List<MBahanBaku> filteredList = new ArrayList<>();
        if (s.length()==0){
            filteredList.addAll(bahanBakuListFull);
        }else {
            String filterPattern = s.toString().toLowerCase().trim();
            for (MBahanBaku item : bahanBakuListFull){
                if (item.getNama_bahan().toLowerCase().contains(filterPattern)){
                    filteredList.add(item);
                }
            }
        }
        bahanBakuList.clear();
        bahanBakuList.addAll(filteredList);
        notifyDataSetChanged();
    }

    public void delete(List<MBahanBaku> selectedList){
        for (MBahanBaku bahanBaku : selectedList){
            bahanBakuList.remove(bahanBaku);
            notifyDataSetChanged();
        }
    }
}
