package com.stabus.app.TKalkulator;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.stabus.app.Interface.ISetListener;
import com.stabus.app.Model.CollectString;
import com.stabus.app.Model.CollectStringCRUD;
import com.stabus.app.R;

import java.util.Locale;

public class KalkulatorGetHarga extends Fragment {

    View view;

    TextView mProduk;
    TextView mHPP;
    TextView mMargin;
    TextView mHitung;
    TextView mHarga;
    Button mOk;


    String produkNama;
    int jumlah;
    String satuan;
    float totalHarga;
    float margin;
    String rumusHitung;
    float hargaJual;

    CollectStringCRUD stringCRUD;

    ISetListener mISetListener;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_kalkulator_getharga, container, false);
        init();
        return view;
    }

    private void init() {
        initView();
        initObject();
        initValue();
        initListener();
        setText();
    }

    private void initListener() {
        mOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mISetListener.navigationClikc(R.id.navRiwayat);
            }
        });
    }

    private void initView() {
        mProduk=view.findViewById(R.id.tvProduk);
        mHPP=view.findViewById(R.id.tvHPP);
        mMargin=view.findViewById(R.id.tvMargin);
        mHitung=view.findViewById(R.id.tvHitung);
        mHarga=view.findViewById(R.id.tvHargaJual);
        mOk=view.findViewById(R.id.ok);
    }
    private void initObject(){
        stringCRUD = new CollectStringCRUD(CollectString.getString());
    }
    private void initValue(){
        produkNama = stringCRUD.getString().get(0).getNama();
        jumlah= stringCRUD.getString().get(0).getJumlah();
        satuan= stringCRUD.getString().get(0).getSatuan();
        totalHarga= stringCRUD.getString().get(0).getHarga_total();
        margin= stringCRUD.getString().get(0).getMargin_harga();
        hargaJual = stringCRUD.getString().get(0).getHarga_jual();
        rumusHitung = String.format(Locale.US, "(Rp. %,.0f + Rp. %,.0f) / %s ",totalHarga,margin,jumlah);

    }
    private void setText(){
        String produk = produkNama+" / "+jumlah+" "+satuan;
        mProduk.setText(produk);
        mHPP.setText(String.format(Locale.US,"Rp. %,.0f",totalHarga));
        mMargin.setText(String.format(Locale.US,"Rp. %,.0f",margin));
        mHitung.setText(rumusHitung);
        mHarga.setText(String.format(Locale.US,"Rp. %,.0f / %s",hargaJual,satuan));
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mISetListener = (ISetListener) getActivity();
    }
}
