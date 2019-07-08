package com.stabus.app.RecyclerView;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
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

    public BahanBakuAdapter(List<MBahanBaku> bahanBakuList, OnListener onListener) {
        this.bahanBakuList = bahanBakuList;
        this.mOnListener = onListener;
    }

    @NonNull
    @Override
    public BahanBakuHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).
                inflate(R.layout.card_bahan_baku, viewGroup, false);
        return new BahanBakuHolder(view, mOnListener);
    }

    @Override
    public void onBindViewHolder(@NonNull BahanBakuHolder bahanBakuHolder, int i) {
        final MBahanBaku bahanBaku = bahanBakuList.get(i);
        String nama = bahanBaku.getNama_bahan();
        String jumlah;
        if (bahanBaku.getJumlah()>99){
            jumlah = "99+ harga";
        }else if (bahanBaku.getJumlah()<=0){
            jumlah = "Tidak Ada Harga";
        }else {
            jumlah = String.format("%s harga",bahanBaku.getJumlah());
        }
        bahanBakuHolder.nameText.setText(nama);
        bahanBakuHolder.jumlahText.setText(jumlah);

        if (bahanBaku.isSelected()){
            bahanBakuHolder.checkBox.setChecked(true);
        }else {
            bahanBakuHolder.checkBox.setChecked(false);
        }

        if (!bahanBaku.isOpen()){
            bahanBakuHolder.checkBox.setVisibility(View.GONE);
            bahanBakuHolder.checkBox.setChecked(false);
        }else {
            bahanBakuHolder.checkBox.setVisibility(View.VISIBLE);
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
