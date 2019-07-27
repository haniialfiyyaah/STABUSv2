package com.stabus.app.RecyclerView;

import android.annotation.SuppressLint;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.stabus.app.Interface.OnListener;
import com.stabus.app.Model.MProdukRelasi;
import com.stabus.app.R;

import java.util.ArrayList;
import java.util.List;

public class KalkulatorProdukAdapter extends RecyclerView.Adapter<KalkulatorProdukHolder> {
    private List<MProdukRelasi> produklist;
    private OnListener onListener;

    public KalkulatorProdukAdapter(List<MProdukRelasi> produklist, OnListener mOnlistener){
        this.produklist = produklist;
        this.onListener = mOnlistener;
    }
    @NonNull
    @Override
    public KalkulatorProdukHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_pilih_produk, viewGroup, false);
        return new KalkulatorProdukHolder(view, onListener);
    }

    @SuppressLint("DefaultLocale")
    @Override
    public void onBindViewHolder(@NonNull final KalkulatorProdukHolder kalkulatorProdukHolder, int i) {
        MProdukRelasi item = produklist.get(i);
        String nama = item.getNama();

        kalkulatorProdukHolder.nameText.setText(nama);

//        kalkulatorProdukHolder.nameText.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Log.d("AAA", kalkulatorProdukHolder.nameText.getText().toString());
//
//            }
//        });

    }

    @Override
    public int getItemCount() {
        return produklist.size();
    }


}
