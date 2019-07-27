package com.stabus.app;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ScrollView;

public class KalkulatorPilihProduk extends Fragment {

    private RecyclerView mRecyclerView;
    private FrameLayout frameRV;
    private ScrollView scrollView;
    private FloatingActionButton fabTambah;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_kalkulator_produk,container,false);
        initView(view);
        return view;
    }

    private void initView(View view){

    }
}
