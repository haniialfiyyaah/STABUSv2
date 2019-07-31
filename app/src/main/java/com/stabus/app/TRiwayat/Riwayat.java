package com.stabus.app.TRiwayat;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.stabus.app.R;

public class Riwayat extends Fragment {

    View view;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_riwayat, container, false);
        init();
        return view;

    }

    private void init() {
        initView();
        initObject();
        initListener();
        setmRV();
    }

    private void initView() {
    }

    private void initObject() {
    }

    private void initListener() {
    }

    private void setmRV() {
    }
}
