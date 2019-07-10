package com.stabus.app.RecyclerView;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.stabus.app.Interface.OnListener;
import com.stabus.app.Model.MProdukRelasi;
import com.stabus.app.R;

import java.util.ArrayList;
import java.util.List;

public class ProdukAdapter extends RecyclerView.Adapter<ProdukHolder> {

    private List<MProdukRelasi> produkList;
    private OnListener mOnListener;

    public ProdukAdapter(List<MProdukRelasi> produkList, OnListener mOnListener) {
        this.produkList = produkList;
        this.mOnListener = mOnListener;
    }

    @NonNull
    @Override
    public ProdukHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).
                inflate(R.layout.card_produk, viewGroup, false);
        return new ProdukHolder(view,mOnListener);
    }

    @SuppressLint("DefaultLocale")
    @Override
    public void onBindViewHolder(@NonNull ProdukHolder produkHolder, int i) {
        MProdukRelasi item = produkList.get(i);
        String nama = item.getNama();
        //int jumlah = item.getJumlah();
        //String satuan = item.getSatuan();

        produkHolder.nama_produk.setText(nama);
        //produkHolder.jumlah_produksi.setText(String.format("Untuk %d %s", jumlah, satuan));

        if (item.isSelected()){
            produkHolder.cardView.setBackgroundColor(Color.GRAY);
            produkHolder.nama_produk.setTextColor(Color.WHITE);
        }else {
            produkHolder.cardView.setBackgroundColor(Color.WHITE);
            produkHolder.nama_produk.setTextColor(Color.GRAY);
        }

    }

    @Override
    public int getItemCount() {
        return produkList.size();
    }

    public void filter(List<MProdukRelasi> produkListFull , CharSequence s){
        List<MProdukRelasi> filteredList = new ArrayList<>();
        if (s.length()==0){
            filteredList.addAll(produkListFull);
        }else {
            String filterPattern = s.toString().toLowerCase().trim();
            for (MProdukRelasi item : produkListFull){
                if (item.getNama().toLowerCase().contains(filterPattern)){
                    filteredList.add(item);
                }
            }
        }
        produkList.clear();
        produkList.addAll(filteredList);
        notifyDataSetChanged();
    }
    public void delete(List<MProdukRelasi> selectedList){
        for (MProdukRelasi bahanBaku : selectedList){
            produkList.remove(bahanBaku);
            notifyDataSetChanged();
        }
    }
}
