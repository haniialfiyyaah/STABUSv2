package com.stabus.app;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;

import com.stabus.app.Interface.ISetListener;
import com.stabus.app.Interface.OnListener;

public class Kalkulator extends Fragment implements SeekBar.OnSeekBarChangeListener, View.OnClickListener {


    private ISetListener mISetListener;

    SeekBar sk1,sk2,sk3,sk4,sk5;
    TextView mTenaga,mBiaya,mResiko,mUntung,mMarket,mHasil, title, searchProduk;
    int setTenaga,setBiaya,setResiko,setUntung,setMarket,setHasil;
    Toolbar toolbar;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_kalkulator,container,false);
        initView(view);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        settoolbaron();
        initListener();
        return view;
    }



    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        switch(seekBar.getId()){
            case R.id.seekbar1:
                mTenaga.setText(String.valueOf(progress));
                setTenaga = Integer.parseInt(mTenaga.getText().toString());
                break;
            case R.id.seekbar2:
                mBiaya.setText(String.valueOf(progress));
                setBiaya = Integer.parseInt(mBiaya.getText().toString());
                break;
            case R.id.seekbar3:
                mResiko.setText(String.valueOf(progress));
                setResiko = Integer.parseInt(mResiko.getText().toString());
                break;
            case R.id.seekbar4:
                mUntung.setText(String.valueOf(progress));
                setUntung = Integer.parseInt(mUntung.getText().toString());
                break;
            case R.id.seekbar5:
                mMarket.setText(String.valueOf(progress));
                setMarket = Integer.parseInt(mMarket.getText().toString());
        }
        Jumlah();
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }

    private void initView(View view){
        sk1 = view.findViewById(R.id.seekbar1);
        sk2 = view.findViewById(R.id.seekbar2);
        sk3 = view.findViewById(R.id.seekbar3);
        sk4 = view.findViewById(R.id.seekbar4);
        sk5 = view.findViewById(R.id.seekbar5);
        mTenaga = view.findViewById(R.id.tenagaKerja);
        mBiaya = view.findViewById(R.id.biayaOpera);
        mResiko = view.findViewById(R.id.resiko);
        mUntung = view.findViewById(R.id.keuntungan);
        mMarket = view.findViewById(R.id.marketing);
        mHasil = view.findViewById(R.id.hasil);
        title = view.findViewById(R.id.tvTitle);
        toolbar = view.findViewById(R.id.toolbarKalk);
        searchProduk = view.findViewById(R.id.search_produk);
    }

    private void initListener(){
        sk1.setOnSeekBarChangeListener(this);
        sk2.setOnSeekBarChangeListener(this);
        sk3.setOnSeekBarChangeListener(this);
        sk4.setOnSeekBarChangeListener(this);
        sk5.setOnSeekBarChangeListener(this);
        searchProduk.setOnClickListener(this);
    }
    private void Jumlah(){
        setHasil = setTenaga;
        mHasil.setText(String.valueOf(setHasil));
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mISetListener = (ISetListener)getActivity();
    }

    private void settoolbaron(){
        title.setText(getTag());
        toolbar.setNavigationIcon((R.drawable.ic_home_white));
    }

    @Override
    public void onClick(View v) {
        mISetListener.inflateFragment(getString(R.string.KalkulatorPilihProduk), null);
    }
}
