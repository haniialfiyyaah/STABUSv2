package com.stabus.app.TKalkulator;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.button.MaterialButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.stabus.app.Interface.ISetListener;
import com.stabus.app.Model.CollectString;
import com.stabus.app.Model.CollectStringCRUD;
import com.stabus.app.R;

public class KalkulatorSetKemasan extends Fragment implements View.OnClickListener {

    View view;
    private TextView mTvProdukName;
    private TextView mTvJmlProduk;
    private TextView mTvJmlHarga;
    private MaterialButton mBAddKemasan;
    private RecyclerView mRV;
    private LinearLayout nextLine;

    private ISetListener mISetListener;

    private CollectStringCRUD stringCRUD;

    private KalkulatorDialogKemasan dialogKemasan;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_kalkulator_kemasan,container,false);
        init();
        return view;
    }

    private void init(){
        initView();
        initObject();
        initListener();
        setNamaProduk();
        setRV();
        dialogKemasan = new KalkulatorDialogKemasan(view,mISetListener);
    }

    private void initView(){
        mTvProdukName = view.findViewById(R.id.tvProdukName);
        mTvJmlProduk = view.findViewById(R.id.tvJmlProduk);
        mTvJmlHarga = view.findViewById(R.id.tvJumlahHarga);
        mRV = view.findViewById(R.id.rvPilihHarga);
        nextLine = view.findViewById(R.id.next);
        mBAddKemasan = view.findViewById(R.id.addKemasan);
    }
    private void initObject(){
        //crud = new CollectBahanCRUD(CollectBahanBaku.getRelasi());
        stringCRUD = new CollectStringCRUD(CollectString.getString());

    }
    private void initListener(){
        nextLine.setOnClickListener(this);
        mBAddKemasan.setOnClickListener(this);
    }
    private void setRV(){
    }

    @SuppressLint("DefaultLocale")
    private void setNamaProduk(){
        mTvProdukName.setText(stringCRUD.getString().get(0).getNama());
        mTvJmlProduk.setText(String.format("%d %s", stringCRUD.getString().get(0).getJumlah(), stringCRUD.getString().get(0).getSatuan()));
        mTvJmlHarga.setText(String.format("Rp. %,.0f", stringCRUD.getString().get(0).getHarga_total()));
    }

    @Override
    public void onClick(View v) {
        if (v==mBAddKemasan){
            dialogKemasan.showDialog();
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mISetListener = (ISetListener) getActivity();
    }
}
