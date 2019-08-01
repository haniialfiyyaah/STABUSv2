package com.stabus.app.TKalkulator;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.stabus.app.Database.DBMRiwayat;
import com.stabus.app.Interface.ISetListener;
import com.stabus.app.Model.CollectString;
import com.stabus.app.Model.CollectStringCRUD;
import com.stabus.app.Model.MRiwayat;
import com.stabus.app.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class KalkulatorSetMargin extends Fragment implements SeekBar.OnSeekBarChangeListener , View.OnClickListener {

    View view;
    private TextView mTvProdukName;
    private TextView mTvJmlProduk;
    private TextView mTvHPP;
    private TextView mTxMarginHarga;

    private LinearLayout nextLine;
    private ISetListener mISetListener;

    private float hargaPP;

    private double tenagaKerja;
    private double biayaOp;
    private double resiko;
    private double keuntungan;
    private double marketing;

    private TextView tvTenagaKerja;
    private TextView tvBiayaOp;
    private TextView tvResiko;
    private TextView tvKeuntungan;
    private TextView tvMarketing;

    private SeekBar sTenagaKerja;
    private SeekBar sBiayaOp;
    private SeekBar sKeuntungan;
    private SeekBar sMarketing;


    private CollectStringCRUD stringCRUD;

    private DBMRiwayat dbmRiwayat;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_kalkulator_margin, container, false);
        init();
        return view;
    }

    private void init(){
        initView();
        initObject();
        initValue();
        initListener();
        setNamaProduk();
        setSeekBar();
        setMarginHarga();
    }

    private void initView(){
        mTvProdukName = view.findViewById(R.id.tvProdukName);
        mTvJmlProduk = view.findViewById(R.id.tvJmlProduk);
        mTvHPP = view.findViewById(R.id.tvHPP);
        mTxMarginHarga = view.findViewById(R.id.marginHarga);
        nextLine = view.findViewById(R.id.next);
        viewTextSeekBar();
        viewSeekBar();
    }

    private void initValue() {
        hargaPP = stringCRUD.getString().get(0).getHarga_total();
        tenagaKerja = stringCRUD.getString().get(0).getTenagaKerja();
        biayaOp = stringCRUD.getString().get(0).getBiayaOp();
        resiko = stringCRUD.getString().get(0).getResiko();
        keuntungan = stringCRUD.getString().get(0).getKeuntungan();
        marketing = stringCRUD.getString().get(0).getMarketing();
    }

    private void viewTextSeekBar(){
        tvTenagaKerja= view.findViewById(R.id.tvKomponen1);
        tvBiayaOp= view.findViewById(R.id.tvKomponen2);
        tvResiko= view.findViewById(R.id.tvKomponen3);
        tvKeuntungan= view.findViewById(R.id.tvKomponen4);
        tvMarketing= view.findViewById(R.id.tvKomponen5);
    }
    private void viewSeekBar(){
        sTenagaKerja= view.findViewById(R.id.komponen1);
        sBiayaOp= view.findViewById(R.id.komponen2);
        sKeuntungan= view.findViewById(R.id.komponen4);
        sMarketing= view.findViewById(R.id.komponen5);
    }
    private void initObject(){
        stringCRUD = new CollectStringCRUD(CollectString.getString());
        dbmRiwayat = new DBMRiwayat(view);
    }

    private void initListener(){
        sTenagaKerja.setOnSeekBarChangeListener(this);
        sBiayaOp.setOnSeekBarChangeListener(this);
        sKeuntungan.setOnSeekBarChangeListener(this);
        sMarketing.setOnSeekBarChangeListener(this);
        nextLine.setOnClickListener(this);
    }

    private void setMarginHarga(){
        double psT = tenagaKerja/100;
        double psB = biayaOp/100;
        double psR = resiko/100;
        double psK = keuntungan/100;
        double psM = marketing/100;
        float hTenaga = (float) (psT*hargaPP);
        float hbiaya = (float) (psB*hargaPP);
        float hResiko = (float) (psR*hargaPP);
        float hKeuntungan = (float) (psK*hargaPP);
        float hMarketing = (float) (psM*hargaPP);

        float margin = hTenaga+hbiaya+hResiko+hKeuntungan+hMarketing;
        mTxMarginHarga.setText(String.format(Locale.US,"MARGIN : Rp. %,.0f", margin));
        stringCRUD.getString().get(0).setMargin_harga(margin);
        setHargaJual();
    }

    private void setHargaJual(){
        int jumlah= stringCRUD.getString().get(0).getJumlah();
        float totalHarga= stringCRUD.getString().get(0).getHarga_total();
        float margin= stringCRUD.getString().get(0).getMargin_harga();
        float harga_jual =  (totalHarga+margin)/jumlah;
        stringCRUD.getString().get(0).setHarga_jual(harga_jual);
    }

    private void setSeekBar(){
        tvTenagaKerja.setText(String.format(Locale.US, "%d%%", (int) tenagaKerja));
        tvBiayaOp.setText(String.format(Locale.US, "%d%%", (int) biayaOp));
        tvResiko.setText(String.format(Locale.US, "%d%%", (int) resiko));
        tvKeuntungan.setText(String.format(Locale.US, "%d%%", (int) keuntungan));
        tvMarketing.setText(String.format(Locale.US, "%d%%", (int) marketing));

        sTenagaKerja.setProgress((int) tenagaKerja-15);
        sBiayaOp.setProgress((int) biayaOp-10);
        sKeuntungan.setProgress((int) keuntungan-30);
        sMarketing.setProgress((int) marketing-10);
    }

    private void setNamaProduk(){
        mTvProdukName.setText(stringCRUD.getString().get(0).getNama());
        mTvJmlProduk.setText(String.format(Locale.US,"%d %s", stringCRUD.getString().get(0).getJumlah(), stringCRUD.getString().get(0).getSatuan()));
        mTvHPP.setText(String.format(Locale.US,"Rp. %,.0f ", stringCRUD.getString().get(0).getHarga_total()));
    }

    private void saveRiwayat(){
        String nama = stringCRUD.getString().get(0).getNama();
        int jumlah = stringCRUD.getString().get(0).getJumlah();
        String satuan = stringCRUD.getString().get(0).getSatuan();
        float harga_pokok = stringCRUD.getString().get(0).getHarga_total();
        float margin = stringCRUD.getString().get(0).getHarga_total();
        float harga_jual = stringCRUD.getString().get(0).getHarga_jual();
        dbmRiwayat.saveRiwayat(nama,jumlah,satuan,harga_pokok,margin,harga_jual);
        Toast toast = Toast.makeText(getContext(), nama, Toast.LENGTH_SHORT);
        toast.show();
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        if (seekBar==sTenagaKerja){
            tenagaKerja = progress+15;
            tvTenagaKerja.setText(String.format(Locale.US, "%d%%", (int) tenagaKerja));
            stringCRUD.getString().get(0).setTenagaKerja(tenagaKerja);
        }else if (seekBar==sBiayaOp){
            biayaOp = progress+10;
            tvBiayaOp.setText(String.format(Locale.US, "%d%%", (int) biayaOp));
            stringCRUD.getString().get(0).setBiayaOp(biayaOp);
        }else if (seekBar==sKeuntungan){
            keuntungan = progress+30;
            tvKeuntungan.setText(String.format(Locale.US, "%d%%", (int) keuntungan));
            stringCRUD.getString().get(0).setKeuntungan(keuntungan);
        }else if (seekBar==sMarketing){
            marketing = progress+10;
            tvMarketing.setText(String.format(Locale.US, "%d%%", (int) marketing));
            stringCRUD.getString().get(0).setMarketing(marketing);
        }
        setMarginHarga();
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onClick(View v) {
        if (v==nextLine){
            saveRiwayat();
            mISetListener.inflateFragment(getString(R.string.KalkulatorGetHarga),null);

        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mISetListener = (ISetListener) getActivity();
    }
}
