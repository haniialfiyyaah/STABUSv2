package com.stabus.app.TKalkulator;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.stabus.app.Interface.ISetListener;
import com.stabus.app.R;

public class Kalkulator extends Fragment implements View.OnClickListener {

    private View view;
    private ISetListener mISetListener;

    //widget
    private AppCompatButton openProduk;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_kalkulator, container, false);
        initView();
        initListener();
        return view;
    }

    public void initView(){
        openProduk = view.findViewById(R.id.openProduk);
    }
    public void initListener(){
        openProduk.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v==openProduk){
            mISetListener.inflateFragment(getString(R.string.KalkulatorPilihProduk),null);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mISetListener = (ISetListener) getActivity();
    }

    public void onBackPressed(){
        getExitTransition();
    }
}
