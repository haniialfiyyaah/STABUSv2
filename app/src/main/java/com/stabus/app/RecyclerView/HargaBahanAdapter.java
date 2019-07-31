package com.stabus.app.RecyclerView;

import android.annotation.SuppressLint;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.stabus.app.Interface.OnListener;
import com.stabus.app.Model.MHargaBahan;
import com.stabus.app.R;

import java.util.ArrayList;
import java.util.List;

public class HargaBahanAdapter extends RecyclerView.Adapter<HargaBahanHolder> {

    private List<MHargaBahan> hargaBahanList;
    private OnListener mOnListener;

    private boolean isKalkulator;

    public HargaBahanAdapter(List<MHargaBahan> hargaBahanList, OnListener mOnListener, boolean isKalkulator) {
        this.hargaBahanList = hargaBahanList;
        this.mOnListener = mOnListener;
        this.isKalkulator = isKalkulator;
    }

    @NonNull
    @Override
    public HargaBahanHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).
                inflate(R.layout.card_harga_bahan, viewGroup, false);
        return new HargaBahanHolder(view, mOnListener);
    }

    @Override
    public void onBindViewHolder(@NonNull HargaBahanHolder hargaBahanHolder, int i) {
        MHargaBahan hargaBahan = hargaBahanList.get(i);
        String merk = hargaBahan.getMerk();
        String isi = String.valueOf(hargaBahan.getIsi());
        String satuan = hargaBahan.getSatuan();
        String tempat=hargaBahan.getTempat_beli();

        if (merk.trim().length()==0) merk = "Tidak Ada Merk";
        if (merk.trim().length()==0) tempat = "Tidak Ada Tempat";

        @SuppressLint("DefaultLocale")
        String harga = String.format("Rp. %,.0f / %s %s ", hargaBahan.getHarga(), isi, satuan);
        hargaBahanHolder.merkText.setText(merk);
        hargaBahanHolder.tempatText.setText(tempat);
        hargaBahanHolder.hargaText.setText(harga);

        if (isKalkulator){
            hargaBahanHolder.merkText.setVisibility(View.GONE);
            hargaBahanHolder.tempatText.setText(merk);
        }

        if (hargaBahan.isSelected()){
            hargaBahanHolder.checkBox.setChecked(true);
        }else {
            hargaBahanHolder.checkBox.setChecked(false);
        }
        if (!hargaBahan.isOpen()){
            hargaBahanHolder.checkBox.setVisibility(View.GONE);
            hargaBahanHolder.checkBox.setChecked(false);
        }else {
            hargaBahanHolder.checkBox.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public int getItemCount() {
        return hargaBahanList.size();
    }

    public void filter(List<MHargaBahan> hargaBahanListFull ,CharSequence s){
        List<MHargaBahan> filteredList = new ArrayList<>();
        if (s.length()==0){
            filteredList.addAll(hargaBahanListFull);
        }else {
            String userInput = s.toString().toLowerCase().trim();
            for (MHargaBahan name : hargaBahanListFull){
                String isi = name.getIsi() + " " + name.getSatuan();
                String harga = String.valueOf(name.getHarga());
                if (name.getMerk().toLowerCase().contains(userInput) || name.getTempat_beli().toLowerCase().contains(userInput) ||
                        isi.toLowerCase().contains(userInput) || harga.toLowerCase().contains(userInput)){
                    filteredList.add(name);
                }
            }
        }
        hargaBahanList.clear();
        hargaBahanList.addAll(filteredList);
        notifyDataSetChanged();
    }

    public void delete(List<MHargaBahan> selectedList){
        for (MHargaBahan bahanBaku : selectedList){
            hargaBahanList.remove(bahanBaku);
            notifyDataSetChanged();
        }
    }
}
